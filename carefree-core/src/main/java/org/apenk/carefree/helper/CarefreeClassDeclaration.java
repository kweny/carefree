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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeClassDeclaration {
    private static final Map<String, Object> INSTANCE_CACHE = new ConcurrentHashMap<>();
    private static final ReentrantLock lock = new ReentrantLock();

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
    /**
     * singleton/prototype
     */
    private String scope;

    public <T> T instance() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        try {
            lock.lock();
            if (TempCarefreeAide.isBlank(className)) {
                return null;
            }
            Object instance;
            if (TempCarefreeAide.equalsIgnoreCase(scope, "prototype")) {
                instance = newInstance();
            } else {
                instance = INSTANCE_CACHE.computeIfAbsent(className, k -> newInstance());
            }
            @SuppressWarnings("unchecked")
            final T result = (T) instance;
            return result;
        } finally {
            lock.unlock();
        }
    }

    private Object newInstance() {
        try {
            Object instance;

            Class<?> clazz = Class.forName(className);

            if (TempCarefreeAide.isNotBlank(staticFactoryMethod)) {
                // 优先使用静态工厂方法进行实例化
                Class<?>[] parameterTypes = getParameterTypes(staticFactoryArgs);
                instance = clazz.getMethod(staticFactoryMethod, parameterTypes).invoke(null, staticFactoryArgs);
            } else {
                // 若无工厂方法则使用构造方法
                Class<?>[] parameterTypes = getParameterTypes(constructorArgs);
                instance = clazz.getConstructor(parameterTypes).newInstance(constructorArgs);
            }

            // 实例化后调用初始化方法（若存在）
            if (TempCarefreeAide.isNotBlank(initializeMethod) && instance != null) {
                Class<?>[] parameterTypes = getParameterTypes(initializeArgs);
                clazz.getMethod(initializeMethod, parameterTypes).invoke(instance, initializeArgs);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?>[] getParameterTypes(Object[] args) {
        Class<?>[] parameterTypes = null;
        if (TempCarefreeAide.isNotEmpty(args)) {
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
