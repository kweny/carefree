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

import org.apenk.carefree.helper.CarefreeClassDeclaration;

import java.util.Set;

/**
 * <p>客户端功能选项描述。</p>
 *
 * <p>用于描述 {@link io.lettuce.core.ClientOptions} 对象。</p>
 *
 * <p>
 *     参考：
 *     <a href="https://github.com/lettuce-io/lettuce-core/wiki/Client-Options">
 *         https://github.com/lettuce-io/lettuce-core/wiki/Client-Options
 *     </a>
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetypeOptions {
    // ----- ClientOptions
    /**
     * 连接激活前是否执行 ping 命令，
     * 如果取 true，则每次连接或重连之前都会执行 ping 命令并等待响应，
     * 如果 ping 失败，则视为连接或重连失败。
     * 默认 false
     */
    private Boolean pingBeforeActivateConnection;
    /**
     * 是否自动重连，
     * 默认 true
     */
    private Boolean autoReconnect;
    /**
     * 重连失败时是否取消队列中的命令，
     * 默认 false
     */
    private Boolean cancelCommandsOnReconnectFailure;
    /**
     * 是否使用专用的 {@link reactor.core.scheduler.Scheduler} 调度器发出响应式数据信号，
     * 默认 false
     */
    private Boolean publishOnScheduler;
    /**
     * 是否在发生协议错误时暂停重新连接，协议错误指 SSL 协商错误或 ping 命令失败错误，
     * 默认 false
     */
    private Boolean suspendReconnectOnProtocolFailure;
    /**
     * 每个连接的请求队列大小，如果超出队列上限且请求了新命令，将抛出 {@link io.lettuce.core.RedisException}，
     * 默认 {@link Integer#MAX_VALUE}
     */
    private Integer requestQueueSize;
    /**
     * 连接处于断开状态时命令调用的行为，
     * DEFAULT：如果启用了自动重连，将接受命令，否则将拒绝命令。
     * ACCEPT_COMMANDS：始终接受。
     * REJECT_COMMANDS：始终拒绝。
     * 默认 DEFAULT
     */
    private String disconnectedBehavior;
    /**
     * {@link io.lettuce.core.protocol.CommandHandler} 的缓冲区使用率。
     * 该比率控制在解码过程中丢弃字节的频率，特别是当缓冲区使用量达到 {@code bufferUsageRatio / (bufferUsageRatio + 1)} 时。
     * 例如将 bufferUsageRatio 设置为 3，将在缓冲区使用率达到 75％ 时丢弃读取的字节。
     * 默认 3
     */
    private Integer bufferUsageRatio;

    // ----- ClientOptions.SocketOptions
    private Long connectTimeout;
    private Boolean keepAlive;
    private Boolean tcpNoDelay;

    // ----- ClientOptions.SslOptions
    private String sslProvider;
    private String keystore;
    private String keystorePassword;
    private String truststore;
    private String truststorePassword;

    // ----- ClientOptions.TimeoutOptions
    private Boolean timeoutCommands;
    private Boolean applyConnectionTimeout;
    private CarefreeClassDeclaration timeoutSource;

    // ----- ClusterClientOptions
    private Boolean validateClusterNodeMembership;
    private Boolean maxRedirects;

    // ----- ClusterClientOptions.ClusterTopologyRefreshOptions
    private Boolean periodicRefreshEnabled;
    private Long refreshPeriod;
    private Boolean closeStaleConnections;
    private Boolean dynamicRefreshSources;
    private Set<String> adaptiveRefreshTriggers;
    private Long adaptiveRefreshTimeout;
    private Integer refreshTriggersReconnectAttempts;

    public Boolean getPingBeforeActivateConnection() {
        return pingBeforeActivateConnection;
    }

    public void setPingBeforeActivateConnection(Boolean pingBeforeActivateConnection) {
        this.pingBeforeActivateConnection = pingBeforeActivateConnection;
    }

    public Boolean getAutoReconnect() {
        return autoReconnect;
    }

    public void setAutoReconnect(Boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    public Boolean getCancelCommandsOnReconnectFailure() {
        return cancelCommandsOnReconnectFailure;
    }

    public void setCancelCommandsOnReconnectFailure(Boolean cancelCommandsOnReconnectFailure) {
        this.cancelCommandsOnReconnectFailure = cancelCommandsOnReconnectFailure;
    }

    public Boolean getPublishOnScheduler() {
        return publishOnScheduler;
    }

    public void setPublishOnScheduler(Boolean publishOnScheduler) {
        this.publishOnScheduler = publishOnScheduler;
    }

    public Boolean getSuspendReconnectOnProtocolFailure() {
        return suspendReconnectOnProtocolFailure;
    }

    public void setSuspendReconnectOnProtocolFailure(Boolean suspendReconnectOnProtocolFailure) {
        this.suspendReconnectOnProtocolFailure = suspendReconnectOnProtocolFailure;
    }

    public Integer getRequestQueueSize() {
        return requestQueueSize;
    }

    public void setRequestQueueSize(Integer requestQueueSize) {
        this.requestQueueSize = requestQueueSize;
    }

    public String getDisconnectedBehavior() {
        return disconnectedBehavior;
    }

    public void setDisconnectedBehavior(String disconnectedBehavior) {
        this.disconnectedBehavior = disconnectedBehavior;
    }

    public Integer getBufferUsageRatio() {
        return bufferUsageRatio;
    }

    public void setBufferUsageRatio(Integer bufferUsageRatio) {
        this.bufferUsageRatio = bufferUsageRatio;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(Boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public String getSslProvider() {
        return sslProvider;
    }

    public void setSslProvider(String sslProvider) {
        this.sslProvider = sslProvider;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getTruststore() {
        return truststore;
    }

    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

    public void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }

    public Boolean getTimeoutCommands() {
        return timeoutCommands;
    }

    public void setTimeoutCommands(Boolean timeoutCommands) {
        this.timeoutCommands = timeoutCommands;
    }

    public Boolean getApplyConnectionTimeout() {
        return applyConnectionTimeout;
    }

    public void setApplyConnectionTimeout(Boolean applyConnectionTimeout) {
        this.applyConnectionTimeout = applyConnectionTimeout;
    }

    public CarefreeClassDeclaration getTimeoutSource() {
        return timeoutSource;
    }

    public void setTimeoutSource(CarefreeClassDeclaration timeoutSource) {
        this.timeoutSource = timeoutSource;
    }

    public Boolean getValidateClusterNodeMembership() {
        return validateClusterNodeMembership;
    }

    public void setValidateClusterNodeMembership(Boolean validateClusterNodeMembership) {
        this.validateClusterNodeMembership = validateClusterNodeMembership;
    }

    public Boolean getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Boolean maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public Boolean getPeriodicRefreshEnabled() {
        return periodicRefreshEnabled;
    }

    public void setPeriodicRefreshEnabled(Boolean periodicRefreshEnabled) {
        this.periodicRefreshEnabled = periodicRefreshEnabled;
    }

    public Long getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(Long refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }

    public Boolean getCloseStaleConnections() {
        return closeStaleConnections;
    }

    public void setCloseStaleConnections(Boolean closeStaleConnections) {
        this.closeStaleConnections = closeStaleConnections;
    }

    public Boolean getDynamicRefreshSources() {
        return dynamicRefreshSources;
    }

    public void setDynamicRefreshSources(Boolean dynamicRefreshSources) {
        this.dynamicRefreshSources = dynamicRefreshSources;
    }

    public Set<String> getAdaptiveRefreshTriggers() {
        return adaptiveRefreshTriggers;
    }

    public void setAdaptiveRefreshTriggers(Set<String> adaptiveRefreshTriggers) {
        this.adaptiveRefreshTriggers = adaptiveRefreshTriggers;
    }

    public Long getAdaptiveRefreshTimeout() {
        return adaptiveRefreshTimeout;
    }

    public void setAdaptiveRefreshTimeout(Long adaptiveRefreshTimeout) {
        this.adaptiveRefreshTimeout = adaptiveRefreshTimeout;
    }

    public Integer getRefreshTriggersReconnectAttempts() {
        return refreshTriggersReconnectAttempts;
    }

    public void setRefreshTriggersReconnectAttempts(Integer refreshTriggersReconnectAttempts) {
        this.refreshTriggersReconnectAttempts = refreshTriggersReconnectAttempts;
    }
}
