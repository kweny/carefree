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
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisLathe implements CarefreeRedisLatheClientConfiguration, CarefreeRedisLatheModeConfiguration {

    static final CarefreeRedisLathe INSTANCE = new CarefreeRedisLathe();

    static CarefreeRedisLathe getInstance() {
        return INSTANCE;
    }

    private final Map<String, CarefreeRedisPayload> payloads;

    private CarefreeRedisLathe() {
        this.payloads = new ConcurrentHashMap<>();
    }

    void load(String key, Config config) throws Exception {
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

            // 解析加载配置数据
            payload.redisArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetype.class, config, root, referenceRoot);
            payload.poolArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypePool.class, config, poolPath, refPoolPath);
            payload.resourcesArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeResources.class, config, resourcesPath, refResourcesPath);
            payload.optionsArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeOptions.class, config, optionsPath, refOptionsPath);
            payload.serializerArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeSerializer.class, config, serializerPath, refSerializerPath);

            // 创建配置数据中指定的监听器
            CarefreeClassDeclaration declaration = payload.redisArchetype.getConfigureListener();
            CarefreeRedisConfigureListener listener = declaration == null ? null : declaration.instance();

            if (listener != null) {
                listener.archetype(payload.toConfigureEvent());
            }

            if (TempCarefreeAide.isNotFalse(payload.redisArchetype.getEnabled())) {
                // 如果该配置启用
                // 创建 ClientConfiguration 和 RedisConfiguration 对象
                payload.clientConfiguration = createClientConfiguration(payload);
                payload.redisConfiguration = createRedisConfiguration(payload);

                if (listener != null) {
                    listener.configuration(payload.toConfigureEvent());
                }

                // 创建 ConnectionFactory 对象
                payload.connectionFactory = new LettuceConnectionFactory(payload.redisConfiguration, payload.clientConfiguration);
                payload.connectionFactory.afterPropertiesSet();

                if (listener != null) {
                    listener.factory(payload.toConfigureEvent());
                }
            }

            // 缓存 payload
            this.payloads.put(payload.root, payload);
        }
    }

    Map<String, CarefreeRedisPayload> payloads() {
        return this.payloads;
    }
}
