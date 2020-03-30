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

import org.apenk.carefree.archetype.CarefreeArchetype;

import java.util.List;

/**
 * Redis 配置原型
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetype extends CarefreeArchetype {

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
     * 当 {@link #mode} 取值 Socket 时有效，默认 /tmp/redis.sock
     */
    private String socket;
    /**
     * 采用 sentinel HA 方案时，指定 master 名称，
     * 当 {@link #mode} 取值 Sentinel 时有效。
     */
    private String master;
    /**
     * Redis 服务节点列表，每个节点为 “host[:port]” 格式，其中 port 缺省为 6379，
     * 当 {@link #mode} 取值 Sentinel/Cluster/StaticMasterReplica 时有效。
     */
    private List<String> nodes;
    /**
     * 一条 Redis 命令在整个集群中的最大重定向次数，
     * 当 {@link #mode} 取值 Cluster 时有效。
     */
    private Integer maxRedirects;
    /**
     * 采用 sentinel HA 方案时，Redis Sentinel 的密码，
     * 当 {@link #mode} 取值 Sentinel 时有效。
     */
    private String sentinelPassword;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReadFrom() {
        return readFrom;
    }

    public void setReadFrom(String readFrom) {
        this.readFrom = readFrom;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getSentinelPassword() {
        return sentinelPassword;
    }

    public void setSentinelPassword(String sentinelPassword) {
        this.sentinelPassword = sentinelPassword;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Boolean getVerifyPeer() {
        return verifyPeer;
    }

    public void setVerifyPeer(Boolean verifyPeer) {
        this.verifyPeer = verifyPeer;
    }

    public Boolean getStartTls() {
        return startTls;
    }

    public void setStartTls(Boolean startTls) {
        this.startTls = startTls;
    }

    public Long getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(Long commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public Long getShutdownTimeout() {
        return shutdownTimeout;
    }

    public void setShutdownTimeout(Long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public Long getShutdownQuietPeriod() {
        return shutdownQuietPeriod;
    }

    public void setShutdownQuietPeriod(Long shutdownQuietPeriod) {
        this.shutdownQuietPeriod = shutdownQuietPeriod;
    }
}