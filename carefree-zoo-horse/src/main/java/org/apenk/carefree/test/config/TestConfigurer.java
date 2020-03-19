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
import org.apenk.carefree.test.entity.Horse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.awt.*;

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
    public TestConfigurer(@Nullable CarefreeRegistry carefreeRegistry,
                          @Nullable CarefreeDruidRegistry carefreeDruidRegistry,
                          @Nullable CarefreeRedisRegistry carefreeRedisRegistry) {
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
    public StringRedisTemplate mainStringTemplate() {
        return carefreeRedisRegistry.newStringTemplate("main");
    }

    @Bean
    public Object testMainString(StringRedisTemplate mainStringTemplate) {
        mainStringTemplate.opsForValue().set("mainKey", "mainValue");
        System.out.println(mainStringTemplate.opsForValue().get("mainKey"));
        return new Object();
    }

    @Bean
    public StringRedisTemplate testStringTemplate() {
        return carefreeRedisRegistry.newStringTemplate("test");
    }

    @Bean
    public RedisTemplate<Point, Horse> testGenTemplate() {
        return carefreeRedisRegistry.newTemplate("test");
    }

    @Bean
    public ReactiveStringRedisTemplate testReactStringTemplate() {
        return carefreeRedisRegistry.newReactiveStringRedisTemplate("test");
    }

    @Bean
    public ReactiveRedisTemplate<Point, Horse> testReactGenTemplate() {
        return carefreeRedisRegistry.newReactiveRedisTemplate("test");
    }

    @Bean
    public Object testString(StringRedisTemplate testStringTemplate) {
        testStringTemplate.opsForValue().set("testString", "testString");
        System.out.println("testString = " + testStringTemplate.opsForValue().get("testString"));
        return new Object();
    }

    @Bean
    public Object testGen(RedisTemplate<Point, Horse> testGenTemplate) {
        Point point = new Point(1, 1);
        Horse horse = new Horse("testGen", 11);
        testGenTemplate.opsForValue().set(point, horse);
        Horse get = testGenTemplate.opsForValue().get(point);
        System.out.println("testGen = " + get);
        return new Object();
    }

    @Bean
    public Object testReactString(ReactiveStringRedisTemplate testReactStringTemplate) {
        testReactStringTemplate.opsForValue().set("testReactString", "testReactString").subscribe();
        System.out.println("testReactString = " + testReactStringTemplate.opsForValue().get("testReactString").block());
        return new Object();
    }

    @Bean
    public Object testReactGen(ReactiveRedisTemplate<Point, Horse> testReactTemplate) {
        Point point = new Point(2, 2);
        Horse horse = new Horse("testReactGen", 22);
        testReactTemplate.opsForValue().set(point, horse).subscribe();
        Mono<Horse> mono = testReactTemplate.opsForValue().get(point);
        System.out.println("testReactGen = " + mono.block());
        return new Object();
    }
}
