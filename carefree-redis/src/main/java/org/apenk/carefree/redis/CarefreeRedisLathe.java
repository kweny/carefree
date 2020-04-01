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
import org.apenk.carefree.helper.CarefreeAssistance;
import org.apenk.carefree.helper.CarefreeClassDeclaration;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.*;
import org.apenk.carefree.redis.listener.CarefreeRedisConfigureListener;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Set;

/**
 * TODO-Kweny CarefreeRedisLathe
 *
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisLathe implements CarefreeRedisLatheClientConfiguration, CarefreeRedisLatheModeConfiguration {

    void loadConfig(String key, Config config) throws Exception {
        Set<String> roots = CarefreeAssistance.getConfigRoots(config);

        for (String root : roots) {
            CarefreeRedisPayload payload = new CarefreeRedisPayload();
            payload.key = key;
            payload.root = root;

            String referenceRoot = null;
            String referencePath;
            if (config.hasPath(referencePath = root.concat(".reference"))) {
                referenceRoot = config.getString(referencePath);
            }

            String poolPath = String.join(".", root, "pool");
            String resourcesPath = String.join(".", root, "resources");
            String optionsPath = String.join(".", root, "options");
            String serializerPath = String.join(".", root, "serializer");

            String refPoolPath = null;
            String refResourcesPath = null;
            String refOptionsPath = null;
            String refSerializerPath = null;
            if (TempCarefreeAide.isNotBlank(referenceRoot)) {
                refPoolPath = String.join(".", referenceRoot, "pool");
                refResourcesPath = String.join(".", referenceRoot, "resources");
                refOptionsPath = String.join(".", referenceRoot, "options");
                refSerializerPath = String.join(".", referenceRoot, "serializer");
            }

            payload.redisArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetype.class, config, root, referenceRoot);
            payload.poolArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypePool.class, config, poolPath, refPoolPath);
            payload.resourcesArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeResources.class, config, resourcesPath, refResourcesPath);
            payload.optionsArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeOptions.class, config, optionsPath, refOptionsPath);
            payload.serializerArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeSerializer.class, config, serializerPath, refSerializerPath);

            CarefreeClassDeclaration declaration = payload.redisArchetype.getConfigureListener();
            if (declaration != null) {
                CarefreeRedisConfigureListener listener = declaration.instance();
                listener.archetype(payload.toConfigureEvent());
            }

//            CarefreeRedisPayload.cache(root, payload);
        }
    }

    void build() {
        CarefreeRedisPayload.forEach((root, payload) -> {
            try {
                if (TempCarefreeAide.isFalse(payload.redisArchetype.getEnabled())) {
                    return; // means continue
                }

                if (TempCarefreeAide.isNotBlank(payload.redisArchetype.getReference())) {
                    CarefreeRedisPayload refPayload = CarefreeRedisPayload.fetch(payload.redisArchetype.getReference());
                    if (refPayload == null) {
                        CarefreeRedisAutoConfiguration.logger.warn("no reference: {} for redis config root: {}", payload.redisArchetype.getReference(), root);
                        return; // means continue
                    }


                }
            } catch (Exception e) {
                throw new RuntimeException("[Carefree] error to create redis factory for redis config root: " + root, e);
            }
        });
    }

    private static final String CONNECT_MODE_Standalone = "Standalone";
    private static final String CONNECT_MODE_Cluster = "Cluster";
    private static final String CONNECT_MODE_Sentinel = "Sentinel";
    private static final String CONNECT_MODE_Socket = "Socket";
    private static final String CONNECT_MODE_StaticMasterReplica  = "StaticMasterReplica";

    private LettuceConnectionFactory createConnectionFactory(CarefreeRedisArchetype archetype, CarefreeRedisArchetypePool poolArchetype) {
        LettuceClientConfiguration clientConfiguration = createClientConfiguration(archetype, poolArchetype);

        LettuceConnectionFactory factory;
        if (TempCarefreeAide.equalsIgnoreCase(CONNECT_MODE_Cluster, archetype.getMode())) {

            RedisClusterConfiguration cluster = createClusterConfiguration(archetype);
            factory = new LettuceConnectionFactory(cluster, clientConfiguration);

        } else if (TempCarefreeAide.equalsIgnoreCase(CONNECT_MODE_Sentinel, archetype.getMode())) {

            RedisSentinelConfiguration sentinel = createSentinelConfiguration(archetype);
            factory = new LettuceConnectionFactory(sentinel, clientConfiguration);

        } else if (TempCarefreeAide.equalsIgnoreCase(CONNECT_MODE_Socket, archetype.getMode())) {

            RedisSocketConfiguration socket = createSocketConfiguration(archetype);
            factory = new LettuceConnectionFactory(socket, clientConfiguration);

        } else if (TempCarefreeAide.equalsIgnoreCase(CONNECT_MODE_StaticMasterReplica, archetype.getMode())) {

            RedisStaticMasterReplicaConfiguration staticMasterReplica = createStaticMasterReplicaConfiguration(archetype);
            factory = new LettuceConnectionFactory(staticMasterReplica, clientConfiguration);

        } else if (TempCarefreeAide.equalsIgnoreCase(CONNECT_MODE_Standalone, archetype.getMode())) {

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
