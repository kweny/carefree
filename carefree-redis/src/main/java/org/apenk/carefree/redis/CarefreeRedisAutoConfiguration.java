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

import com.typesafe.config.Config;
import org.apenk.carefree.CarefreeOrdered;
import org.apenk.carefree.CarefreeRegistry;
import org.apenk.carefree.aide.StringAide;
import org.apenk.carefree.helper.CarefreeLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.util.Arrays;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRedisAutoConfiguration implements ApplicationContextAware, EnvironmentAware, BeanDefinitionRegistryPostProcessor, Ordered {
    public static final String PropertyName_enabled = "carefree.redis.enabled";
    public static final String PropertyName_Config = "carefree.redis.config-key";

    public static final CarefreeLogger logger = CarefreeLogger.getLogger("carefree.redis");

    private ApplicationContext applicationContext;
    private Environment environment;

    @Bean(CarefreeRedisRegistry.BEAN_NAME)
    @ConditionalOnProperty(name = PropertyName_enabled)
    public CarefreeRedisRegistry carefreeRedisRegistry(@Nullable ResourceLoader resourceLoader) {
        return new CarefreeRedisRegistry(resourceLoader);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        if (!isRedisEnabled()) {
            return;
        }

        if (!isCarefreeEnabled()) {
            logger.warn("carefree is not enabled, please set property \"carefree.enabled=true\" or \"carefree.cloud.enabled=true\" to enable it");
            return;
        }

        String configKey = environment.getProperty(PropertyName_Config, String.class, "");
        if (StringAide.isBlank(configKey)) {
            logger.warn("please specify a redis config key: carefree.redis.config-key=xxx[,yyy]");
            return;
        }

        if (!applicationContext.containsBean(CarefreeRegistry.BEAN_NAME)) {
            logger.warn("no carefree config");
            return;
        }

        CarefreeRegistry carefreeRegistry = applicationContext.getBean(CarefreeRegistry.BEAN_NAME, CarefreeRegistry.class);

        String[] keys = configKey.split(",");
        Arrays.stream(keys).filter(StringAide::isNotBlank).forEach(key -> {
            Config config = carefreeRegistry.get(key);
            if (config == null) {
                logger.warn("no redis config for key: {}", key);
                return; // means continue
            }

            try {
                CarefreeRedisBuilder.getInstance().loadConfig(config);
            } catch (Exception e) {
                throw new BeanCreationException("[Carefree] error to create the redis connection factory instance for config key: " + key, e);
            }
        });

        CarefreeRedisRegistry carefreeRedisRegistry = applicationContext.getBean(CarefreeRedisRegistry.BEAN_NAME, CarefreeRedisRegistry.class);

        CarefreeRedisBuilder.getInstance().build().forEach(carefreeRedisRegistry::register);
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
    }

    public boolean isRedisEnabled() {
        return this.environment.getProperty(PropertyName_enabled, Boolean.class, false);
    }

    public boolean isCarefreeEnabled() {
        return this.environment.getProperty("carefree.enabled", Boolean.class, false)
                || this.environment.getProperty("carefree.cloud.enabled", Boolean.class, false);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return CarefreeOrdered.ORDER_REDIS;
    }
}