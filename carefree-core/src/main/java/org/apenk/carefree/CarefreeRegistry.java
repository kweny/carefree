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
package org.apenk.carefree;

import com.typesafe.config.Config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持有 carefree 配置数据，
 * 以 carefreeRegistry 为 bean name 存在于容器，
 * 可注入到应用程序中，使用 {@link #getConfig(String)} 方法获取指定配置数据，
 * 或使用 {@link #getAllConfigs()} 方法获取所有配置数据。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRegistry {
    public static final String BEAN_NAME = "carefreeRegistry";

    public static final CarefreeRegistry EMPTY = new CarefreeRegistry();

    /** < key, 配置数据 > */
    private final Map<String, Config> configHolder;

    public CarefreeRegistry() {
        this.configHolder = new ConcurrentHashMap<>();
    }

    public CarefreeRegistry(Map<String, Config> configMap) {
        this();
        this.configHolder.putAll(configMap);
    }

    public void register(String key, Config config) {
        this.configHolder.put(key, config);
    }

    public Config getConfig(String key) {
        return this.configHolder.get(key);
    }

    public Map<String, Config> getAllConfigs() {
        return Collections.unmodifiableMap(this.configHolder);
    }

}
