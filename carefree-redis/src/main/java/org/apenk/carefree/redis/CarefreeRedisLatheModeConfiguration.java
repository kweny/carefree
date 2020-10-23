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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kweny
 * @since 0.0.1
 */
interface CarefreeRedisLatheModeConfiguration {

    String MODE_Sentinel = "Sentinel";
    String MODE_Cluster = "Cluster";
    String MODE_StaticMasterReplica  = "StaticMasterReplica";
    String MODE_Socket = "Socket";
    String MODE_Standalone = "Standalone";

    default RedisConfiguration createRedisConfiguration(CarefreeRedisPayload payload) {
        String mode = payload.redisArchetype.getMode();
        if (TempCarefreeAide.equalsIgnoreCase(mode, MODE_Sentinel)) {
            return createSentinelConfiguration(payload.redisArchetype);

        } else if (TempCarefreeAide.equalsIgnoreCase(mode, MODE_Cluster)) {
            return createClusterConfiguration(payload.redisArchetype);

        } else if (TempCarefreeAide.equalsIgnoreCase(mode, MODE_StaticMasterReplica)) {
            return createStaticMasterReplicaConfiguration(payload.redisArchetype);

        } else if (TempCarefreeAide.equalsIgnoreCase(mode, MODE_Socket)) {
            return createSocketConfiguration(payload.redisArchetype);

        } else if (TempCarefreeAide.equalsIgnoreCase(mode, MODE_Standalone)) {
            return createStandaloneConfiguration(payload.redisArchetype);

        } else {
            return createStandaloneConfiguration(payload.redisArchetype);

        }
    }

    default Set<String> parseNodeAddresses(String nodes) {
        return Arrays.stream(nodes.split(",")).map(TempCarefreeAide::trim).collect(Collectors.toSet());
    }

    default RedisSentinelConfiguration createSentinelConfiguration(CarefreeRedisArchetype archetype) {

        RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration(archetype.getMaster(), parseNodeAddresses(archetype.getNodes()));
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
        RedisClusterConfiguration cluster = new RedisClusterConfiguration(parseNodeAddresses(archetype.getNodes()));
        if (TempCarefreeAide.isNotNull(archetype.getMaxRedirects())) {
            cluster.setMaxRedirects(archetype.getMaxRedirects());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            cluster.setPassword(archetype.getPassword());
        }
        return cluster;
    }

    default RedisStaticMasterReplicaConfiguration createStaticMasterReplicaConfiguration(CarefreeRedisArchetype archetype) {
        List<RedisStandaloneConfiguration> nodes = new LinkedList<>();
        if (TempCarefreeAide.isEmpty(archetype.getNodes())) {
            nodes.add(new RedisStandaloneConfiguration());
        } else {
            for (String hostAndPort : parseNodeAddresses(archetype.getNodes())) {
                String[] sections = TempCarefreeAide.split(hostAndPort, ":");
                if (TempCarefreeAide.getLength(sections) != 2) {
                    throw new IllegalArgumentException("[Carefree] Host and Port String needs to specified as host:port");
                }
                String host = sections[0];
                int port;
                try {
                    port = Integer.parseInt(sections[1]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("[Carefree] Host and Port String needs to specified as host:port", e);
                }
                nodes.add(new RedisStandaloneConfiguration(host, port));
            }
        }
        RedisStaticMasterReplicaConfiguration staticMasterReplica = new RedisStaticMasterReplicaConfiguration(nodes.get(0).getHostName(), nodes.get(0).getPort());
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            staticMasterReplica.setPassword(archetype.getPassword());
        }
        if (TempCarefreeAide.isNotNull(archetype.getDatabase())) {
            staticMasterReplica.setDatabase(archetype.getDatabase());
        }
        if (nodes.size() > 1) {
            for (int i = 1; i < nodes.size(); i++) {
                staticMasterReplica.addNode(nodes.get(i).getHostName(), nodes.get(i).getPort());
            }
        }
        return staticMasterReplica;
    }


    default RedisSocketConfiguration createSocketConfiguration(CarefreeRedisArchetype archetype) {
        RedisSocketConfiguration socket = new RedisSocketConfiguration();
        if (TempCarefreeAide.isNotNull(archetype.getSocket())) {
            socket.setSocket(archetype.getSocket());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            socket.setPassword(archetype.getPassword());
        }
        if (TempCarefreeAide.isNotNull(archetype.getDatabase())) {
            socket.setDatabase(archetype.getDatabase());
        }
        return socket;
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
                throw new IllegalArgumentException("[Carefree] Malformed url '" + archetype.getUrl() + "'", e);
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

}
