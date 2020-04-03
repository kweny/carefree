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

import io.lettuce.core.*;
import io.lettuce.core.resource.ClientResources;
import io.netty.handler.ssl.SslProvider;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeOptions;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypePool;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeResources;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
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
        boolean configured = false;
        ClientResources.Builder builder = ClientResources.builder();

        if (TempCarefreeAide.isNotNull(resourcesArchetype.getIoThreadPoolSize())) {
            configured = true;
            builder.ioThreadPoolSize(resourcesArchetype.getIoThreadPoolSize());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getComputationThreadPoolSize())) {
            configured = true;
            builder.computationThreadPoolSize(resourcesArchetype.getComputationThreadPoolSize());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventLoopGroupProvider())) {
            configured = true;
            builder.eventLoopGroupProvider(resourcesArchetype.getEventLoopGroupProvider().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventExecutorGroup())) {
            configured = true;
            builder.eventExecutorGroup(resourcesArchetype.getEventExecutorGroup().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getTimer())) {
            configured = true;
            builder.timer(resourcesArchetype.getTimer().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getEventBus())) {
            configured = true;
            builder.eventBus(resourcesArchetype.getEventBus().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyPublisherOptions())) {
            configured = true;
            builder.commandLatencyPublisherOptions(resourcesArchetype.getCommandLatencyPublisherOptions().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyCollectorOptions())) {
            configured = true;
            builder.commandLatencyCollectorOptions(resourcesArchetype.getCommandLatencyCollectorOptions().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getCommandLatencyCollector())) {
            configured = true;
            builder.commandLatencyCollector(resourcesArchetype.getCommandLatencyCollector().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getDnsResolver())) {
            configured = true;
            builder.dnsResolver(resourcesArchetype.getDnsResolver().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getReconnectDelay())) {
            configured = true;
            builder.reconnectDelay(() -> resourcesArchetype.getReconnectDelay().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getNettyCustomizer())) {
            configured = true;
            builder.nettyCustomizer(resourcesArchetype.getNettyCustomizer().instance());
        }
        if (TempCarefreeAide.isNotNull(resourcesArchetype.getTracing())) {
            configured = true;
            builder.tracing(resourcesArchetype.getTracing().instance());
        }

        return configured ? builder.build() : null;
    }

    default ClientOptions createClientOptions(CarefreeRedisArchetypeOptions optionsArchetype) {
        // ClientOptions
        boolean configured = false;
        ClientOptions.Builder builder = ClientOptions.builder();
        if (TempCarefreeAide.isNotNull(optionsArchetype.getPingBeforeActivateConnection())) {
            configured = true;
            builder.pingBeforeActivateConnection(optionsArchetype.getPingBeforeActivateConnection());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getAutoReconnect())) {
            configured = true;
            builder.autoReconnect(optionsArchetype.getAutoReconnect());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getCancelCommandsOnReconnectFailure())) {
            configured = true;
            builder.cancelCommandsOnReconnectFailure(optionsArchetype.getCancelCommandsOnReconnectFailure());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getPublishOnScheduler())) {
            configured = true;
            builder.publishOnScheduler(optionsArchetype.getPublishOnScheduler());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getSuspendReconnectOnProtocolFailure())) {
            configured = true;
            builder.suspendReconnectOnProtocolFailure(optionsArchetype.getSuspendReconnectOnProtocolFailure());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getRequestQueueSize())) {
            configured = true;
            builder.requestQueueSize(optionsArchetype.getRequestQueueSize());
        }
        if (TempCarefreeAide.isNotBlank(optionsArchetype.getDisconnectedBehavior())) {
            ClientOptions.DisconnectedBehavior behavior = resolveEnumItem(ClientOptions.DisconnectedBehavior.class, optionsArchetype.getDisconnectedBehavior());
            if (behavior != null) {
                configured = true;
                builder.disconnectedBehavior(behavior);
            }
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getBufferUsageRatio())) {
            configured = true;
            builder.bufferUsageRatio(optionsArchetype.getBufferUsageRatio());
        }

        // ClientOptions.SocketOptions
        boolean socketConfigured = false;
        SocketOptions.Builder socketBuilder = SocketOptions.builder();
        if (TempCarefreeAide.isNotNull(optionsArchetype.getConnectTimeout())) {
            socketConfigured = true;
            socketBuilder.connectTimeout(Duration.ofMillis(optionsArchetype.getConnectTimeout()));
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getKeepAlive())) {
            socketConfigured = true;
            socketBuilder.keepAlive(optionsArchetype.getKeepAlive());
        }
        if (TempCarefreeAide.isNotNull(optionsArchetype.getTcpNoDelay())) {
            socketConfigured = true;
            socketBuilder.tcpNoDelay(optionsArchetype.getTcpNoDelay());
        }
        if (socketConfigured) {
            configured = true;
            builder.socketOptions(socketBuilder.build());
        }

        // ClientOptions.SslOptions
        boolean sslConfigured = false;
        SslOptions.Builder sslBuilder = SslOptions.builder();
        if (TempCarefreeAide.isNotBlank(optionsArchetype.getSslProvider())) {
            SslProvider provider = resolveEnumItem(SslProvider.class, optionsArchetype.getSslProvider());
            if (provider == SslProvider.JDK) {
                sslConfigured = true;
                sslBuilder.jdkSslProvider();
            }
            if (provider == SslProvider.OPENSSL) {
                sslConfigured = true;
                sslBuilder.openSslProvider();
            }
        }
        if (TempCarefreeAide.isNotBlank(optionsArchetype.getKeystore())) {
            sslConfigured = true;
            try {
                char[] password = TempCarefreeAide.isEmpty(optionsArchetype.getKeystorePassword()) ? new char[0] : optionsArchetype.getKeystorePassword().toCharArray();
                sslBuilder.keystore(new URL(optionsArchetype.getKeystore()), password);
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("[Carefree] Malformed url '" + optionsArchetype.getKeystore() + "'", e);
            }
        }
        if (TempCarefreeAide.isNotBlank(optionsArchetype.getTruststore())) {
            sslConfigured = true;
            try {
                sslBuilder.truststore(new URL(optionsArchetype.getTruststore()), optionsArchetype.getTruststorePassword());
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("[Carefree] Malformed url '" + optionsArchetype.getKeystore() + "'", e);
            }
        }
        if (sslConfigured) {
            configured = true;
            builder.sslOptions(sslBuilder.build());
        }

        // ClientOptions.TimeoutOptions
        boolean timeoutConfigured = false;
        TimeoutOptions.Builder timeoutBuilder = TimeoutOptions.builder();
        if (TempCarefreeAide.isNotNull(optionsArchetype.getTimeoutCommands())) {
            timeoutConfigured = true;
            timeoutBuilder.timeoutCommands(optionsArchetype.getTimeoutCommands());
        }

        return configured ? builder.build() : null;
    }



    default <T extends Enum<T>> T resolveEnumItem(Class<T> enumClazz, String name) {
        T[] items = enumClazz.getEnumConstants();
        for (T item : items) {
            if (TempCarefreeAide.equalsIgnoreCase(name, item.name())) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new Test().resolveEnumItem(ClientOptions.DisconnectedBehavior.class, "default"));
    }

    public static class Test implements CarefreeRedisLatheClientConfiguration {

    }
}
