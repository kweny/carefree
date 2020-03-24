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
import org.apenk.carefree.helper.CarefreeAide;
import org.apenk.carefree.helper.CarefreeAssistance;
import org.apenk.carefree.redis.archetype.*;

import java.util.Set;

/**
 * TODO-Kweny CarefreeRedisLathe
 *
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisLathe {

    void loadConfig(String key, Config config) throws Exception {
        Set<String> roots = CarefreeAssistance.getConfigRoots(config);

        for (String root : roots) {
            CarefreeRedisPayload payload = new CarefreeRedisPayload();
            payload.key = key;
            payload.root = root;

            Config oneConfig = config.getConfig(root);
            payload.redisArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetype.class, oneConfig);

            if (oneConfig.hasPath("pool")) {
                Config poolConfig = oneConfig.getConfig("pool");
                payload.poolArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypePool.class, poolConfig);
            }

            if (oneConfig.hasPath("resources")) {
                payload.resourcesArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeResources.class, oneConfig.getConfig("resources"));
            }

            if (oneConfig.hasPath("options")) {
                payload.optionsArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeOptions.class, oneConfig.getConfig("options"));
            }

            if (oneConfig.hasPath("serializer")) {
                payload.serializerArchetype = CarefreeAssistance.fromConfig(CarefreeRedisArchetypeSerializer.class, oneConfig.getConfig("serializer"));
            }

            CarefreeRedisPayload.cache(root, payload);
        }
    }

    void build() {
        CarefreeRedisPayload.forEach((root, payload) -> {
            try {
                if (CarefreeAide.isFalse(payload.redisArchetype.getEnabled())) {
                    return; // means continue
                }

                if (CarefreeAide.isNotBlank(payload.redisArchetype.getReference())) {
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
}
