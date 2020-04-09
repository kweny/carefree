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

package org.apenk.carefree.druid.listener;

/**
 * <p>
 *     若要监听 Carefree Druid 的构建过程，或者对构建过程进行干预，可以实现该接口。
 * </p>
 *
 * <p>
 *      Carefree Redis 的构建过程大体分为两个步骤——
 *      <ul>
 *          <li>
 *              1. 加载配置文件并创建相应的 Java 对象，用于描述配置文件中的配置选项：
 *              <ul>
 *                  <li>配置描述对象：{@link org.apenk.carefree.druid.archetype.CarefreeDruidArchetype}</li>
 *              </ul>
 *              该步骤完成后将触发监听器的 {@link #archetype(CarefreeDruidConfigureEvent)} 方法，
 *              此时可以从事件参数中获取上述对象，并可对这些对象进行修改操作，这些修改将被应用到后续步骤中。
 *          </li>
 *          <li>
 *              2. 利用第一步的描述对象创建并注册数据源对象 DruidDataSource：
 *              <ul>
 *                  <li>{@link com.alibaba.druid.pool.DruidDataSource}</li>
 *              </ul>
 *              该步骤完成后将触发 {@link #dataSource(CarefreeDruidConfigureEvent)} 方法，
 *              可以从事件参数中获取数据源对象。
 *              至此构建过程全部完成，可以利用 {@link org.apenk.carefree.druid.CarefreeDruidRegistry} Bean 获取指定数据源对象。
 *          </li>
 *      </ul>
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public interface CarefreeDruidConfigureListener {

    void archetype(CarefreeDruidConfigureEvent event);

    void dataSource(CarefreeDruidConfigureEvent event);

}
