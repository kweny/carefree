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
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetype;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypePool;
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

        if (TempCarefreeAide.isTrue(archetype.getUsePooling())) {
            builder = LettucePoolingClientConfiguration.builder();
            GenericObjectPoolConfig<?> poolConfig = createPoolConfig(poolArchetype);
            ((LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder) builder).poolConfig(poolConfig);
        } else {
            builder = LettuceClientConfiguration.builder();
        }

        if (TempCarefreeAide.isNotNull(archetype.getCommandTimeout())) {
            builder.commandTimeout(Duration.ofMillis(archetype.getCommandTimeout()));
        }
        if (TempCarefreeAide.isNotNull(archetype.getShutdownTimeout())) {
            builder.shutdownTimeout(Duration.ofMillis(archetype.getShutdownTimeout()));
        }
        if (TempCarefreeAide.isNotNull(archetype.getClientName())) {
            builder.clientName(archetype.getClientName());
        }
        if (TempCarefreeAide.isNotNull(archetype.getReadFrom())) {
            builder.readFrom(ReadFrom.valueOf(archetype.getReadFrom()));
        }

        // TODO-Kweny useSsl, verifyPeer, startTls, clientName, timeout, shutdownTimeout, shutdownQuietPeriod, readFrom, clientResources, clientOptions
        clientConfiguration = builder.build();

        return clientConfiguration;
    }

    default GenericObjectPoolConfig<?> createPoolConfig(CarefreeRedisArchetypePool poolArchetype) {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        if (TempCarefreeAide.isNotNull(poolArchetype.getMaxTotal())) {
            poolConfig.setMaxTotal(poolArchetype.getMaxTotal());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getMaxIdle())) {
            poolConfig.setMaxIdle(poolArchetype.getMaxIdle());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getMinIdle())) {
            poolConfig.setMinIdle(poolArchetype.getMinIdle());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getLifo())) {
            poolConfig.setLifo(poolArchetype.getLifo());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getFairness())) {
            poolConfig.setFairness(poolArchetype.getFairness());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getBlockWhenExhausted())) {
            poolConfig.setBlockWhenExhausted(poolArchetype.getBlockWhenExhausted());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getMaxWaitMillis())) {
            poolConfig.setMaxWaitMillis(poolArchetype.getMaxWaitMillis());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getTestOnCreate())) {
            poolConfig.setTestOnCreate(poolArchetype.getTestOnCreate());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getTestOnBorrow())) {
            poolConfig.setTestOnBorrow(poolArchetype.getTestOnBorrow());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getTestOnReturn())) {
            poolConfig.setTestOnReturn(poolArchetype.getTestOnReturn());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getTestWhileIdle())) {
            poolConfig.setTestWhileIdle(poolArchetype.getTestWhileIdle());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getTimeBetweenEvictionRunsMillis())) {
            poolConfig.setTimeBetweenEvictionRunsMillis(poolArchetype.getTimeBetweenEvictionRunsMillis());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getNumTestsPerEvictionRun())) {
            poolConfig.setNumTestsPerEvictionRun(poolArchetype.getNumTestsPerEvictionRun());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getMinEvictableIdleTimeMillis())) {
            poolConfig.setMinEvictableIdleTimeMillis(poolArchetype.getMinEvictableIdleTimeMillis());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getSoftMinEvictableIdleTimeMillis())) {
            poolConfig.setSoftMinEvictableIdleTimeMillis(poolArchetype.getSoftMinEvictableIdleTimeMillis());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getEvictorShutdownTimeoutMillis())) {
            poolConfig.setEvictorShutdownTimeoutMillis(poolArchetype.getEvictorShutdownTimeoutMillis());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getEvictionPolicyClassName())) {
            poolConfig.setEvictionPolicyClassName(poolArchetype.getEvictionPolicyClassName());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getJmxEnabled())) {
            poolConfig.setJmxEnabled(poolArchetype.getJmxEnabled());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getJmxNameBase())) {
            poolConfig.setJmxNameBase(poolArchetype.getJmxNameBase());
        }
        if (TempCarefreeAide.isNotNull(poolArchetype.getJmxNamePrefix())) {
            poolConfig.setJmxNamePrefix(poolArchetype.getJmxNamePrefix());
        }
        return poolConfig;
    }

}
