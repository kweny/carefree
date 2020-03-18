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
package org.apenk.carefree;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeClassWrapper {
    /**
     * 类全名
     */
    private String className;
    /**
     * 构造方法参数
     */
    private Object[] constructorArgs;
    /**
     * 初始化方法
     */
    private String initializeMethod;
    /**
     * 初始化方法参数
     */
    private Object[] initializeArgs;
    /**
     * 静态工厂方法
     */
    private String staticFactoryMethod;
    /**
     * 静态工厂方法参数
     */
    private Object[] staticFactoryArgs;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(Object[] constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public String getInitializeMethod() {
        return initializeMethod;
    }

    public void setInitializeMethod(String initializeMethod) {
        this.initializeMethod = initializeMethod;
    }

    public Object[] getInitializeArgs() {
        return initializeArgs;
    }

    public void setInitializeArgs(Object[] initializeArgs) {
        this.initializeArgs = initializeArgs;
    }

    public String getStaticFactoryMethod() {
        return staticFactoryMethod;
    }

    public void setStaticFactoryMethod(String staticFactoryMethod) {
        this.staticFactoryMethod = staticFactoryMethod;
    }

    public Object[] getStaticFactoryArgs() {
        return staticFactoryArgs;
    }

    public void setStaticFactoryArgs(Object[] staticFactoryArgs) {
        this.staticFactoryArgs = staticFactoryArgs;
    }
}
