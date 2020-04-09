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

import com.alibaba.druid.pool.DruidDataSource;
import org.apenk.carefree.druid.archetype.CarefreeDruidArchetype;

/**
 * 监听器接口 {@link CarefreeDruidConfigureListener} 的事件对象。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeDruidConfigureEvent {
    private String key;
    private String root;

    private CarefreeDruidArchetype druidArchetype;

    private DruidDataSource dataSource;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public CarefreeDruidArchetype getDruidArchetype() {
        return druidArchetype;
    }

    public void setDruidArchetype(CarefreeDruidArchetype druidArchetype) {
        this.druidArchetype = druidArchetype;
    }

    public DruidDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
