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

import org.apenk.carefree.CarefreeOrdered;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * carefree cloud 自动配置
 *
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
@ConditionalOnProperty(name = CarefreeCloudAutoConfiguration.PropertyName_enabled)
public class CarefreeCloudAutoConfiguration implements Ordered {
    public static final String PropertyName_enabled = "carefree.cloud.enabled";

    @Bean
    public CarefreeCloudProperties carefreeCloudProperties(ApplicationContext context) {
        if (context.getParent() != null
                && BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                context.getParent(), CarefreeCloudProperties.class).length > 0) {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(context.getParent(),
                    CarefreeCloudProperties.class);
        }
        return new CarefreeCloudProperties();
    }

    @Override
    public int getOrder() {
        return CarefreeOrdered.ORDER_CLOUD;
    }
}