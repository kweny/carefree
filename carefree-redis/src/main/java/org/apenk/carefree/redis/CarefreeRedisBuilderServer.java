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

import org.springframework.data.redis.connection.*;

import java.util.Arrays;

/**
 * redis 服务端集群方式
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CarefreeRedisBuilderServer {
    default RedisClusterConfiguration createClusterConfiguration(CarefreeRedisArchetype archetype) {
        RedisClusterConfiguration cluster = new RedisClusterConfiguration(Arrays.asList(archetype.getNodes().split(",")));
        if (archetype.getMaxRedirects() != null) {
            cluster.setMaxRedirects(archetype.getMaxRedirects());
        }
        if (archetype.getPassword() != null) {
            cluster.setPassword(archetype.getPassword());
        }
        return cluster;
    }

    default RedisStandaloneConfiguration createStandaloneConfiguration(CarefreeRedisArchetype archetype) {
        RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration();
        if (archetype.getHost() != null) {
            standalone.setHostName(archetype.getHost());
        }
        if (archetype.getPort() != null) {
            standalone.setPort(archetype.getPort());
        }
        if (archetype.getPassword() != null) {
            standalone.setPassword(archetype.getPassword());
        }
        if (archetype.getDatabase() != null) {
            standalone.setDatabase(archetype.getDatabase());
        }
        return standalone;
    }

    default RedisSentinelConfiguration createSentinelConfiguration(CarefreeRedisArchetype archetype) {
        RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration();
        // TODO-Kweny RedisSentinelConfiguration
        return sentinel;
    }

    default RedisSocketConfiguration createSocketConfiguration(CarefreeRedisArchetype archetype) {
        RedisSocketConfiguration socket = new RedisSocketConfiguration();
        // TODO-Kweny RedisSocketConfiguration
        return socket;
    }

    default RedisStaticMasterReplicaConfiguration createStaticMasterReplicaConfiguration(CarefreeRedisArchetype archetype) {
        RedisStaticMasterReplicaConfiguration staticMasterReplica = new RedisStaticMasterReplicaConfiguration("localhost", 6379);
        // TODO-Kweny RedisStaticMasterReplicaConfiguration
        return staticMasterReplica;
    }
}
