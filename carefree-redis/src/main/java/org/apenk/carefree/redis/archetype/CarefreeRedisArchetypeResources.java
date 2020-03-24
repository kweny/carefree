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

import io.lettuce.core.resource.ClientResources;
import org.apenk.carefree.helper.CarefreeClassDeclaration;

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
    private CarefreeClassDeclaration eventLoopGroupProvider;

    /**
     * {@link io.netty.util.concurrent.EventExecutorGroup} 接口的实现类的构造描述符。
     * 如果要对线程池进行总体控制，或者要重用已经构建好的 Netty 框架，则可以配置该项。
     * 这是一个高级配置，仅在知道自己在做什么的时候使用。
     * 客户端资源关闭时不会释放 EventExecutorGroup 实例，若不再需要则应手动释放。
     */
    private CarefreeClassDeclaration eventExecutorGroup;

    /**
     * {@link io.netty.util.Timer} 接口的实现类的构造描述符。
     * 可以在 RedisClient 和 RedisClusterClient 的不同实例之间使用的共享 Timer。
     * 这是一个高级配置，仅在知道自己在做什么的时候使用。
     * 客户端资源关闭时不会释放 Timer 实例，若不再需要则应手动释放。
     */
    private CarefreeClassDeclaration timer;

    /**
     * {@link io.lettuce.core.event.EventBus} 接口的实现类的构造描述符。
     * 在不同 RedisClient 实例之间使用的事件总线。
     */
    private CarefreeClassDeclaration eventBus;

    /**
     * {@link io.lettuce.core.event.EventPublisherOptions} 接口的实现类的构造描述符。
     * 命令延迟发布选项
     */
    private CarefreeClassDeclaration commandLatencyPublisherOptions;

    /**
     * {@link io.lettuce.core.metrics.CommandLatencyCollectorOptions} 接口的实现类全名，
     * 命令延迟收集选项
     */
    private CarefreeClassDeclaration commandLatencyCollectorOptions;

    /**
     * {@link io.lettuce.core.metrics.CommandLatencyCollector} 接口的实现类构造描述符，
     * 命令延迟收集器
     */
    private CarefreeClassDeclaration commandLatencyCollector;

    /**
     * {@link io.lettuce.core.resource.DnsResolver} 接口的实现类构造描述符，
     * DNS 解析器
     */
    private CarefreeClassDeclaration dnsResolver;

    /**
     * {@link io.lettuce.core.resource.Delay} 的实现类构造描述符，
     * 重连延迟
     */
    private CarefreeClassDeclaration reconnectDelay;

    /**
     * {@link io.lettuce.core.resource.NettyCustomizer} 接口的实现类构造描述符，
     * Netty 定制程序
     */
    private CarefreeClassDeclaration nettyCustomizer;

    /**
     * {@link io.lettuce.core.tracing.Tracing} 接口的实现类构造描述符，
     * 跟踪 Redis 调用
     */
    private CarefreeClassDeclaration tracing;

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

    public CarefreeClassDeclaration getEventLoopGroupProvider() {
        return eventLoopGroupProvider;
    }

    public void setEventLoopGroupProvider(CarefreeClassDeclaration eventLoopGroupProvider) {
        this.eventLoopGroupProvider = eventLoopGroupProvider;
    }

    public CarefreeClassDeclaration getEventExecutorGroup() {
        return eventExecutorGroup;
    }

    public void setEventExecutorGroup(CarefreeClassDeclaration eventExecutorGroup) {
        this.eventExecutorGroup = eventExecutorGroup;
    }

    public CarefreeClassDeclaration getTimer() {
        return timer;
    }

    public void setTimer(CarefreeClassDeclaration timer) {
        this.timer = timer;
    }

    public CarefreeClassDeclaration getEventBus() {
        return eventBus;
    }

    public void setEventBus(CarefreeClassDeclaration eventBus) {
        this.eventBus = eventBus;
    }

    public CarefreeClassDeclaration getCommandLatencyPublisherOptions() {
        return commandLatencyPublisherOptions;
    }

    public void setCommandLatencyPublisherOptions(CarefreeClassDeclaration commandLatencyPublisherOptions) {
        this.commandLatencyPublisherOptions = commandLatencyPublisherOptions;
    }

    public CarefreeClassDeclaration getCommandLatencyCollectorOptions() {
        return commandLatencyCollectorOptions;
    }

    public void setCommandLatencyCollectorOptions(CarefreeClassDeclaration commandLatencyCollectorOptions) {
        this.commandLatencyCollectorOptions = commandLatencyCollectorOptions;
    }

    public CarefreeClassDeclaration getCommandLatencyCollector() {
        return commandLatencyCollector;
    }

    public void setCommandLatencyCollector(CarefreeClassDeclaration commandLatencyCollector) {
        this.commandLatencyCollector = commandLatencyCollector;
    }

    public CarefreeClassDeclaration getDnsResolver() {
        return dnsResolver;
    }

    public void setDnsResolver(CarefreeClassDeclaration dnsResolver) {
        this.dnsResolver = dnsResolver;
    }

    public CarefreeClassDeclaration getReconnectDelay() {
        return reconnectDelay;
    }

    public void setReconnectDelay(CarefreeClassDeclaration reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public CarefreeClassDeclaration getNettyCustomizer() {
        return nettyCustomizer;
    }

    public void setNettyCustomizer(CarefreeClassDeclaration nettyCustomizer) {
        this.nettyCustomizer = nettyCustomizer;
    }

    public CarefreeClassDeclaration getTracing() {
        return tracing;
    }

    public void setTracing(CarefreeClassDeclaration tracing) {
        this.tracing = tracing;
    }
}
