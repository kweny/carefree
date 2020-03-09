package org.apenk.carefree.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.typesafe.config.Config;
import org.apenk.carefree.aide.*;
import org.apenk.carefree.helper.CarefreeAssistance;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeDruidBuilder {

    private static final CarefreeDruidBuilder INSTANCE = new CarefreeDruidBuilder();

    public static CarefreeDruidBuilder getInstance() {
        return INSTANCE;
    }

    private final Map<String, CarefreeDruidArchetype> archetypeCache;

    private CarefreeDruidBuilder() {
        this.archetypeCache = new ConcurrentHashMap<>();
    }

    void loadConfig(Config config) throws Exception {
        Set<String> rootKeys = CarefreeAssistance.getConfigRootNames(config);

        for (String rootKey : rootKeys) {
            Config oneConfig = config.getConfig(rootKey);
            CarefreeDruidArchetype archetype = CarefreeAssistance.fromConfig(CarefreeDruidArchetype.class, oneConfig);
            this.archetypeCache.put(rootKey, archetype);
        }
    }

    Map<String, DruidDataSource> build() {
        Map<String, DruidDataSource> map = new HashMap<>();
        archetypeCache.forEach((key, archetype) -> {
            try {
                if (BooleanAide.isFalse(archetype.getEnabled())) {
                    return; // means continue
                }
                if (StringAide.isNotBlank(archetype.getReference())) {
                    CarefreeDruidArchetype refArchetype = archetypeCache.get(archetype.getReference());
                    if (refArchetype == null) {
                        CarefreeDruidAutoConfiguration.logger.warn("no reference: {} for druid config: {}", archetype.getReference(), key);
                        return; // means continue
                    }

                    CarefreeAssistance.loadReference(CarefreeDruidArchetype.class, archetype, refArchetype);
                }
                map.put(key, createDataSource(archetype));
            } catch (Exception e) {
                throw new RuntimeException("[Carefree] error to create druid instance for config key: " + key, e);
            }
        });
        MapAide.clear(archetypeCache);
        return map;
    }

    private DruidDataSource createDataSource(CarefreeDruidArchetype archetype) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();

        if (StringAide.isNotBlank(archetype.getName())) {
            dataSource.setName(archetype.getName());
        }
        if (StringAide.isNotBlank(archetype.getUrl())) {
            dataSource.setUrl(archetype.getUrl());
        }
        if (StringAide.isNotBlank(archetype.getUsername())) {
            dataSource.setUsername(archetype.getUsername());
        }
        if (StringAide.isNotBlank(archetype.getPassword())) {
            dataSource.setPassword(archetype.getPassword());
        }
        if (StringAide.isNotBlank(archetype.getDriverClassName())) {
            dataSource.setDriverClassName(archetype.getDriverClassName());
        }
        if (ObjectAide.isNotNull(archetype.getInitialSize())) {
            dataSource.setInitialSize(archetype.getInitialSize());
        }
        if (ObjectAide.isNotNull(archetype.getMaxActive())) {
            dataSource.setMaxActive(archetype.getMaxActive());
        }
        if (ObjectAide.isNotNull(archetype.getMinIdle())) {
            dataSource.setMinIdle(archetype.getMinIdle());
        }
        if (ObjectAide.isNotNull(archetype.getMaxWait())) {
            dataSource.setMaxWait(archetype.getMaxWait());
        }
        if (ObjectAide.isNotNull(archetype.getPoolPreparedStatements())) {
            dataSource.setPoolPreparedStatements(archetype.getPoolPreparedStatements());
        }
        if (ObjectAide.isNotNull(archetype.getMaxPoolPreparedStatementPerConnectionSize())) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(archetype.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (StringAide.isNotBlank(archetype.getValidationQuery())) {
            dataSource.setValidationQuery(archetype.getValidationQuery());
        }
        if (ObjectAide.isNotNull(archetype.getValidationQueryTimeout())) {
            dataSource.setValidationQueryTimeout(archetype.getValidationQueryTimeout());
        }
        if (ObjectAide.isNotNull(archetype.getTestOnBorrow())) {
            dataSource.setTestOnBorrow(archetype.getTestOnBorrow());
        }
        if (ObjectAide.isNotNull(archetype.getTestOnReturn())) {
            dataSource.setTestOnReturn(archetype.getTestOnReturn());
        }
        if (ObjectAide.isNotNull(archetype.getTestWhileIdle())) {
            dataSource.setTestWhileIdle(archetype.getTestWhileIdle());
        }
        if (ObjectAide.isNotNull(archetype.getKeepAlive())) {
            dataSource.setKeepAlive(archetype.getKeepAlive());
        }
        if (ObjectAide.isNotNull(archetype.getTimeBetweenEvictionRunsMillis())) {
            dataSource.setTimeBetweenEvictionRunsMillis(archetype.getTimeBetweenEvictionRunsMillis());
        }
        if (ObjectAide.isNotNull(archetype.getMinEvictableIdleTimeMillis())) {
            dataSource.setMinEvictableIdleTimeMillis(archetype.getMinEvictableIdleTimeMillis());
        }
        if (CollectionAide.isNotEmpty(archetype.getConnectionInitSqls())) {
            dataSource.setConnectionInitSqls(archetype.getConnectionInitSqls());
        }
        if (StringAide.isNotBlank(archetype.getExceptionSorter())) {
            dataSource.setExceptionSorterClassName(archetype.getExceptionSorter());
        }
        if (StringAide.isNotBlank(archetype.getFilters())) {
            dataSource.setFilters(archetype.getFilters());
        }
        if (CollectionAide.isNotEmpty(archetype.getProxyFilters())) {
            List<Filter> proxyFilters = new ArrayList<>();
            for (String proxyFilterName : archetype.getProxyFilters()) {
                Class<?> filterClass = Class.forName(proxyFilterName);
                proxyFilters.add((Filter) filterClass.newInstance());
            }
            dataSource.setProxyFilters(proxyFilters);
        }

        return dataSource;
    }
}