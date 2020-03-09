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
package org.apenk.carefree.test.config;

import org.apenk.carefree.CarefreeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * carefree config
 *
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
public class TestConfigurer {

    private final CarefreeRegistry carefreeRegistry;

    @Autowired
    public TestConfigurer(CarefreeRegistry carefreeRegistry) {
        System.out.println("TestConfigurer");
        this.carefreeRegistry = carefreeRegistry;

        printCarefreeConfig();
    }

    private void printCarefreeConfig() {
        carefreeRegistry.getAllConfigs().forEach((key, config) -> {
            System.out.println("===== " + key + " =====");
            config.entrySet().forEach(entry -> System.out.println(entry.getKey() + " = " + entry.getValue().unwrapped()));
            System.out.println("===== " + key + " =====\n");
        });
    }
}
