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
package org.apenk.carefree.druid;

import java.util.List;

/**
 * Druid 配置载体
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeDruidArchetype {
    /**
     * 是否启用本配置，若 false 则不会创建对应的 DataSource 对象，但仍然可以被其它配置引用，默认为 true
     */
    private Boolean enabled = true;
    /**
     * 引用的另一个配置的 key，若本实例的某个属性未配置，则使用所引用配置的相应属性作为默认值
     */
    private String reference;
    /**
     * 配置这个属性的意义在于，
     * 如果存在多个数据源，监控的时候可以通过名字来区分开来。
     * 如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this).
     * 另外配置此属性至少在1.0.5版本中是不起作用的，强行设置name会出错。详情：https://blog.csdn.net/lanmo555/article/details/41248763
     */
    private String name;
    /**
     * 连接数据库的url，不同数据库不一样。例如：
     * mysql : jdbc:mysql://10.20.153.104:3306/druid2
     * oracle : jdbc:oracle:thin:@10.20.149.85:1521:ocnauto
     */
    private String url;
    /**
     * 连接数据库的用户名
     */
    private String username;
    /**
     * 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。详细看：https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
     */
    private String password;
    /**
     * 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName。
     * 缺省值：根据url自动识别
     */
    private String driverClassName;
    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时。
     * 缺省值：0
     */
    private Integer initialSize;
    /**
     * 最大连接池数量。
     * 缺省值：8
     */
    private Integer maxActive;
    /**
     * 最小连接池数量
     */
    private Integer minIdle;
    /**
     * 获取连接时最大等待时间，单位毫秒。
     * 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Long maxWait;
    /**
     * 是否缓存preparedStatement，也就是PSCache。
     * PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements;
    /**
     * 要启用PSCache，必须配置大于0，
     * 当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     * 缺省值：10
     */
    private Integer maxPoolPreparedStatementPerConnectionSize;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     * 缺省值：null
     */
    private String validationQuery;
    /**
     * 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
     * 缺省值：-1
     */
    private Integer validationQueryTimeout;
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     * 缺省值：false
     */
    private Boolean testOnBorrow;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     * 缺省值：false
     */
    private Boolean testOnReturn;
    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     * 缺省值：true
     */
    private Boolean testWhileIdle;
    /**
     * 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。
     * 缺省值：false
     */
    private Boolean keepAlive;
    /**
     * 毫秒，有两个含义：
     * 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明。
     * 缺省值：60000（1分钟）
     */
    private Long timeBetweenEvictionRunsMillis;
    /**
     * 连接保持空闲而不被驱逐的最小时间。
     * 缺省值：1800000（30分钟）
     */
    private Long minEvictableIdleTimeMillis;
    /**
     * 物理连接初始化的时候执行的sql
     */
    private List<String> connectionInitSqls;
    /**
     * 当数据库抛出一些不可恢复的异常时，抛弃连接。
     * ExceptionSorter 实现类名
     * 缺省值：根据dbType自动识别
     */
    private String exceptionSorter;
    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat；
     * 日志用的filter:log4j；
     * 防御sql注入的filter:wall。
     * 多个以半角逗号,分割
     */
    private String filters;
    /**
     * com.alibaba.druid.filter.Filter 实现类名集合，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
     */
    private List<String> proxyFilters;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    public Boolean getPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public Integer getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public Integer getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(Integer validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
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

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public List<String> getConnectionInitSqls() {
        return connectionInitSqls;
    }

    public void setConnectionInitSqls(List<String> connectionInitSqls) {
        this.connectionInitSqls = connectionInitSqls;
    }

    public String getExceptionSorter() {
        return exceptionSorter;
    }

    public void setExceptionSorter(String exceptionSorter) {
        this.exceptionSorter = exceptionSorter;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public List<String> getProxyFilters() {
        return proxyFilters;
    }

    public void setProxyFilters(List<String> proxyFilters) {
        this.proxyFilters = proxyFilters;
    }

}