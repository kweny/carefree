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
package org.apenk.carefree.archetype;

import org.apenk.carefree.helper.CarefreeClassDeclaration;

/**
 * @author Kweny
 * @since 0.0.1
 */
public abstract class CarefreeArchetype {
    /**
     * carefree 自动装配过程监听器，
     * {@link org.apenk.carefree.listener.CarefreeConfigureListener} 接口的实现
     */
    private CarefreeClassDeclaration configureListener;
    /**
     * 是否启用本配置，默认为 true
     */
    private Boolean enabled = true;
    /**
     * 引用的另一个配置的 key，若本实例的某个属性未配置，则使用所引用配置的相应属性作为默认值
     */
    private String reference;

    public CarefreeClassDeclaration getConfigureListener() {
        return configureListener;
    }

    public void setConfigureListener(CarefreeClassDeclaration configureListener) {
        this.configureListener = configureListener;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
