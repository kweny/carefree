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

import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.redis.archetype.CarefreeRedisArchetype;
import org.springframework.data.redis.connection.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO-Kweny CarefreeRedisLatheModeConfiguration
 *
 * @author Kweny
 * @since 0.0.1
 */
interface CarefreeRedisLatheModeConfiguration {

    String MODE_Standalone = "Standalone";
    String MODE_Socket = "Socket";
    String MODE_Cluster = "Cluster";
    String MODE_Sentinel = "Sentinel";
    String MODE_StaticMasterReplica  = "StaticMasterReplica";

    default RedisConfiguration createRedisConfiguration(CarefreeRedisPayload payload) {

    }

    default RedisSentinelConfiguration createSentinelConfiguration(CarefreeRedisArchetype archetype) {
        RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration(archetype.getMaster(), archetype.getNodes());
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            sentinel.setPassword(archetype.getPassword());
        }
        if (TempCarefreeAide.isNotNull(archetype.getSentinelPassword())) {
            sentinel.setSentinelPassword(archetype.getSentinelPassword());
        }
        if (TempCarefreeAide.isNotNull(archetype.getDatabase())) {
            sentinel.setDatabase(archetype.getDatabase());
        }
        return sentinel;
    }

    default RedisClusterConfiguration createClusterConfiguration(CarefreeRedisArchetype archetype) {
        RedisClusterConfiguration cluster = new RedisClusterConfiguration(archetype.getNodes());
        if (TempCarefreeAide.isNotNull(archetype.getMaxRedirects())) {
            cluster.setMaxRedirects(archetype.getMaxRedirects());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            cluster.setPassword(archetype.getPassword());
        }
        return cluster;
    }

    default RedisStaticMasterReplicaConfiguration createStaticMasterReplicaConfiguration(CarefreeRedisArchetype archetype) {
        Set<RedisStandaloneConfiguration> nodes = archetype.getNodes().stream()
                                                    .map(str -> str.split(":"))
                                                    .map(arr -> new RedisStandaloneConfiguration(arr[0], Integer.parseInt(arr[1])))
                                                    .collect(Collectors.toSet());
        RedisStaticMasterReplicaConfiguration staticMasterReplica = new RedisStaticMasterReplicaConfiguration();
        // TODO-Kweny RedisStaticMasterReplicaConfiguration
        return staticMasterReplica;
    }

    default RedisStandaloneConfiguration createStandaloneConfiguration(CarefreeRedisArchetype archetype) {
        RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration();

        String hostname;
        Integer port;
        String password = null;

        if (TempCarefreeAide.isNotBlank(archetype.getUrl())) {
            try {
                URI uri = new URI(archetype.getUrl());
                if (uri.getUserInfo() != null) {
                    password = uri.getUserInfo();
                    int index = password.indexOf(':');
                    if (index >= 0) {
                        password = password.substring(index + 1);
                    }
                }
                hostname = uri.getHost();
                port = uri.getPort();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Malformed url '" + archetype.getUrl() + "'", e);
            }
        } else {
            hostname = archetype.getHost();
            port = archetype.getPort();
            password = archetype.getPassword();
        }

        if (TempCarefreeAide.isNotNull(hostname)) {
            standalone.setHostName(hostname);
        }
        if (TempCarefreeAide.isNotNull(port)) {
            standalone.setPort(port);
        }
        if (TempCarefreeAide.isNotNull(password)) {
            standalone.setPassword(password);
        }
        if (TempCarefreeAide.isNotNull(archetype.getDatabase())) {
            standalone.setDatabase(archetype.getDatabase());
        }

        return standalone;
    }

    default RedisSocketConfiguration createSocketConfiguration(CarefreeRedisArchetype archetype) {
        RedisSocketConfiguration socket = new RedisSocketConfiguration();
        // TODO-Kweny RedisSocketConfiguration
        return socket;
    }



}
