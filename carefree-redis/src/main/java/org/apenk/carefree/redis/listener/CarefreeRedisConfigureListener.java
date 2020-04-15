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

package org.apenk.carefree.redis.listener;

import org.apenk.carefree.listener.CarefreeConfigureListener;

/**
 * <p>
 *     若要监听 Carefree Redis 的构建过程，或者对构建过程进行干预，可以实现该接口。
 * </p>
 *
 * <p>Carefree Redis 的构建过程大体分为三个步骤——</p>
 *      <ul>
 *          <li>
 *              1. 加载配置文件并创建相应的 Java 对象，它们与配置文件中的配置项对应，包括：
 *              <ul>
 *                  <li>连接配置描述对象：{@link org.apenk.carefree.redis.archetype.CarefreeRedisArchetype}</li>
 *                  <li>连接池描述对象：{@link org.apenk.carefree.redis.archetype.CarefreeRedisArchetypePool}</li>
 *                  <li>客户端本地资源描述对象：{@link org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeResources}</li>
 *                  <li>客户端功能选项描述对象：{@link org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeOptions}</li>
 *                  <li>序列化器描述对象：{@link org.apenk.carefree.redis.archetype.CarefreeRedisArchetypeSerializer}</li>
 *              </ul>
 *              该步骤完成后将触发监听器的 {@link #archetype(CarefreeRedisConfigureEvent)} 方法，
 *              此时可以从事件参数中获取上述对象，并可对这些对象进行修改操作，这些修改将被应用到后续步骤中。
 *          </li>
 *          <li>
 *              2. 利用第一步的描述对象创建 Redis 连接配置对象和客户端配置对象：
 *              <ul>
 *                  <li>{@link org.springframework.data.redis.connection.RedisConfiguration}</li>
 *                  <li>{@link org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration}</li>
 *              </ul>
 *              该步骤完成后将触发监听器的 {@link #configuration(CarefreeRedisConfigureEvent)} 方法，
 *              可以从事件参数中获取这两个对象，对其进行的修改操作依然会被应用到后续步骤中。
 *          </li>
 *          <li>
 *              3. 创建并注册 Redis 连接工厂对象：
 *              <ul>
 *                  <li>{@link org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory}</li>
 *              </ul>
 *              该步骤完成后将触发 {@link #factory(CarefreeRedisConfigureEvent)} 方法，
 *              可以从事件参数中获取连接工厂对象。
 *              至此构建过程全部完成，可以利用 {@link org.apenk.carefree.redis.CarefreeRedisRegistry} Bean 获取指定的工厂对象、序列化器、以及直接创建 Redis 模板对象。
 *          </li>
 *      </ul>
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CarefreeRedisConfigureListener extends CarefreeConfigureListener {

    default void archetype(CarefreeRedisConfigureEvent event) {}

    default void configuration(CarefreeRedisConfigureEvent event) {}

    default void factory(CarefreeRedisConfigureEvent event) {}

}
