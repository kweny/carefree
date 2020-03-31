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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持有 carefree druid 配置及 {@link DruidDataSource} 对象，
 * 以 carefreeDruidRegistry 为 bean name 存在于容器，
 * 可注入到应用程序中，使用 {@link #get(String)} 方法获取指定 {@link DruidDataSource} 对象，
 * 或使用 {@link #getAll()} 方法获取所有 {@link DruidDataSource} 对象。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeDruidRegistry {
    public static final String BEAN_NAME = "carefreeDruidRegistry";

    public Map<String, CarefreeDruidWrapper> holder;

    public CarefreeDruidRegistry() {
        this.holder = new ConcurrentHashMap<>();
    }

    public void register(String name, CarefreeDruidWrapper wrapper) {
        this.holder.put(name, wrapper);
    }

    public DruidDataSource get(String name) {
        CarefreeDruidWrapper wrapper = holder.get(name);
        return wrapper != null ? wrapper.getDataSource() : null;
    }

    public Map<String, DruidDataSource> getAll() {
        Map<String, DruidDataSource> map = new ConcurrentHashMap<>(this.holder.size());
        this.holder.forEach((key, value) -> map.put(key, value.getDataSource()));
        return map;
    }
}
