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

import org.apenk.carefree.aide.BooleanAide;
import org.apenk.carefree.aide.StringAide;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持有 carefree druid 配置及 {@link RedisConnectionFactory} 对象，
 * 以 carefreeDruidRegistry 为 bean name 存在于容器，
 * 可注入到应用程序中，使用 {@link #get(String)} 方法获取指定 {@link RedisConnectionFactory} 对象，
 * 或使用 {@link #getAll()} 方法获取所有 {@link RedisConnectionFactory} 对象。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisRegistry {
    public static final String BEAN_NAME = "carefreeRedisRegistry";

    private static final Map<String, RedisSerializer<?>> SERIALIZER_CACHE = new ConcurrentHashMap<>();

    private Map<String, CarefreeRedisWrapper> holder;

    public CarefreeRedisRegistry() {
        this.holder = new ConcurrentHashMap<>();
    }

    public void register(String name, CarefreeRedisWrapper wrapper) {
        this.holder.put(name, wrapper);
    }

    public RedisConnectionFactory get(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getFactory() : null;
    }

    public Map<String, RedisConnectionFactory> getAll() {
        Map<String, RedisConnectionFactory> map = new ConcurrentHashMap<>(this.holder.size());
        this.holder.forEach((key, value) -> map.put(key, value.getFactory()));
        return Collections.unmodifiableMap(map);
    }

    public boolean isDisableDefaultSerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null && BooleanAide.isTrue(wrapper.isDisableDefaultSerializer());
    }

    public String getDefaultSerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getDefaultSerializer() : null;
    }

    public String getKeySerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getKeySerializer() : null;
    }

    public String getValueSerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getValueSerializer() : null;
    }

    public String getHashKeySerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getHashKeySerializer() : null;
    }

    public String getHashValueSerializer(String name) {
        CarefreeRedisWrapper wrapper = this.holder.get(name);
        return wrapper != null ? wrapper.getHashValueSerializer() : null;
    }

    public StringRedisTemplate newStringTemplate(String name) {
        RedisConnectionFactory factory = get(name);
        if (factory == null) {
            throw new RuntimeException("[Carefree] no redis config: " + name);
        }

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        setSerializer(template, name);

        template.afterPropertiesSet();

        return template;
    }

    public <K, V> RedisTemplate<K, V> newTemplate(String name) {
        RedisConnectionFactory factory = get(name);
        if (factory == null) {
            throw new RuntimeException("[Carefree] no redis config: " + name);
        }

        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        setSerializer(template, name);

        template.afterPropertiesSet();

        return template;
    }

    private void setSerializer(RedisTemplate<?, ?> template, String name) {
        if (isDisableDefaultSerializer(name)) {
            template.setEnableDefaultSerializer(false);
        }

        if (StringAide.isNotBlank(getDefaultSerializer(name))) {
            template.setDefaultSerializer(serializer(getDefaultSerializer(name)));
        }
        if (StringAide.isNotBlank(getKeySerializer(name))) {
            template.setKeySerializer(serializer(getKeySerializer(name)));
        }
        if (StringAide.isNotBlank(getValueSerializer(name))) {
            template.setValueSerializer(serializer(getValueSerializer(name)));
        }
        if (StringAide.isNotBlank(getHashKeySerializer(name))) {
            template.setHashKeySerializer(serializer(getHashKeySerializer(name)));
        }
        if (StringAide.isNotBlank(getHashValueSerializer(name))) {
            template.setHashValueSerializer(serializer(getHashValueSerializer(name)));
        }
    }

    private RedisSerializer<?> serializer(String className) {
        return SERIALIZER_CACHE.computeIfAbsent(className, k -> {
            try {
                return (RedisSerializer<?>) Class.forName(className).getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}