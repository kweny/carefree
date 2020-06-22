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

package org.apenk.carefree.rabbitmq.archetype;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.ErrorOnWriteListener;
import com.rabbitmq.client.impl.nio.NioParams;
import com.rabbitmq.client.impl.recovery.RetryHandler;
import com.rabbitmq.client.impl.recovery.TopologyRecoveryFilter;
import org.apenk.carefree.archetype.CarefreeArchetype;
import org.apenk.carefree.helper.CarefreeClassDeclaration;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.function.Predicate;

/**
 * TODO-Kweny CarefreeRabbitArchetype
 * com.rabbitmq.client.ConnectionFactory
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRabbitArchetype extends CarefreeArchetype {
    private String virtualHost; //
    private String host; //
    private Integer port; //
    private String username; // credentialsProvider
    private String password; // credentialsProvider
    private CarefreeClassDeclaration credentialsProvider;
    private String uri;

    private Integer requestedChannelMax; //
    private Integer requestedFrameMax; //
    private Integer requestedHeartbeat; //
    private Integer connectionTimeout; //
    private Integer handshakeTimeout;
    private Integer shutdownTimeout;
    private Map<String, Object> clientProperties; //
    private CarefreeClassDeclaration socketFactory; //
    private CarefreeClassDeclaration saslConfig; //


    private ExecutorService sharedExecutor; //
    private ThreadFactory threadFactory; //
    private ExecutorService shutdownExecutor;
    private ScheduledExecutorService heartbeatExecutor;
    private SocketConfigurator socketConfigurator; //
    private ExceptionHandler exceptionHandler; //

    private Boolean automaticRecovery; //
    private Boolean topologyRecovery; //
    private ExecutorService topologyRecoveryExecutor;

    private Long networkRecoveryInterval;
    private RecoveryDelayHandler recoveryDelayHandler;

    private MetricsCollector metricsCollector;

    private Boolean nio; //
    private CarefreeClassDeclaration frameHandlerFactory;
    private NioParams nioParams; //

    private SslContextFactory sslContextFactory;

    private Integer channelRpcTimeout; //

    private Boolean channelShouldCheckRpcResponseType;

    private ErrorOnWriteListener errorOnWriteListener;

    private Integer workPoolTimeout;

    private TopologyRecoveryFilter topologyRecoveryFilter;

    private Predicate<ShutdownSignalException> connectionRecoveryTriggeringCondition;

    private RetryHandler topologyRecoveryRetryHandler;

    private TrafficListener trafficListener;

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CarefreeClassDeclaration getCredentialsProvider() {
        return credentialsProvider;
    }

    public void setCredentialsProvider(CarefreeClassDeclaration credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getRequestedChannelMax() {
        return requestedChannelMax;
    }

    public void setRequestedChannelMax(Integer requestedChannelMax) {
        this.requestedChannelMax = requestedChannelMax;
    }

    public Integer getRequestedFrameMax() {
        return requestedFrameMax;
    }

    public void setRequestedFrameMax(Integer requestedFrameMax) {
        this.requestedFrameMax = requestedFrameMax;
    }

    public Integer getRequestedHeartbeat() {
        return requestedHeartbeat;
    }

    public void setRequestedHeartbeat(Integer requestedHeartbeat) {
        this.requestedHeartbeat = requestedHeartbeat;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getHandshakeTimeout() {
        return handshakeTimeout;
    }

    public void setHandshakeTimeout(Integer handshakeTimeout) {
        this.handshakeTimeout = handshakeTimeout;
    }

    public Integer getShutdownTimeout() {
        return shutdownTimeout;
    }

    public void setShutdownTimeout(Integer shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public Map<String, Object> getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(Map<String, Object> clientProperties) {
        this.clientProperties = clientProperties;
    }

    public CarefreeClassDeclaration getSocketFactory() {
        return socketFactory;
    }

    public void setSocketFactory(CarefreeClassDeclaration socketFactory) {
        this.socketFactory = socketFactory;
    }

    public Boolean getAutomaticRecovery() {
        return automaticRecovery;
    }

    public void setAutomaticRecovery(Boolean automaticRecovery) {
        this.automaticRecovery = automaticRecovery;
    }

    public Boolean getTopologyRecovery() {
        return topologyRecovery;
    }

    public void setTopologyRecovery(Boolean topologyRecovery) {
        this.topologyRecovery = topologyRecovery;
    }

    public ExecutorService getTopologyRecoveryExecutor() {
        return topologyRecoveryExecutor;
    }

    public void setTopologyRecoveryExecutor(ExecutorService topologyRecoveryExecutor) {
        this.topologyRecoveryExecutor = topologyRecoveryExecutor;
    }

    public Long getNetworkRecoveryInterval() {
        return networkRecoveryInterval;
    }

    public void setNetworkRecoveryInterval(Long networkRecoveryInterval) {
        this.networkRecoveryInterval = networkRecoveryInterval;
    }

    public RecoveryDelayHandler getRecoveryDelayHandler() {
        return recoveryDelayHandler;
    }

    public void setRecoveryDelayHandler(RecoveryDelayHandler recoveryDelayHandler) {
        this.recoveryDelayHandler = recoveryDelayHandler;
    }

    public MetricsCollector getMetricsCollector() {
        return metricsCollector;
    }

    public void setMetricsCollector(MetricsCollector metricsCollector) {
        this.metricsCollector = metricsCollector;
    }

    public Boolean getNio() {
        return nio;
    }

    public void setNio(Boolean nio) {
        this.nio = nio;
    }

    public CarefreeClassDeclaration getFrameHandlerFactory() {
        return frameHandlerFactory;
    }

    public void setFrameHandlerFactory(CarefreeClassDeclaration frameHandlerFactory) {
        this.frameHandlerFactory = frameHandlerFactory;
    }

    public NioParams getNioParams() {
        return nioParams;
    }

    public void setNioParams(NioParams nioParams) {
        this.nioParams = nioParams;
    }

    public SslContextFactory getSslContextFactory() {
        return sslContextFactory;
    }

    public void setSslContextFactory(SslContextFactory sslContextFactory) {
        this.sslContextFactory = sslContextFactory;
    }

    public Integer getChannelRpcTimeout() {
        return channelRpcTimeout;
    }

    public void setChannelRpcTimeout(Integer channelRpcTimeout) {
        this.channelRpcTimeout = channelRpcTimeout;
    }

    public Boolean getChannelShouldCheckRpcResponseType() {
        return channelShouldCheckRpcResponseType;
    }

    public void setChannelShouldCheckRpcResponseType(Boolean channelShouldCheckRpcResponseType) {
        this.channelShouldCheckRpcResponseType = channelShouldCheckRpcResponseType;
    }

    public ErrorOnWriteListener getErrorOnWriteListener() {
        return errorOnWriteListener;
    }

    public void setErrorOnWriteListener(ErrorOnWriteListener errorOnWriteListener) {
        this.errorOnWriteListener = errorOnWriteListener;
    }

    public Integer getWorkPoolTimeout() {
        return workPoolTimeout;
    }

    public void setWorkPoolTimeout(Integer workPoolTimeout) {
        this.workPoolTimeout = workPoolTimeout;
    }

    public TopologyRecoveryFilter getTopologyRecoveryFilter() {
        return topologyRecoveryFilter;
    }

    public void setTopologyRecoveryFilter(TopologyRecoveryFilter topologyRecoveryFilter) {
        this.topologyRecoveryFilter = topologyRecoveryFilter;
    }

    public Predicate<ShutdownSignalException> getConnectionRecoveryTriggeringCondition() {
        return connectionRecoveryTriggeringCondition;
    }

    public void setConnectionRecoveryTriggeringCondition(Predicate<ShutdownSignalException> connectionRecoveryTriggeringCondition) {
        this.connectionRecoveryTriggeringCondition = connectionRecoveryTriggeringCondition;
    }

    public RetryHandler getTopologyRecoveryRetryHandler() {
        return topologyRecoveryRetryHandler;
    }

    public void setTopologyRecoveryRetryHandler(RetryHandler topologyRecoveryRetryHandler) {
        this.topologyRecoveryRetryHandler = topologyRecoveryRetryHandler;
    }

    public TrafficListener getTrafficListener() {
        return trafficListener;
    }

    public void setTrafficListener(TrafficListener trafficListener) {
        this.trafficListener = trafficListener;
    }

    public CarefreeClassDeclaration getSaslConfig() {
        return saslConfig;
    }

    public void setSaslConfig(CarefreeClassDeclaration saslConfig) {
        this.saslConfig = saslConfig;
    }

    public ExecutorService getSharedExecutor() {
        return sharedExecutor;
    }

    public void setSharedExecutor(ExecutorService sharedExecutor) {
        this.sharedExecutor = sharedExecutor;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public ExecutorService getShutdownExecutor() {
        return shutdownExecutor;
    }

    public void setShutdownExecutor(ExecutorService shutdownExecutor) {
        this.shutdownExecutor = shutdownExecutor;
    }

    public ScheduledExecutorService getHeartbeatExecutor() {
        return heartbeatExecutor;
    }

    public void setHeartbeatExecutor(ScheduledExecutorService heartbeatExecutor) {
        this.heartbeatExecutor = heartbeatExecutor;
    }

    public SocketConfigurator getSocketConfigurator() {
        return socketConfigurator;
    }

    public void setSocketConfigurator(SocketConfigurator socketConfigurator) {
        this.socketConfigurator = socketConfigurator;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

}
