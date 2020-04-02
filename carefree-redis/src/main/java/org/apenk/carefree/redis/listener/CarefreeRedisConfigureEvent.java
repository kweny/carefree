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

package org.apenk.carefree.redis.listener;

import org.apenk.carefree.redis.archetype.*;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 监听器接口 {@link CarefreeRedisConfigureListener} 的事件对象。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisConfigureEvent {
    private String key;
    private String root;

    private CarefreeRedisArchetype redisArchetype;
    private CarefreeRedisArchetypePool poolArchetype;
    private CarefreeRedisArchetypeResources resourcesArchetype;
    private CarefreeRedisArchetypeOptions optionsArchetype;
    private CarefreeRedisArchetypeSerializer serializerArchetype;

    private LettuceClientConfiguration clientConfiguration;
    private RedisConfiguration redisConfiguration;

    private LettuceConnectionFactory connectionFactory;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public CarefreeRedisArchetype getRedisArchetype() {
        return redisArchetype;
    }

    public void setRedisArchetype(CarefreeRedisArchetype redisArchetype) {
        this.redisArchetype = redisArchetype;
    }

    public CarefreeRedisArchetypePool getPoolArchetype() {
        return poolArchetype;
    }

    public void setPoolArchetype(CarefreeRedisArchetypePool poolArchetype) {
        this.poolArchetype = poolArchetype;
    }

    public CarefreeRedisArchetypeResources getResourcesArchetype() {
        return resourcesArchetype;
    }

    public void setResourcesArchetype(CarefreeRedisArchetypeResources resourcesArchetype) {
        this.resourcesArchetype = resourcesArchetype;
    }

    public CarefreeRedisArchetypeOptions getOptionsArchetype() {
        return optionsArchetype;
    }

    public void setOptionsArchetype(CarefreeRedisArchetypeOptions optionsArchetype) {
        this.optionsArchetype = optionsArchetype;
    }

    public CarefreeRedisArchetypeSerializer getSerializerArchetype() {
        return serializerArchetype;
    }

    public void setSerializerArchetype(CarefreeRedisArchetypeSerializer serializerArchetype) {
        this.serializerArchetype = serializerArchetype;
    }

    public LettuceClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    public void setClientConfiguration(LettuceClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    public RedisConfiguration getRedisConfiguration() {
        return redisConfiguration;
    }

    public void setRedisConfiguration(RedisConfiguration redisConfiguration) {
        this.redisConfiguration = redisConfiguration;
    }

    public LettuceConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(LettuceConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
