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

/**
 * Redis 配置
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetype1 {
    /**
     * 是否启用本配置，若 false 则不会创建对应的 RedisConnectionFactory 对象，但仍然可以被其它配置引用，默认为 true
     */
    private Boolean enabled = true;
    /**
     * 引用的另一个配置的 key，若本实例的某个属性未配置，则使用所引用配置的相应属性作为默认值
     */
    private String reference;

    /**
     * Redis 服务高可用及连接方式
     *
     * Standalone：单节点
     * Socket：使用 unix socket 方式连接
     * Sentinel：哨兵模式 https://github.com/lettuce-io/lettuce-core/wiki/Redis-Sentinel
     * Cluster：集群分片 https://github.com/lettuce-io/lettuce-core/wiki/Redis-Cluster
     * StaticMasterReplica：静态主从 https://github.com/lettuce-io/lettuce-core/wiki/Master-Replica
     */
    private String mode;
    /**
     * 设置在什么节点执行读取操作。
     *
     * master：默认，在主节点读取数据；
     * masterPreferred：首选主节点，当主节点不可用则在副本节点读取；
     * slave/replica：在副本节点读取；
     * slavePreferred/replicaPreferred：首选副本节点，当副本节点不可用则在主节点读取；
     * nearest：在集群中延迟最小的节点读取；
     * any：在任意节点读取。
     *
     * 默认为 master，即从主节点读取数据，由于写操作总是会发布到主节点，因此从 master 总会读取到最新数据，可以保证强一致性。
     * 若改为其它方式需要注意：由于节点之间的数据同步是异步的，因此无法保证能读取到最新数据。
     *
     * https://github.com/lettuce-io/lettuce-core/wiki/ReadFrom-Settings
     */
    private String readFrom;
    /**
     * 使用 CLIENT SETNAME 命令设置客户端名称
     */
    private String clientName;
    /**
     * database 索引
     */
    private Integer database;
    /**
     * 连接 URL，将覆盖 host、port、password，如：redis://user:password@example.com:6379，忽略其中的 user 部分。
     */
    private String url;
    /**
     * Redis 服务地址
     */
    private String host;
    /**
     * Redis 服务端口
     */
    private Integer port;
    /**
     * Redis 服务密码
     */
    private String password;
    /**
     * 是否启用 SSL
     */
    private Boolean ssl;
    /**
     * 是否启用对等验证
     */
    private Boolean verifyPeer;
    /**
     * 是否启用 StartTLS
     */
    private Boolean startTls;
    /**
     * 命令执行超时时间，毫秒
     */
    private Long commandTimeout;
    /**
     * 客户端关闭最大等待时间
     */
    private Long shutdownTimeout;
    /**
     * 客户端优雅关闭静默时间，必须 <= shutdownTimeout
     */
    private Long shutdownQuietPeriod;

    public static class Sentinel {

    }

    public static class Cluster {

    }

    public static class StaticMasterReplica {

    }
}
