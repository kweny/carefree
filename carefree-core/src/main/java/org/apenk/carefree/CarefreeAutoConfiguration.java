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
import com.typesafe.config.ConfigValue;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.*;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

/**
 * carefree 自动配置
 *
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
public class CarefreeAutoConfiguration implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware, EnvironmentAware, Ordered {

    public static final String PropertyName_enabled = "carefree.enabled";

    private Environment environment;
    private ApplicationContext applicationContext;
    private ScopeMetadataResolver scopeMetadataResolver;

    public CarefreeAutoConfiguration() {
        this.scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    }

    @Bean(CarefreeRegistry.BEAN_NAME)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = PropertyName_enabled)
    public CarefreeRegistry carefreeRegistry() {
        // 如果启用了 carefree-cloud 模块，则该 Bean 将由 carefree-cloud 模块创建
        return new CarefreeRegistry();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry registry) throws BeansException {
        if (!isCarefreeEnabled()) {
            return;
        }

        CarefreeProperties properties = buildCarefreeProperties();

        // 加载本地配置
        Map<String, List<Resource>> resourcesMap = CarefreeConfigLocator.locate(properties);
        Map<String, Config> configCache = CarefreeConfigLoader.load(resourcesMap);

        CarefreeRegistry carefreeRegistry = null;
        if (applicationContext.containsBean(CarefreeRegistry.BEAN_NAME)) {
            carefreeRegistry = applicationContext.getBean(CarefreeRegistry.BEAN_NAME, CarefreeRegistry.class);
        }
        if (carefreeRegistry != null) {
            // 如果已经存在 CarefreeRegistry 的 Bean（由 cloud 模块加载的配置中心的配置）
            for (Map.Entry<String, Config> entry : configCache.entrySet()) {
                Config cloudConfig = carefreeRegistry.get(entry.getKey());
                if (cloudConfig == null) {
                    // 如果远端配置中心没有该 key 的配置，加载整个本地配置
                    carefreeRegistry.register(entry.getKey(), entry.getValue());
                } else {
                    // 如果远端配置中心存在该 key 的配置，加载远端没有的选项
                    for (Map.Entry<String, ConfigValue> localOption : entry.getValue().entrySet()) {
                        if (!cloudConfig.hasPath(localOption.getKey())) {
                            cloudConfig = cloudConfig.withValue(localOption.getKey(), localOption.getValue());
                        }
                    }
                    carefreeRegistry.register(entry.getKey(), cloudConfig);
                }
            }
        } else {
            // 注册 CarefreeRegistry Bean 定义
            // （注：该部分为遗留代码，由于增加了 carefreeRegistry() 方法的 Bean 声明，因此这部分 Bean 定义其实已经不需要了）
            AnnotatedGenericBeanDefinition definition = new AnnotatedGenericBeanDefinition(CarefreeRegistry.class);
            ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(definition);
            definition.setScope(scopeMetadata.getScopeName());
            definition.getConstructorArgumentValues().addGenericArgumentValue(configCache);
            AnnotationConfigUtils.processCommonDefinitionAnnotations(definition);
            BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(definition, CarefreeRegistry.BEAN_NAME);
            BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
        }
    }

    private CarefreeProperties buildCarefreeProperties() {
        Binder binder = Binder.get(environment);
        BindResult<CarefreeProperties> bindResult = binder.bind(CarefreeProperties.PREFIX, Bindable.of(CarefreeProperties.class), new BindHandler() {
            @Override
            public Object onSuccess(ConfigurationPropertyName name, Bindable<?> target, BindContext context, Object result) {
                String nameString = name.toString();
                // 每当一个 CarefreeProperties#Position 成功创建之后，检查其属性的合法性
                if (CarefreeProperties.ONE_POSITION_NAME_PATTERN.matcher(nameString).matches()) {
                    CarefreeProperties.Position position = (CarefreeProperties.Position) result;
                    if (TempCarefreeAide.isBlank(position.getName())) {
                        throw new RuntimeException("[Carefree] position id is missing: " + nameString + ".name");
                    }
                }
                return result;
            }
        });
        return bindResult.orElseGet(CarefreeProperties::new);
    }

    private boolean isCarefreeEnabled() {
        return this.environment.getProperty(PropertyName_enabled, Boolean.class, false);
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // do nothing
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
        return CarefreeOrdered.ORDER_LOCAL;
    }
}
