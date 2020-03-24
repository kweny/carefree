/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apenk.carefree.redis;

import com.typesafe.config.Config;
import org.apenk.carefree.aide.GenericPair;
import org.apenk.carefree.helper.CarefreeAide;
import org.apenk.carefree.helper.CarefreeAssistance;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetype;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypePool;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeResources;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisBuilder implements CarefreeRedisBuilderClient, CarefreeRedisBuilderServer {

    private static final CarefreeRedisBuilder INSTANCE = new CarefreeRedisBuilder();

    public static CarefreeRedisBuilder getInstance() {
        return INSTANCE;
    }

    private final Map<String, GenericPair<CarefreeRedisArchetype, CarefreeRedisArchetypePool>> archetypeCache;

    private CarefreeRedisBuilder() {
        this.archetypeCache = new ConcurrentHashMap<>();
    }

    void loadConfig(Config config) throws Exception {
        Set<String> rootNames = CarefreeAssistance.getConfigRoots(config);

        for (String rootName : rootNames) {
            Config oneConfig = config.getConfig(rootName);
            CarefreeRedisArchetype archetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetype.class, oneConfig);
            CarefreeRedisArchetypePool poolArchetype;
            CarefreeRedisArchetypeResources resourcesArchetype;
            if (oneConfig.hasPath("pool")) {
                Config poolConfig = oneConfig.getConfig("pool");
                poolArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypePool.class, poolConfig);
            } else {
                poolArchetype = new CarefreeRedisArchetypePool();
            }
            if (oneConfig.hasPath("resources")) {
                Config resourcesConfig = oneConfig.getConfig("resources");
                resourcesArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeResources.class, resourcesConfig);
            } else {
                resourcesArchetype = new CarefreeRedisArchetypeResources();
            }
            if (oneConfig.hasPath("options")) {
                Config optionsConfig = oneConfig.getConfig("options");

            }
            this.archetypeCache.put(rootName, GenericPair.newInstance(archetype, poolArchetype));
        }
    }

    Map<String, CarefreeRedisWrapper> build() {
        Map<String, CarefreeRedisWrapper> map = new HashMap<>();
        archetypeCache.forEach((key, pair) -> {
            try {
                CarefreeRedisArchetype archetype = pair.first();
                CarefreeRedisArchetypePool poolArchetype = pair.second();

                if (CarefreeAide.isFalse(archetype.getEnabled())) {
                    return; // means continue
                }
                if (CarefreeAide.isNotBlank(archetype.getReference())) {
                    GenericPair<CarefreeRedisArchetype, CarefreeRedisArchetypePool> refPair = archetypeCache.get(archetype.getReference());
                    CarefreeRedisArchetype refArchetype = refPair.first();
                    CarefreeRedisArchetypePool refPoolArchetype = refPair.second();
                    if (refArchetype == null) {
                        CarefreeRedisAutoConfiguration.logger.warn("no reference: {} for redis config: {}", archetype.getReference(), key);
                        return; // means continue
                    }

                    CarefreeAssistance.loadReference(CarefreeRedisArchetype.class, archetype, refArchetype);
                    if (CarefreeAide.isNotNull(poolArchetype) || CarefreeAide.isNotNull(refPoolArchetype)) {
                        CarefreeAssistance.loadReference(CarefreeRedisArchetypePool.class, poolArchetype, refPoolArchetype);
                    }
                }

                CarefreeRedisWrapper wrapper = new CarefreeRedisWrapper();
                wrapper.setFactory(createConnectionFactory(archetype, poolArchetype));
//                wrapper.setDisableDefaultSerializer(archetype.isDisableDefaultSerializer());
//                wrapper.setDefaultSerializer(archetype.getDefaultSerializer());
//                wrapper.setKeySerializer(archetype.getKeySerializer());
//                wrapper.setValueSerializer(archetype.getValueSerializer());
//                wrapper.setHashKeySerializer(archetype.getHashKeySerializer());
//                wrapper.setHashValueSerializer(archetype.getHashValueSerializer());
                map.put(key, wrapper);
            } catch (Exception e) {
                throw new RuntimeException("[Carefree] error to create redis factory for redis config name: " + key, e);
            }
        });
        CarefreeAide.clear(archetypeCache);
        return map;
    }

    private static final String CONNECT_MODE_Standalone = "Standalone";
    private static final String CONNECT_MODE_Cluster = "Cluster";
    private static final String CONNECT_MODE_Sentinel = "Sentinel";
    private static final String CONNECT_MODE_Socket = "Socket";
    private static final String CONNECT_MODE_StaticMasterReplica  = "StaticMasterReplica";

    private LettuceConnectionFactory createConnectionFactory(CarefreeRedisArchetype archetype, CarefreeRedisArchetypePool poolArchetype) {
        LettuceClientConfiguration clientConfiguration = createClientConfiguration(archetype, poolArchetype);

        LettuceConnectionFactory factory;
        if (CarefreeAide.equalsIgnoreCase(CONNECT_MODE_Cluster, archetype.getMode())) {

            RedisClusterConfiguration cluster = createClusterConfiguration(archetype);
            factory = new LettuceConnectionFactory(cluster, clientConfiguration);

        } else if (CarefreeAide.equalsIgnoreCase(CONNECT_MODE_Sentinel, archetype.getMode())) {

            RedisSentinelConfiguration sentinel = createSentinelConfiguration(archetype);
            factory = new LettuceConnectionFactory(sentinel, clientConfiguration);

        } else if (CarefreeAide.equalsIgnoreCase(CONNECT_MODE_Socket, archetype.getMode())) {

            RedisSocketConfiguration socket = createSocketConfiguration(archetype);
            factory = new LettuceConnectionFactory(socket, clientConfiguration);

        } else if (CarefreeAide.equalsIgnoreCase(CONNECT_MODE_StaticMasterReplica, archetype.getMode())) {

            RedisStaticMasterReplicaConfiguration staticMasterReplica = createStaticMasterReplicaConfiguration(archetype);
            factory = new LettuceConnectionFactory(staticMasterReplica, clientConfiguration);

        } else if (CarefreeAide.equalsIgnoreCase(CONNECT_MODE_Standalone, archetype.getMode())) {

            RedisStandaloneConfiguration standalone = createStandaloneConfiguration(archetype);
            factory = new LettuceConnectionFactory(standalone, clientConfiguration);

        } else {

            RedisStandaloneConfiguration standalone = createStandaloneConfiguration(archetype);
            factory = new LettuceConnectionFactory(standalone, clientConfiguration);

        }

        factory.afterPropertiesSet();

        return factory;
    }
}