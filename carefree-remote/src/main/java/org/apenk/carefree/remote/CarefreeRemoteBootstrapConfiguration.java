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

package org.apenk.carefree.remote;

import org.apenk.carefree.CarefreeOrdered;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
@ConditionalOnProperty(name = CarefreeRemoteAutoConfiguration.PropertyName_enabled)
public class CarefreeRemoteBootstrapConfiguration implements Ordered {

    @Bean
    @ConditionalOnMissingBean
    public CarefreeRemoteProperties carefreeCloudProperties() {
        return new CarefreeRemoteProperties();
    }

//    @Bean
//    public CarefreeCloudConfigLoader carefreeCloudConfigLoader(CarefreeRemoteProperties carefreeCloudProperties,
//                                                               @Nullable NacosConfigManager nacosConfigManager) {
//        if (TempCarefreeAide.equalsAnyIgnoreCase("nacos", carefreeCloudProperties.getServerType())) {
//            return new CarefreeCloudConfigNacosLoader(carefreeCloudProperties, nacosConfigManager);
//        }
//        return new CarefreeCloudConfigLoader._NoLoader(carefreeCloudProperties);
//    }

//    @Bean(CarefreeRegistry.BEAN_NAME)
//    public CarefreeRegistry carefreeConfig(CarefreeCloudConfigLoader carefreeCloudConfigLoader) {
//        return carefreeCloudConfigLoader.load();
//    }

    @Override
    public int getOrder() {
        return CarefreeOrdered.ORDER_CLOUD;
    }
}