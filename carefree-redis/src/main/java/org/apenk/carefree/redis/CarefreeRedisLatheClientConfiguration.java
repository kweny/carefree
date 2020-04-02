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
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypePool;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeResources;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * @author Kweny
 * @since 0.0.1
 */
interface CarefreeRedisLatheClientConfiguration {

    default LettuceClientConfiguration createClientConfiguration(CarefreeRedisPayload payload) {
        LettuceClientConfiguration clientConfiguration;
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder;

        if (TempCarefreeAide.isNotFalse(payload.redisArchetype.getUsePooling())) {
            builder = LettucePoolingClientConfiguration.builder();
            GenericObjectPoolConfig<?> poolConfig = createPoolConfig(payload.poolArchetype);
            ((LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder) builder).poolConfig(poolConfig);
        } else {
            builder = LettuceClientConfiguration.builder();
        }

        if (TempCarefreeAide.isNotNull(payload.redisArchetype.getCommandTimeout())) {
            builder.commandTimeout(Duration.ofMillis(payload.redisArchetype.getCommandTimeout()));
        }
        if (TempCarefreeAide.isNotNull(payload.redisArchetype.getShutdownTimeout())) {
            builder.shutdownTimeout(Duration.ofMillis(payload.redisArchetype.getShutdownTimeout()));
        }
        if (TempCarefreeAide.isNotNull(payload.redisArchetype.getShutdownQuietPeriod())) {
            builder.shutdownQuietPeriod(Duration.ofMillis(payload.redisArchetype.getShutdownQuietPeriod()));
        }
        if (TempCarefreeAide.isNotNull(payload.redisArchetype.getClientName())) {
            builder.clientName(payload.redisArchetype.getClientName());
        }
        if (TempCarefreeAide.isNotNull(payload.redisArchetype.getReadFrom())) {
            builder.readFrom(ReadFrom.valueOf(payload.redisArchetype.getReadFrom()));
        }

        ClientResources clientResources = createClientResources(payload.resourcesArchetype);
        if (clientResources != null) {
            builder.clientResources(clientResources);
        }

        // TODO-Kweny clientOptions

        if (TempCarefreeAide.isTrue(payload.redisArchetype.getUseSsl())) {
            LettuceClientConfiguration.LettuceSslClientConfigurationBuilder sslBuilder = builder.useSsl();
            if (TempCarefreeAide.isTrue(payload.redisArchetype.getStartTls())) {
                sslBuilder.startTls();
            }
            if (TempCarefreeAide.isFalse(payload.redisArchetype.getVerifyPeer())) {
                sslBuilder.disablePeerVerification();
            }
            clientConfiguration = sslBuilder.build();
        } else {
            clientConfiguration = builder.build();
        }

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

    default ClientResources createClientResources(CarefreeRedisArchetypeResources resourcesArchetype) {
        boolean nonResources = true;
        ClientResources.Builder builder = ClientResources.builder();

        if (TempCarefreeAide.isNotNull(resourcesArchetype.getIoThreadPoolSize())) {
            nonResources = false;
            builder.ioThreadPoolSize(resourcesArchetype.getIoThreadPoolSize());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getComputationThreadPoolSize())) {
            nonResources = false;
            builder.computationThreadPoolSize(resourcesArchetype.getComputationThreadPoolSize());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventLoopGroupProvider())) {
            nonResources = false;
            builder.eventLoopGroupProvider(resourcesArchetype.getEventLoopGroupProvider().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventExecutorGroup())) {
            nonResources = false;
            builder.eventExecutorGroup(resourcesArchetype.getEventExecutorGroup().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getTimer())) {
            nonResources = false;
            builder.timer(resourcesArchetype.getTimer().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventBus())) {
            nonResources = false;
            builder.eventBus(resourcesArchetype.getEventBus().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyPublisherOptions())) {
            nonResources = false;
            builder.commandLatencyPublisherOptions(resourcesArchetype.getCommandLatencyPublisherOptions().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyCollectorOptions())) {
            nonResources = false;
            builder.commandLatencyCollectorOptions(resourcesArchetype.getCommandLatencyCollectorOptions().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyCollector())) {
            nonResources = false;
            builder.commandLatencyCollector(resourcesArchetype.getCommandLatencyCollector().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getDnsResolver())) {
            nonResources = false;
            builder.dnsResolver(resourcesArchetype.getDnsResolver().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getReconnectDelay())) {
            nonResources = false;
            builder.reconnectDelay(() -> resourcesArchetype.getReconnectDelay().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getNettyCustomizer())) {
            nonResources = false;
            builder.nettyCustomizer(resourcesArchetype.getNettyCustomizer().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getTracing())) {
            nonResources = false;
            builder.tracing(resourcesArchetype.getTracing().instance());
        }

        return nonResources ? null : builder.build();
    }
}
