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

/**
 * Redis 配置原型
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetype {
    /**
     * 是否启用本配置，若 false 则不会创建对应的 RedisConnectionFactory 对象，但仍然可以被其它配置引用，默认为 true
     */
    private Boolean enabled = true;
    /**
     * 引用的另一个配置的 key，若本实例的某个属性未配置，则使用所引用配置的相应属性作为默认值
     */
    private String reference;
    /**
     * Standalone/Cluster/Sentinel/Socket/StaticMasterReplica
     */
    private String mode;
    private String clientName;
    private String host;
    private Integer port;
    private String nodes;
    private Integer maxRedirects;
    private String password;
    private Integer database;
    private Long commandTimeout;
    private Long shutdownTimeout;
    private Boolean usePooling;
    private Boolean useSsl;
    /**
     * master/masterPreferred/slave/slavePreferred/nearest
     */
    private String readFrom;
    private boolean disableDefaultSerializer;
    private String defaultSerializer;
    private String keySerializer;
    private String valueSerializer;
    private String hashKeySerializer;
    private String hashValueSerializer;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
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

    public Boolean getUsePooling() {
        return usePooling;
    }

    public void setUsePooling(Boolean usePooling) {
        this.usePooling = usePooling;
    }

    public Boolean getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(Boolean useSsl) {
        this.useSsl = useSsl;
    }

    public String getReadFrom() {
        return readFrom;
    }

    public void setReadFrom(String readFrom) {
        this.readFrom = readFrom;
    }

    public boolean isDisableDefaultSerializer() {
        return disableDefaultSerializer;
    }

    public void setDisableDefaultSerializer(boolean disableDefaultSerializer) {
        this.disableDefaultSerializer = disableDefaultSerializer;
    }

    public String getDefaultSerializer() {
        return defaultSerializer;
    }

    public void setDefaultSerializer(String defaultSerializer) {
        this.defaultSerializer = defaultSerializer;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getHashKeySerializer() {
        return hashKeySerializer;
    }

    public void setHashKeySerializer(String hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    public String getHashValueSerializer() {
        return hashValueSerializer;
    }

    public void setHashValueSerializer(String hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }
}