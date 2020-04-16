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
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 解析并加载 carefree 配置数据
 *
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeConfigLoader {

    static Map<String, Config> load(Map<String, List<Resource>> resourceMap) {
        Map<String, Config> configCache = new ConcurrentHashMap<>();

        if (TempCarefreeAide.isEmpty(resourceMap)) {
            return configCache;
        }

        resourceMap.forEach((key, resources) -> resources.forEach(resource -> {
            try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                Config config = ConfigFactory.parseReader(reader).resolve();
                Config cachedConfig = configCache.get(key);
                if (cachedConfig == null) {
                    configCache.put(key, config);
                } else {
                    for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
                        cachedConfig = cachedConfig.withValue(entry.getKey(), entry.getValue());
                    }
                    configCache.put(key, cachedConfig);
                }
            } catch (Exception e) {
                throw new RuntimeException("[Carefree] error to read config: " + resource, e);
            }
        }));

        return configCache;
    }

}
