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

import org.apenk.carefree.redis.archetype.*;
import org.apenk.carefree.redis.listener.CarefreeRedisConfigureEvent;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;

/**
 * TODO-Kweny CarefreeRedisPayload
 *
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisPayload {
    private static final Map<String, CarefreeRedisPayload> PAYLOAD_CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    static void cache(String name, CarefreeRedisPayload payload) {
        PAYLOAD_CACHE.put(name, payload);
    }

    static CarefreeRedisPayload fetch(String name) {
        return PAYLOAD_CACHE.get(name);
    }

    static CarefreeRedisConfigureEvent fetchEvent(String name) {
        CarefreeRedisPayload payload = fetch(name);
        return payload != null ? payload.toConfigureEvent() : new CarefreeRedisConfigureEvent();
    }

    static void forEach(BiConsumer<String, CarefreeRedisPayload> action) {
        for (Map.Entry<String, CarefreeRedisPayload> entry : PAYLOAD_CACHE.entrySet()) {
            String root;
            CarefreeRedisPayload payload;
            try {
                root = entry.getKey();
                payload = entry.getValue();
            } catch(IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            action.accept(root, payload);
        }
    }

    String key;
    String root;

    CarefreeRedisArchetype redisArchetype;
    CarefreeRedisArchetypePool poolArchetype;
    CarefreeRedisArchetypeResources resourcesArchetype;
    CarefreeRedisArchetypeOptions optionsArchetype;
    CarefreeRedisArchetypeSerializer serializerArchetype;

    LettuceClientConfiguration clientConfiguration;
    RedisConfiguration redisConfiguration;

    LettuceConnectionFactory connectionFactory;

    CarefreeRedisConfigureEvent toConfigureEvent() {
        CarefreeRedisConfigureEvent event = new CarefreeRedisConfigureEvent();
        event.setKey(key);
        event.setRoot(root);
        event.setRedisArchetype(redisArchetype);
        event.setPoolArchetype(poolArchetype);
        event.setResourcesArchetype(resourcesArchetype);
        event.setOptionsArchetype(optionsArchetype);
        event.setSerializerArchetype(serializerArchetype);
        event.setClientConfiguration(clientConfiguration);
        event.setRedisConfiguration(redisConfiguration);
        event.setConnectionFactory(connectionFactory);
        return event;
    }
}