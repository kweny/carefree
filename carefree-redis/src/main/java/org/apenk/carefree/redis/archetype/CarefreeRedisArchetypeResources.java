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

import io.lettuce.core.event.EventBus;
import io.lettuce.core.resource.ClientResources;
import org.apenk.carefree.helper.CarefreeClassWrapper;

/**
 * {@link ClientResources} configuration
 *
 * https://github.com/lettuce-io/lettuce-core/wiki/Configuring-Client-resources
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetypeResources {
    /**
     * 用于 I/O 操作的线程池大小，默认为可用 CPU 数量，必须大于 0
     */
    private Integer ioThreadPoolSize;
    /**
     * 用于计算操作的线程池大小，默认为可用 CPU 数量，必须大于 0
     */
    private Integer computationThreadPoolSize;

    /**
     * {@link io.lettuce.core.resource.EventLoopGroupProvider} 接口的实现类的构造描述符。
     * 可以在 RedisClient 和 RedisClusterClient 的不同实例之间使用的共享事件执行程序提供程序。
     * 如果要对线程池进行总体控制，或者要重用已经构建好的 Netty 框架，则可以配置该项。
     * 这是一个高级配置，仅在知道自己在做什么的时候使用。
     * 客户端资源关闭时不会释放 EventLoopGroupProvider 实例，若不再需要则应手动释放。
     */
    private CarefreeClassWrapper eventLoopGroupProvider;
    /**
     * 同 {@link #eventLoopGroupProvider}，
     * {@link io.lettuce.core.resource.EventLoopGroupProvider} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 eventLoopGroupProvider 和 eventLoopGroupProviderClassName 则以 eventLoopGroupProvider 为准。
     */
    private String eventLoopGroupProviderClassName;

    /**
     * {@link io.netty.util.concurrent.EventExecutorGroup} 接口的实现类的构造描述符。
     * 如果要对线程池进行总体控制，或者要重用已经构建好的 Netty 框架，则可以配置该项。
     * 这是一个高级配置，仅在知道自己在做什么的时候使用。
     * 客户端资源关闭时不会释放 EventExecutorGroup 实例，若不再需要则应手动释放。
     */
    private CarefreeClassWrapper eventExecutorGroup;
    /**
     * 同 {@link #eventExecutorGroup}，
     * {@link io.netty.util.concurrent.EventExecutorGroup} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 eventExecutorGroup 和 eventExecutorGroupClassName 则以 eventExecutorGroup 为准。
     */
    private String eventExecutorGroupClassName;

    /**
     * {@link io.netty.util.Timer} 接口的实现类的构造描述符。
     * 可以在 RedisClient 和 RedisClusterClient 的不同实例之间使用的共享 Timer。
     * 这是一个高级配置，仅在知道自己在做什么的时候使用。
     * 客户端资源关闭时不会释放 Timer 实例，若不再需要则应手动释放。
     */
    private CarefreeClassWrapper timer;
    /**
     * 同 {@link #timer}，
     * {@link io.netty.util.Timer} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 timer 和 timerClassName 则以 timer 为准。
     */
    private String timerClassName;

    /**
     * {@link io.lettuce.core.event.EventBus} 接口的实现类的构造描述符。
     * 在不同 RedisClient 实例之间使用的事件总线。
     */
    private CarefreeClassWrapper eventBus;
    /**
     * 同 {@link #eventBus}，
     * {@link io.lettuce.core.event.EventBus} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 eventBus 和 eventBusClassName 则以 eventBus 为准。
     */
    private EventBus eventBusClassName;

    /**
     * {@link io.lettuce.core.event.EventPublisherOptions} 接口的实现类的构造描述符。
     * 命令延迟发布选项
     */
    private CarefreeClassWrapper commandLatencyPublisherOptions;
    /**
     * 同 {@link #commandLatencyPublisherOptions}，
     * {@link io.lettuce.core.event.EventPublisherOptions} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 commandLatencyPublisherOptions 和 commandLatencyPublisherOptionsClassName 则以 commandLatencyPublisherOptions 为准。
     */
    private String commandLatencyPublisherOptionsClassName;

    /**
     * {@link io.lettuce.core.metrics.CommandLatencyCollectorOptions} 接口的实现类全名，
     * 命令延迟收集选项
     */
    private CarefreeClassWrapper commandLatencyCollectorOptions;
    /**
     * 同 {@link #commandLatencyCollectorOptions}，
     * {@link io.lettuce.core.metrics.CommandLatencyCollectorOptions} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 commandLatencyCollectorOptions 和 commandLatencyCollectorOptionsClassName 则以 commandLatencyCollectorOptions 为准。
     */
    private String commandLatencyCollectorOptionsClassName;

    /**
     * {@link io.lettuce.core.metrics.CommandLatencyCollector} 接口的实现类构造描述符，
     * 命令延迟收集器
     */
    private CarefreeClassWrapper commandLatencyCollector;
    /**
     * 同 {@link #commandLatencyCollector}，
     * {@link io.lettuce.core.metrics.CommandLatencyCollector} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 commandLatencyCollector 和 commandLatencyCollectorClassName 则以 commandLatencyCollector 为准。
     */
    private String commandLatencyCollectorClassName;

    /**
     * {@link io.lettuce.core.resource.DnsResolver} 接口的实现类构造描述符，
     * DNS 解析器
     */
    private CarefreeClassWrapper dnsResolver;
    /**
     * 同 {@link #dnsResolver}，
     * {@link io.lettuce.core.resource.DnsResolver} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 dnsResolver 和 dnsResolverClassName 则以 dnsResolver 为准。
     */
    private String dnsResolverClassName;

    /**
     * {@link io.lettuce.core.resource.Delay} 的实现类构造描述符，
     * 重连延迟
     */
    private CarefreeClassWrapper reconnectDelay;
    /**
     * 同 {@link #reconnectDelay}，
     * {@link io.lettuce.core.resource.Delay} 实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 reconnectDelay 和 reconnectDelayClassName 则以 reconnectDelay 为准。
     */
    private String reconnectDelayClassName;

    /**
     * {@link io.lettuce.core.resource.NettyCustomizer} 接口的实现类构造描述符，
     * Netty 定制程序
     */
    private CarefreeClassWrapper nettyCustomizer;
    /**
     * 同 {@link #nettyCustomizer}，
     * {@link io.lettuce.core.resource.NettyCustomizer} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 nettyCustomizer 和 nettyCustomizerClassName 则以 nettyCustomizer 为准。
     */
    private String nettyCustomizerClassName;

    /**
     * {@link io.lettuce.core.tracing.Tracing} 接口的实现类构造描述符，
     * 跟踪 Redis 调用
     */
    private CarefreeClassWrapper tracing;
    /**
     * 同 {@link #tracing}，
     * {@link io.lettuce.core.tracing.Tracing} 接口的实现类全名，
     * 将使用其默认构造方法创建实例。
     * 若同时配置了 tracing 和 tracingClassName 则以 tracing 为准。
     */
    private String tracingClassName;

    public Integer getIoThreadPoolSize() {
        return ioThreadPoolSize;
    }

    public void setIoThreadPoolSize(Integer ioThreadPoolSize) {
        this.ioThreadPoolSize = ioThreadPoolSize;
    }

    public Integer getComputationThreadPoolSize() {
        return computationThreadPoolSize;
    }

    public void setComputationThreadPoolSize(Integer computationThreadPoolSize) {
        this.computationThreadPoolSize = computationThreadPoolSize;
    }

    public CarefreeClassWrapper getEventLoopGroupProvider() {
        return eventLoopGroupProvider;
    }

    public void setEventLoopGroupProvider(CarefreeClassWrapper eventLoopGroupProvider) {
        this.eventLoopGroupProvider = eventLoopGroupProvider;
    }

    public String getEventLoopGroupProviderClassName() {
        return eventLoopGroupProviderClassName;
    }

    public void setEventLoopGroupProviderClassName(String eventLoopGroupProviderClassName) {
        this.eventLoopGroupProviderClassName = eventLoopGroupProviderClassName;
    }

    public CarefreeClassWrapper getEventExecutorGroup() {
        return eventExecutorGroup;
    }

    public void setEventExecutorGroup(CarefreeClassWrapper eventExecutorGroup) {
        this.eventExecutorGroup = eventExecutorGroup;
    }

    public String getEventExecutorGroupClassName() {
        return eventExecutorGroupClassName;
    }

    public void setEventExecutorGroupClassName(String eventExecutorGroupClassName) {
        this.eventExecutorGroupClassName = eventExecutorGroupClassName;
    }

    public CarefreeClassWrapper getTimer() {
        return timer;
    }

    public void setTimer(CarefreeClassWrapper timer) {
        this.timer = timer;
    }

    public String getTimerClassName() {
        return timerClassName;
    }

    public void setTimerClassName(String timerClassName) {
        this.timerClassName = timerClassName;
    }

    public CarefreeClassWrapper getEventBus() {
        return eventBus;
    }

    public void setEventBus(CarefreeClassWrapper eventBus) {
        this.eventBus = eventBus;
    }

    public EventBus getEventBusClassName() {
        return eventBusClassName;
    }

    public void setEventBusClassName(EventBus eventBusClassName) {
        this.eventBusClassName = eventBusClassName;
    }

    public CarefreeClassWrapper getCommandLatencyPublisherOptions() {
        return commandLatencyPublisherOptions;
    }

    public void setCommandLatencyPublisherOptions(CarefreeClassWrapper commandLatencyPublisherOptions) {
        this.commandLatencyPublisherOptions = commandLatencyPublisherOptions;
    }

    public String getCommandLatencyPublisherOptionsClassName() {
        return commandLatencyPublisherOptionsClassName;
    }

    public void setCommandLatencyPublisherOptionsClassName(String commandLatencyPublisherOptionsClassName) {
        this.commandLatencyPublisherOptionsClassName = commandLatencyPublisherOptionsClassName;
    }

    public CarefreeClassWrapper getCommandLatencyCollectorOptions() {
        return commandLatencyCollectorOptions;
    }

    public void setCommandLatencyCollectorOptions(CarefreeClassWrapper commandLatencyCollectorOptions) {
        this.commandLatencyCollectorOptions = commandLatencyCollectorOptions;
    }

    public String getCommandLatencyCollectorOptionsClassName() {
        return commandLatencyCollectorOptionsClassName;
    }

    public void setCommandLatencyCollectorOptionsClassName(String commandLatencyCollectorOptionsClassName) {
        this.commandLatencyCollectorOptionsClassName = commandLatencyCollectorOptionsClassName;
    }

    public CarefreeClassWrapper getCommandLatencyCollector() {
        return commandLatencyCollector;
    }

    public void setCommandLatencyCollector(CarefreeClassWrapper commandLatencyCollector) {
        this.commandLatencyCollector = commandLatencyCollector;
    }

    public String getCommandLatencyCollectorClassName() {
        return commandLatencyCollectorClassName;
    }

    public void setCommandLatencyCollectorClassName(String commandLatencyCollectorClassName) {
        this.commandLatencyCollectorClassName = commandLatencyCollectorClassName;
    }

    public CarefreeClassWrapper getDnsResolver() {
        return dnsResolver;
    }

    public void setDnsResolver(CarefreeClassWrapper dnsResolver) {
        this.dnsResolver = dnsResolver;
    }

    public String getDnsResolverClassName() {
        return dnsResolverClassName;
    }

    public void setDnsResolverClassName(String dnsResolverClassName) {
        this.dnsResolverClassName = dnsResolverClassName;
    }

    public CarefreeClassWrapper getReconnectDelay() {
        return reconnectDelay;
    }

    public void setReconnectDelay(CarefreeClassWrapper reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public String getReconnectDelayClassName() {
        return reconnectDelayClassName;
    }

    public void setReconnectDelayClassName(String reconnectDelayClassName) {
        this.reconnectDelayClassName = reconnectDelayClassName;
    }

    public CarefreeClassWrapper getNettyCustomizer() {
        return nettyCustomizer;
    }

    public void setNettyCustomizer(CarefreeClassWrapper nettyCustomizer) {
        this.nettyCustomizer = nettyCustomizer;
    }

    public String getNettyCustomizerClassName() {
        return nettyCustomizerClassName;
    }

    public void setNettyCustomizerClassName(String nettyCustomizerClassName) {
        this.nettyCustomizerClassName = nettyCustomizerClassName;
    }

    public CarefreeClassWrapper getTracing() {
        return tracing;
    }

    public void setTracing(CarefreeClassWrapper tracing) {
        this.tracing = tracing;
    }

    public String getTracingClassName() {
        return tracingClassName;
    }

    public void setTracingClassName(String tracingClassName) {
        this.tracingClassName = tracingClassName;
    }
}
