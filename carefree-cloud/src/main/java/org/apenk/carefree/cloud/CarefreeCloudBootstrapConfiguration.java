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
package org.apenk.carefree.cloud;

import com.alibaba.cloud.nacos.NacosConfigManager;
import org.apenk.carefree.CarefreeOrdered;
import org.apenk.carefree.CarefreeRegistry;
import org.apenk.carefree.aide.StringAide;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
@ConditionalOnProperty(name = CarefreeCloudAutoConfiguration.PropertyName_enabled)
public class CarefreeCloudBootstrapConfiguration implements Ordered {

    @Bean
    @ConditionalOnMissingBean
    public CarefreeCloudProperties carefreeCloudProperties() {
        return new CarefreeCloudProperties();
    }

    @Bean
    public CarefreeCloudConfigLoader carefreeCloudConfigLoader(CarefreeCloudProperties carefreeCloudProperties,
                                                               @Nullable NacosConfigManager nacosConfigManager) {
        if (StringAide.equalsAnyIgnoreCase("nacos", carefreeCloudProperties.getServiceType())) {
            return new CarefreeCloudConfigNacosLoader(carefreeCloudProperties, nacosConfigManager);
        }
        return new CarefreeCloudConfigLoader._NoLoader(carefreeCloudProperties);
    }

    @Bean(CarefreeRegistry.BEAN_NAME)
    public CarefreeRegistry carefreeConfig(CarefreeCloudConfigLoader carefreeCloudConfigLoader) {
        return carefreeCloudConfigLoader.load();
    }

    @Override
    public int getOrder() {
        return CarefreeOrdered.ORDER_CLOUD;
    }
}