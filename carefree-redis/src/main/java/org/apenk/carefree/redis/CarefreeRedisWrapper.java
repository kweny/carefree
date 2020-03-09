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

import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeRedisWrapper {
    private RedisConnectionFactory factory;
    private boolean disableDefaultSerializer;
    private String defaultSerializer;
    private String keySerializer;
    private String valueSerializer;
    private String hashKeySerializer;
    private String hashValueSerializer;

    RedisConnectionFactory getFactory() {
        return factory;
    }

    void setFactory(RedisConnectionFactory factory) {
        this.factory = factory;
    }

    boolean isDisableDefaultSerializer() {
        return disableDefaultSerializer;
    }

    void setDisableDefaultSerializer(boolean disableDefaultSerializer) {
        this.disableDefaultSerializer = disableDefaultSerializer;
    }

    String getDefaultSerializer() {
        return defaultSerializer;
    }

    void setDefaultSerializer(String defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    String getKeySerializer() {
        return keySerializer;
    }

    void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    String getValueSerializer() {
        return valueSerializer;
    }

    void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    String getHashKeySerializer() {
        return hashKeySerializer;
    }

    void setHashKeySerializer(String hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    String getHashValueSerializer() {
        return hashValueSerializer;
    }

    void setHashValueSerializer(String hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }
}
