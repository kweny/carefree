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
 * Redis 连接池配置原型
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisArchetypePool {
    /**
     * 池中可分配的最大连接数，
     * 负值表示无限制，
     * 默认 8
     */
    private Integer maxTotal;
    /**
     * 池中允许的最大空闲连接数，
     * 负值表示无限制，
     * 默认 8
     */
    private Integer maxIdle;
    /**
     * 池中维持的最小空闲连接数，
     * 负值表示无效值，
     * 默认 0
     */
    private Integer minIdle;
    /**
     * 从池中获取连接时是否使用 LIFO 策略，
     * true 表示使用 LIFO（后进先出），始终返回最近使用的空闲连接；
     * false 表示使用 FIFO（先进先出），始终返回最旧的空闲连接，
     * 默认 true，即 LIFO
     */
    private Boolean lifo;
    /**
     * 从池中获取连接时是否启用公平锁机制，
     * 默认 false
     */
    private Boolean fairness;
    /**
     * 当连接池耗尽，从池中获取连接时是否等待，
     * 默认 true
     */
    private Boolean blockWhenExhausted;
    /**
     * 当连接池耗尽，且 {@link #blockWhenExhausted} 为 true，从池中获取连接时的最长等待时间，
     * 单位为毫秒，负值表示无限期等待，
     * 默认 -1
     */
    private Long maxWaitMillis;
    /**
     * 从池中获取连接时，在返回连接之前（borrow 阶段），是否对新建连接（无空闲连接时）进行验证，
     * 若验证失败，则连接获取失败，
     * 默认 false
     */
    private Boolean testOnCreate;
    /**
     * 从池中获取连接时，在返回之前（borrow 阶段），是否进行验证，
     * 若验证失败，则将其从池中删除并销毁，并且尝试重新获取，
     * 默认 false
     */
    private Boolean testOnBorrow;
    /**
     * 从池中获取连接时，在返回阶段（borrow 之后），是否进行验证，
     * 若验证失败，则将该连接销毁，而不是放回池中，
     * 默认 false
     */
    private Boolean testOnReturn;
    /**
     * 当“空闲连接剔除线程”（如果有）判断一个空闲连接不需要移除时，是否对其进行验证，
     * 若验证失败，则依然将其移除并销毁，
     * 默认 false
     */
    private Boolean testWhileIdle;
    /**
     * “空闲连接剔除线程”两次运行之间的间隔时间，
     * 单位为毫秒，当取 0 或负值时则不运行“空闲连接剔除线程”，
     * 默认 -1
     */
    private Long timeBetweenEvictionRunsMillis;
    /**
     * “空闲连接剔除线程”（如果有）每次运行期间检查的最大连接数，
     * 如果为正值，则每次检查的真实数量为：min(该值, 池中空闲连接数)，
     * 如果为负值，则每次检查的真实数量为：ceil(池中空闲连接数 / abs(该值))，
     * 默认 3
     */
    private Integer numTestsPerEvictionRun;
    /**
     * 一个连接的空闲时间至少达到该值才允许被剔除，
     * 单位为毫秒，当取 0 或负值时，则不会仅仅因为空闲时间达标而剔除连接，除非有其它原因（如连接失效），
     * 默认 ‭1800000（1000L * 60L * 30L）‬，即 30 分钟
     */
    private Long minEvictableIdleTimeMillis;
    /**
     * 当一个连接的空闲时间至少达到该值，且池中当前的空闲连接数多于 {@link #minIdle} 选项所设置的最小空闲数，才允许被剔除，
     * 单位为毫秒，当取 0 或负值时，则不会仅仅因为前述条件而剔除连接，除非有其它原因（如连接失效），
     * 仅当 {@link #minEvictableIdleTimeMillis} 取 0 或 负值时，该选项才生效，
     * 默认 -1
     */
    private Long softMinEvictableIdleTimeMillis;
    /**
     * 当连接池关闭时，等待其持有的“空闲连接剔除线程（Evictor）”关闭的超时时间，
     * 此外在运行时重设 timeBetweenEvictionRunsMillis 选项将关闭旧有的 Evictor 而创建一个新的来替代，
     * 此时该选项值也将用于旧有 Evictor 的关闭等待时间，
     * 默认 10000（10L * 1000L），即 10 秒钟
     */
    private Long evictorShutdownTimeoutMillis;
    /**
     * 自定义剔除策略接口 {@link org.apache.commons.pool2.impl.EvictionPolicy} 的实现类全名，
     * 默认 “org.apache.commons.pool2.impl.DefaultEvictionPolicy”
     */
    private String evictionPolicyClassName;
    /**
     * 是否启用 JMX 管理和监视连接池
     * true
     */
    private Boolean jmxEnabled;
    /**
     * 用于组装 JMX MBean 的 Object Name，组装规则为：jmxNameBase + jmxNamePrefix + i，
     * jmxNameBase 为包括 domain、type 的基础部分，当空或不合法时，则使用 "org.apache.commons.pool2:type=GenericObjectPool,name="，
     */
    private String jmxNameBase;
    /**
     * 用于组装 JMX MBean 的 Object Name，组装规则为：jmxNameBase + jmxNamePrefix + i，
     * jmxNamePrefix 为 name 段的前缀，
     * 默认 “pool”
     */
    private String jmxNamePrefix;

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getLifo() {
        return lifo;
    }

    public void setLifo(Boolean lifo) {
        this.lifo = lifo;
    }

    public Boolean getFairness() {
        return fairness;
    }

    public void setFairness(Boolean fairness) {
        this.fairness = fairness;
    }

    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public Long getEvictorShutdownTimeoutMillis() {
        return evictorShutdownTimeoutMillis;
    }

    public void setEvictorShutdownTimeoutMillis(Long evictorShutdownTimeoutMillis) {
        this.evictorShutdownTimeoutMillis = evictorShutdownTimeoutMillis;
    }

    public Long getSoftMinEvictableIdleTimeMillis() {
        return softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(Long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    public Integer getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public String getEvictionPolicyClassName() {
        return evictionPolicyClassName;
    }

    public void setEvictionPolicyClassName(String evictionPolicyClassName) {
        this.evictionPolicyClassName = evictionPolicyClassName;
    }

    public Boolean getTestOnCreate() {
        return testOnCreate;
    }

    public void setTestOnCreate(Boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public Boolean getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public Long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Boolean getBlockWhenExhausted() {
        return blockWhenExhausted;
    }

    public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public Boolean getJmxEnabled() {
        return jmxEnabled;
    }

    public void setJmxEnabled(Boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    public String getJmxNamePrefix() {
        return jmxNamePrefix;
    }

    public void setJmxNamePrefix(String jmxNamePrefix) {
        this.jmxNamePrefix = jmxNamePrefix;
    }

    public String getJmxNameBase() {
        return jmxNameBase;
    }

    public void setJmxNameBase(String jmxNameBase) {
        this.jmxNameBase = jmxNameBase;
    }
}