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

import io.lettuce.core.ReadFrom;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apenk.carefree.aide.BooleanAide;
import org.apenk.carefree.aide.ObjectAide;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * redis 客户端连接配置
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CarefreeRedisBuilderClient {

    default LettuceClientConfiguration createClientConfiguration(CarefreeRedisArchetype archetype, CarefreeRedisArchetypePool poolArchetype) {
        LettuceClientConfiguration clientConfiguration;
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder;

        if (BooleanAide.isTrue(archetype.getUsePooling())) {
            builder = LettucePoolingClientConfiguration.builder();
            GenericObjectPoolConfig<?> poolConfig = createPoolConfig(poolArchetype);
            ((LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder) builder).poolConfig(poolConfig);
        } else {
            builder = LettuceClientConfiguration.builder();
        }

        if (ObjectAide.isNotNull(archetype.getCommandTimeout())) {
            builder.commandTimeout(Duration.ofMillis(archetype.getCommandTimeout()));
        }
        if (ObjectAide.isNotNull(archetype.getShutdownTimeout())) {
            builder.shutdownTimeout(Duration.ofMillis(archetype.getShutdownTimeout()));
        }
        if (ObjectAide.isNotNull(archetype.getClientName())) {
            builder.clientName(archetype.getClientName());
        }
        if (ObjectAide.isNotNull(archetype.getReadFrom())) {
            builder.readFrom(ReadFrom.valueOf(archetype.getReadFrom()));
        }

        // TODO-Kweny useSsl, verifyPeer, startTls, clientName, timeout, shutdownTimeout, shutdownQuietPeriod, readFrom, clientResources, clientOptions
        clientConfiguration = builder.build();

        return clientConfiguration;
    }

    default GenericObjectPoolConfig<?> createPoolConfig(CarefreeRedisArchetypePool poolArchetype) {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        if (ObjectAide.isNotNull(poolArchetype.getMaxTotal())) {
            poolConfig.setMaxTotal(poolArchetype.getMaxTotal());
        }
        if (ObjectAide.isNotNull(poolArchetype.getMaxIdle())) {
            poolConfig.setMaxIdle(poolArchetype.getMaxIdle());
        }
        if (ObjectAide.isNotNull(poolArchetype.getMinIdle())) {
            poolConfig.setMinIdle(poolArchetype.getMinIdle());
        }
        if (ObjectAide.isNotNull(poolArchetype.getLifo())) {
            poolConfig.setLifo(poolArchetype.getLifo());
        }
        if (ObjectAide.isNotNull(poolArchetype.getFairness())) {
            poolConfig.setFairness(poolArchetype.getFairness());
        }
        if (ObjectAide.isNotNull(poolArchetype.getMaxWaitMillis())) {
            poolConfig.setMaxWaitMillis(poolArchetype.getMaxWaitMillis());
        }
        if (ObjectAide.isNotNull(poolArchetype.getMinEvictableIdleTimeMillis())) {
            poolConfig.setMinEvictableIdleTimeMillis(poolArchetype.getMinEvictableIdleTimeMillis());
        }
        if (ObjectAide.isNotNull(poolArchetype.getSoftMinEvictableIdleTimeMillis())) {
            poolConfig.setSoftMinEvictableIdleTimeMillis(poolArchetype.getSoftMinEvictableIdleTimeMillis());
        }
        if (ObjectAide.isNotNull(poolArchetype.getNumTestsPerEvictionRun())) {
            poolConfig.setNumTestsPerEvictionRun(poolArchetype.getNumTestsPerEvictionRun());
        }
        if (ObjectAide.isNotNull(poolArchetype.getEvictionPolicyClassName())) {
            poolConfig.setEvictionPolicyClassName(poolArchetype.getEvictionPolicyClassName());
        }
        if (ObjectAide.isNotNull(poolArchetype.getTestOnCreate())) {
            poolConfig.setTestOnCreate(poolArchetype.getTestOnCreate());
        }
        if (ObjectAide.isNotNull(poolArchetype.getTestWhileIdle())) {
            poolConfig.setTestWhileIdle(poolArchetype.getTestWhileIdle());
        }
        if (ObjectAide.isNotNull(poolArchetype.getTestOnBorrow())) {
            poolConfig.setTestOnBorrow(poolArchetype.getTestOnBorrow());
        }
        if (ObjectAide.isNotNull(poolArchetype.getTestOnReturn())) {
            poolConfig.setTestOnReturn(poolArchetype.getTestOnReturn());
        }
        if (ObjectAide.isNotNull(poolArchetype.getTimeBetweenEvictionRunsMillis())) {
            poolConfig.setTimeBetweenEvictionRunsMillis(poolArchetype.getTimeBetweenEvictionRunsMillis());
        }
        if (ObjectAide.isNotNull(poolArchetype.getBlockWhenExhausted())) {
            poolConfig.setBlockWhenExhausted(poolArchetype.getBlockWhenExhausted());
        }
        if (ObjectAide.isNotNull(poolArchetype.getJmxEnabled())) {
            poolConfig.setJmxEnabled(poolArchetype.getJmxEnabled());
        }
        if (ObjectAide.isNotNull(poolArchetype.getJmxNamePrefix())) {
            poolConfig.setJmxNamePrefix(poolArchetype.getJmxNamePrefix());
        }
        if (ObjectAide.isNotNull(poolArchetype.getJmxNameBase())) {
            poolConfig.setJmxNameBase(poolArchetype.getJmxNameBase());
        }
        return poolConfig;
    }

}
