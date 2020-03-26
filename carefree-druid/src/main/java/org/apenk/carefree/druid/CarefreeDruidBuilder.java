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

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.typesafe.config.Config;
import org.apenk.carefree.helper.CarefreeAssistance;
import org.apenk.carefree.helper.TempCarefreeAide;

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
        Set<String> roots = CarefreeAssistance.getConfigRoots(config);

        for (String root : roots) {
            Config oneConfig = config.getConfig(root);
            CarefreeDruidArchetype archetype = CarefreeAssistance.fromConfig(CarefreeDruidArchetype.class, oneConfig);
            this.archetypeCache.put(root, archetype);
        }
    }

    Map<String, CarefreeDruidWrapper> build() {
        Map<String, CarefreeDruidWrapper> map = new HashMap<>();
        archetypeCache.forEach((key, archetype) -> {
            try {
                if (TempCarefreeAide.isFalse(archetype.getEnabled())) {
                    return; // means continue
                }
                if (TempCarefreeAide.isNotBlank(archetype.getReference())) {
                    CarefreeDruidArchetype refArchetype = archetypeCache.get(archetype.getReference());
                    if (refArchetype == null) {
                        CarefreeDruidAutoConfiguration.logger.warn("no reference: {} for druid config: {}", archetype.getReference(), key);
                        return; // means continue
                    }

                    CarefreeAssistance.loadReference(CarefreeDruidArchetype.class, archetype, refArchetype);
                }
                CarefreeDruidWrapper wrapper = new CarefreeDruidWrapper();
                wrapper.setDataSource(createDataSource(archetype));
                map.put(key, wrapper);
            } catch (Exception e) {
                throw new RuntimeException("[Carefree] error to create druid instance for config name: " + key, e);
            }
        });
        TempCarefreeAide.clear(archetypeCache);
        return map;
    }

    private DruidDataSource createDataSource(CarefreeDruidArchetype archetype) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();

        if (TempCarefreeAide.isNotBlank(archetype.getName())) {
            dataSource.setName(archetype.getName());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getUrl())) {
            dataSource.setUrl(archetype.getUrl());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getUsername())) {
            dataSource.setUsername(archetype.getUsername());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getPassword())) {
            dataSource.setPassword(archetype.getPassword());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getDriverClassName())) {
            dataSource.setDriverClassName(archetype.getDriverClassName());
        }
        if (TempCarefreeAide.isNotNull(archetype.getInitialSize())) {
            dataSource.setInitialSize(archetype.getInitialSize());
        }
        if (TempCarefreeAide.isNotNull(archetype.getMaxActive())) {
            dataSource.setMaxActive(archetype.getMaxActive());
        }
        if (TempCarefreeAide.isNotNull(archetype.getMinIdle())) {
            dataSource.setMinIdle(archetype.getMinIdle());
        }
        if (TempCarefreeAide.isNotNull(archetype.getMaxWait())) {
            dataSource.setMaxWait(archetype.getMaxWait());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPoolPreparedStatements())) {
            dataSource.setPoolPreparedStatements(archetype.getPoolPreparedStatements());
        }
        if (TempCarefreeAide.isNotNull(archetype.getMaxPoolPreparedStatementPerConnectionSize())) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(archetype.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getValidationQuery())) {
            dataSource.setValidationQuery(archetype.getValidationQuery());
        }
        if (TempCarefreeAide.isNotNull(archetype.getValidationQueryTimeout())) {
            dataSource.setValidationQueryTimeout(archetype.getValidationQueryTimeout());
        }
        if (TempCarefreeAide.isNotNull(archetype.getTestOnBorrow())) {
            dataSource.setTestOnBorrow(archetype.getTestOnBorrow());
        }
        if (TempCarefreeAide.isNotNull(archetype.getTestOnReturn())) {
            dataSource.setTestOnReturn(archetype.getTestOnReturn());
        }
        if (TempCarefreeAide.isNotNull(archetype.getTestWhileIdle())) {
            dataSource.setTestWhileIdle(archetype.getTestWhileIdle());
        }
        if (TempCarefreeAide.isNotNull(archetype.getKeepAlive())) {
            dataSource.setKeepAlive(archetype.getKeepAlive());
        }
        if (TempCarefreeAide.isNotNull(archetype.getTimeBetweenEvictionRunsMillis())) {
            dataSource.setTimeBetweenEvictionRunsMillis(archetype.getTimeBetweenEvictionRunsMillis());
        }
        if (TempCarefreeAide.isNotNull(archetype.getMinEvictableIdleTimeMillis())) {
            dataSource.setMinEvictableIdleTimeMillis(archetype.getMinEvictableIdleTimeMillis());
        }
        if (TempCarefreeAide.isNotEmpty(archetype.getConnectionInitSqls())) {
            dataSource.setConnectionInitSqls(archetype.getConnectionInitSqls());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getExceptionSorter())) {
            dataSource.setExceptionSorterClassName(archetype.getExceptionSorter());
        }
        if (TempCarefreeAide.isNotBlank(archetype.getFilters())) {
            dataSource.setFilters(archetype.getFilters());
        }
        if (TempCarefreeAide.isNotEmpty(archetype.getProxyFilters())) {
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