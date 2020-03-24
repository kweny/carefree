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
package org.apenk.carefree.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeClassDeclaration {
    private static final Map<String, Object> INSTANCE_CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * 类全名
     */
    private String className;
    /**
     * 构造方法参数，若构造方法参数中含有基本类型，则应在声明时使用其包装类型
     */
    private Object[] constructorArgs;
    /**
     * 初始化方法，若方法参数中含有基本类型，则应在声明时使用其包装类型
     */
    private String initializeMethod;
    /**
     * 初始化方法参数
     */
    private Object[] initializeArgs;
    /**
     * 静态工厂方法，若方法参数中含有基本类型，则应在声明时使用其包装类型
     */
    private String staticFactoryMethod;
    /**
     * 静态工厂方法参数
     */
    private Object[] staticFactoryArgs;

    public <T> T instance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        if (CarefreeAide.isBlank(className)) {
            return null;
        }
        Object instance = INSTANCE_CACHE.get(className);
        if (instance == null) {
            Class<?> clazz = Class.forName(className);
            if (CarefreeAide.isNotBlank(staticFactoryMethod)) { // 优先使用静态工厂方法进行实例化
                Class<?>[] parameterTypes = getParameterTypes(staticFactoryArgs);
                instance = clazz.getMethod(staticFactoryMethod, parameterTypes).invoke(null, staticFactoryArgs);
            } else { // 若无工厂方法则使用构造方法
                Class<?>[] parameterTypes = getParameterTypes(constructorArgs);
                instance = clazz.getConstructor(parameterTypes).newInstance(constructorArgs);
            }
            if (CarefreeAide.isNotBlank(initializeMethod) && instance != null) { // 实例化后调用初始化方法（若存在）
                Class<?>[] parameterTypes = getParameterTypes(initializeArgs);
                clazz.getMethod(initializeMethod, parameterTypes).invoke(instance, initializeArgs);
            }
            INSTANCE_CACHE.put(className, instance);
        }
        @SuppressWarnings("unchecked")
        final T result = (T) instance;
        return result;
    }

    private Class<?>[] getParameterTypes(Object[] args) {
        Class<?>[] parameterTypes = null;
        if (CarefreeAide.isNotEmpty(args)) {
            parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }
        return parameterTypes;
    }

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
