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

package org.apenk.carefree.redis.archetype;

import org.apenk.carefree.helper.CarefreeClassDeclaration;

/**
 * {@link org.springframework.data.redis.core.RedisTemplate} 使用的序列化程序
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetypeSerializer {
    /**
     * 是否使用默认的序列化程序，如果不使用，则任何未明确设置的序列化程序都将保持为空，并且不会对值进行序列化和反序列化，
     * 默认 true
     */
    private Boolean enableDefaultSerializer;
    /**
     * 默认的序列化程序，除了 {@link #stringSerializer} 之外，其它任何未明确设置的序列化程序都将被初始化为该默认程序，
     * 默认 {@link org.springframework.data.redis.serializer.JdkSerializationRedisSerializer}
     */
    private CarefreeClassDeclaration defaultSerializer;
    /**
     * 针对字符串的序列化程序，当参数和返回值始终为字符串时将使用该程序，
     * 默认 {@link org.springframework.data.redis.serializer.StringRedisSerializer}
     */
    private CarefreeClassDeclaration stringSerializer;
    /**
     * key-value 结构的序列化程序（key），
     * 默认根据 {@link #enableDefaultSerializer} 和 {@link #defaultSerializer} 确定
     */
    private CarefreeClassDeclaration keySerializer;
    /**
     * key-value 结构的序列化程序（value），
     * 默认根据 {@link #enableDefaultSerializer} 和 {@link #defaultSerializer} 确定
     */
    private CarefreeClassDeclaration valueSerializer;
    /**
     * hash 结构的序列化程序（key），
     * 默认根据 {@link #enableDefaultSerializer} 和 {@link #defaultSerializer} 确定
     */
    private CarefreeClassDeclaration hashKeySerializer;
    /**
     * hash结构的序列化程序（value)
     * 默认根据 {@link #enableDefaultSerializer} 和 {@link #defaultSerializer} 确定
     */
    private CarefreeClassDeclaration hashValueSerializer;

    public Boolean getEnableDefaultSerializer() {
        return enableDefaultSerializer;
    }

    public void setEnableDefaultSerializer(Boolean enableDefaultSerializer) {
        this.enableDefaultSerializer = enableDefaultSerializer;
    }

    public CarefreeClassDeclaration getDefaultSerializer() {
        return defaultSerializer;
    }

    public void setDefaultSerializer(CarefreeClassDeclaration defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    public CarefreeClassDeclaration getStringSerializer() {
        return stringSerializer;
    }

    public void setStringSerializer(CarefreeClassDeclaration stringSerializer) {
        this.stringSerializer = stringSerializer;
    }

    public CarefreeClassDeclaration getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(CarefreeClassDeclaration keySerializer) {
        this.keySerializer = keySerializer;
    }

    public CarefreeClassDeclaration getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(CarefreeClassDeclaration valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public CarefreeClassDeclaration getHashKeySerializer() {
        return hashKeySerializer;
    }

    public void setHashKeySerializer(CarefreeClassDeclaration hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    public CarefreeClassDeclaration getHashValueSerializer() {
        return hashValueSerializer;
    }

    public void setHashValueSerializer(CarefreeClassDeclaration hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }
}
