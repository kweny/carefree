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

import org.apenk.carefree.helper.CarefreeClassDeclaration;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeSerializer;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     持有 Redis 连接工厂对象，
 *     以 carefreeRedisRegistry 为 bean name 存在 Spring 于容器，
 *     可注入到应用程序中，用于创建 Redis 模板对象。
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisRegistry {
    public static final String BEAN_NAME = "carefreeRedisRegistry";

    private ClassLoader classLoader;
    private Map<String, LettuceConnectionFactory> factoryHolder;
    private Map<String, CarefreeRedisArchetypeSerializer> serializerHolder;

    public CarefreeRedisRegistry(ResourceLoader resourceLoader) {
        if (resourceLoader != null) {
            this.classLoader = resourceLoader.getClassLoader();
        }
        this.factoryHolder = new ConcurrentHashMap<>();
        this.serializerHolder = new ConcurrentHashMap<>();
    }

    public void register(String root, CarefreeRedisPayload payload) {
        this.factoryHolder.put(root, payload.connectionFactory);
        this.serializerHolder.put(root, payload.serializerArchetype);
    }

    public LettuceConnectionFactory getConnectionFactory(String root) {
        return this.factoryHolder.get(root);
    }

    public Map<String, LettuceConnectionFactory> getAllConnectionFactories() {
        return Collections.unmodifiableMap(this.factoryHolder);
    }

    public Boolean getEnableDefaultSerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        // enableDefaultSerializer 默认为 true，只要不是 false 就返回 true（如 true 和 null 都认为是 true）
        return serializerArchetype != null ? serializerArchetype.getEnableDefaultSerializer() : null;
    }

    public <T extends RedisSerializer<?>> T getDefaultSerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        if (serializerArchetype == null) {
            return null;
        }
        CarefreeClassDeclaration declaration = serializerArchetype.getDefaultSerializer();
        return declaration != null ? declaration.instance() : null;
    }

    public <T extends RedisSerializer<?>> T getKeySerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        if (serializerArchetype == null) {
            return null;
        }
        CarefreeClassDeclaration declaration = serializerArchetype.getKeySerializer();
        return declaration != null ? declaration.instance() : null;
    }

    public <T extends RedisSerializer<?>> T getValueSerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        if (serializerArchetype == null) {
            return null;
        }
        CarefreeClassDeclaration declaration = serializerArchetype.getValueSerializer();
        return declaration != null ? declaration.instance() : null;
    }

    public <T extends RedisSerializer<?>> T getHashKeySerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        if (serializerArchetype == null) {
            return null;
        }
        CarefreeClassDeclaration declaration = serializerArchetype.getHashKeySerializer();
        return declaration != null ? declaration.instance() : null;
    }

    public <T extends RedisSerializer<?>> T getHashValueSerializer(String root) {
        CarefreeRedisArchetypeSerializer serializerArchetype = this.serializerHolder.get(root);
        if (serializerArchetype == null) {
            return null;
        }
        CarefreeClassDeclaration declaration = serializerArchetype.getHashValueSerializer();
        return declaration != null ? declaration.instance() : null;
    }

    public <K, V> ReactiveRedisTemplate<K, V> newReactiveRedisTemplate(String root) {
        LettuceConnectionFactory factory = getConnectionFactory(root);
        assertConnectionFactory(factory, root);
        return new ReactiveRedisTemplate<>(factory, buildSerializationContext(root, false));
    }

    public ReactiveStringRedisTemplate newReactiveStringRedisTemplate(String root) {
        LettuceConnectionFactory factory = getConnectionFactory(root);
        assertConnectionFactory(factory, root);
        return new ReactiveStringRedisTemplate(factory, buildSerializationContext(root, true));
    }

    @SuppressWarnings("unchecked")
    private <K, V> RedisSerializationContext<K, V> buildSerializationContext(String root, boolean isString) {
        RedisSerializationContext.RedisSerializationContextBuilder<K, V> builder = RedisSerializationContext.newSerializationContext();

        RedisSerializer<?> defaultSerializer = getDefaultSerializer(root);
        if (defaultSerializer == null) {
            defaultSerializer = isString ? RedisSerializer.string() : RedisSerializer.java(classLoader);
        }

        RedisSerializer<K> keySerializer = getKeySerializer(root);
        builder.key(keySerializer != null ? keySerializer : (RedisSerializer<K>) defaultSerializer);

        RedisSerializer<V> valueSerializer = getValueSerializer(root);
        builder.value(valueSerializer != null ? valueSerializer : (RedisSerializer<V>) defaultSerializer);

        RedisSerializer<?> hashKeySerializer = getHashKeySerializer(root);
        builder.hashKey(hashKeySerializer != null ? hashKeySerializer : defaultSerializer);

        RedisSerializer<?> hashValueSerializer = getHashValueSerializer(root);
        builder.hashValue(hashValueSerializer != null ? hashValueSerializer : defaultSerializer);

        return builder.build();
    }

    public StringRedisTemplate newStringTemplate(String root) {
        LettuceConnectionFactory factory = getConnectionFactory(root);
        assertConnectionFactory(factory, root);

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        setSerializer(template, root);

        template.afterPropertiesSet();

        return template;
    }

    public <K, V> RedisTemplate<K, V> newTemplate(String root) {
        LettuceConnectionFactory factory = getConnectionFactory(root);
        assertConnectionFactory(factory, root);

        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        setSerializer(template, root);

        template.afterPropertiesSet();

        return template;
    }

    private void setSerializer(RedisTemplate<?, ?> template, String root) {
        if (TempCarefreeAide.isFalse(getEnableDefaultSerializer(root))) {
            template.setEnableDefaultSerializer(false);
        }

        RedisSerializer<?> defaultSerializer = getDefaultSerializer(root);
        if (defaultSerializer != null) {
            template.setDefaultSerializer(defaultSerializer);
        }

        RedisSerializer<?> keySerializer = getKeySerializer(root);
        if (keySerializer != null) {
            template.setKeySerializer(keySerializer);
        }

        RedisSerializer<?> valueSerializer = getValueSerializer(root);
        if (valueSerializer != null) {
            template.setValueSerializer(valueSerializer);
        }

        RedisSerializer<?> hashKeySerializer = getHashKeySerializer(root);
        if (hashKeySerializer != null) {
            template.setHashKeySerializer(hashKeySerializer);
        }

        RedisSerializer<?> hashValueSerializer = getHashValueSerializer(root);
        if (hashValueSerializer != null) {
            template.setHashValueSerializer(hashValueSerializer);
        }
    }

    private void assertConnectionFactory(LettuceConnectionFactory factory, String root) {
        if (factory == null) {
            throw new IllegalArgumentException("[Carefree] no redis connection factory: " + root);
        }
    }
}
