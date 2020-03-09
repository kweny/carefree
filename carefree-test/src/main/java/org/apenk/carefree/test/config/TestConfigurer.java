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
import org.apenk.carefree.druid.CarefreeDruidRegistry;
import org.apenk.carefree.redis.CarefreeRedisRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * carefree config
 *
 * @author Kweny
 * @since 0.0.1
 */
@Configuration
public class TestConfigurer {

    private final CarefreeRegistry carefreeRegistry;
    private final CarefreeDruidRegistry carefreeDruidRegistry;
    private final CarefreeRedisRegistry carefreeRedisRegistry;

    @Autowired
    public TestConfigurer(CarefreeRegistry carefreeRegistry, CarefreeDruidRegistry carefreeDruidRegistry, CarefreeRedisRegistry carefreeRedisRegistry) {
        this.carefreeRegistry = carefreeRegistry;
        this.carefreeDruidRegistry = carefreeDruidRegistry;
        this.carefreeRedisRegistry = carefreeRedisRegistry;

        System.out.println("TestConfigurer\n");

        printCarefreeConfig();
        printCarefreeDruid();
        printCarefreeRedis();
    }

    private void printCarefreeConfig() {
        carefreeRegistry.getAll().forEach((key, config) -> {
            System.out.println("===== config " + key + " =====");
            config.entrySet().forEach(entry -> System.out.println("\t" + entry.getKey() + " = " + entry.getValue().unwrapped()));
            System.out.println("===== config " + key + " =====\n");
        });
    }

    private void printCarefreeDruid() {
        carefreeDruidRegistry.getAll().forEach((key, druid) -> {
            System.out.println("===== druid " + key + " =====");
            System.out.println(druid);
            System.out.println("===== druid " + key + " =====\n");
        });
    }

    private void printCarefreeRedis() {
        carefreeRedisRegistry.getAll().forEach((key, factory) -> {
            System.out.println("===== redis " + key + " =====");
            System.out.println(factory);
            System.out.println("===== redis " + key + " =====\n");
        });
    }

    @Bean
    public StringRedisTemplate mainTemplate() {
        return carefreeRedisRegistry.newStringTemplate("main");
    }

    @Bean
    public Object test(StringRedisTemplate mainTemplate) {
        mainTemplate.opsForValue().set("testa", "aaaa");
        System.out.println(mainTemplate.opsForValue().get("testa"));
        return new Object();
    }
}
