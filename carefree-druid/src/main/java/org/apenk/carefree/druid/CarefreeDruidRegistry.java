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

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     持有 DruidDataSource 对象，
 *     以 carefreeDruidRegistry 为 bean name 存在 Spring 于容器，
 *     可注入到应用程序中，用于数据库相关操作。
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeDruidRegistry {
    public static final String BEAN_NAME = "carefreeDruidRegistry";

    public Map<String, DruidDataSource> holder;

    public CarefreeDruidRegistry() {
        this.holder = new ConcurrentHashMap<>();
    }

    public void register(String root, CarefreeDruidPayload payload) {
        this.holder.put(root, payload.dataSource);
    }

    public DruidDataSource getDataSource(String root) {
        return this.holder.get(root);
    }

    public Map<String, DruidDataSource> getAllDataSources() {
        return Collections.unmodifiableMap(this.holder);
    }
}
