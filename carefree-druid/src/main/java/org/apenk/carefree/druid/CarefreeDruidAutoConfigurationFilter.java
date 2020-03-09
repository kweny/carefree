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

import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 排除 spring boot 原来的数据源自动配置
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeDruidAutoConfigurationFilter implements AutoConfigurationImportFilter {
    private static final Set<String> EXCLUSIONS = new HashSet<>(
            Arrays.asList(
                    DataSourceAutoConfiguration.class.getName(),
                    DataSourceTransactionManagerAutoConfiguration.class.getName()
            )
    );

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            matches[i] = !EXCLUSIONS.contains(autoConfigurationClasses[i]);
        }
        return matches;
    }
}
