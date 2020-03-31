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

package org.apenk.carefree.redis;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 排除 spring boot 原来的 redis 自动配置
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisAutoConfigurationFilter implements AutoConfigurationImportFilter, EnvironmentAware {
    private static final Set<String> EXCLUSIONS = new HashSet<>(
            Arrays.asList(
                    RedisAutoConfiguration.class.getName(),
                    RedisRepositoriesAutoConfiguration.class.getName(),
                    RedisReactiveAutoConfiguration.class.getName()
            )
    );

    private Environment environment;

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            matches[i] = !(isRedisEnabled() && EXCLUSIONS.contains(autoConfigurationClasses[i]));
        }
        return matches;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }

    public boolean isRedisEnabled() {
        return this.environment.getProperty(CarefreeRedisAutoConfiguration.PropertyName_enabled, Boolean.class, false);
    }
}