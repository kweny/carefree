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
package org.apenk.carefree.aide;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 内省工具
 *
 * @author Kweny
 * @since 0.0.1
 */
public class IntrospectAide {
    /**
     * BeanInfo缓存
     */
    private static final Map<String, BeanInfo> BEAN_INFO_CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * 获取指定类的BeanInfo，止于指定的父类 stopClass。
     * 若stopClass是 beanClass 的直接父类，则只获取 beanClass 本身的BeanInfo
     *
     * @param beanClass 指定类
     * @param stopClass 终止类，必须为指定类的父类
     * @return beanInfo
     * @throws IntrospectionException IntrospectionException
     * @see #getSelfBeanInfo(Class)
     */
    public static BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass) throws IntrospectionException {
        if (stopClass == null) {
            stopClass = beanClass.getSuperclass();
        }
        String key = beanClass.getName() + "-" + stopClass.getName();
        BeanInfo beanInfo = BEAN_INFO_CACHE.get(key);

        if (beanInfo == null) {
            beanInfo = Introspector.getBeanInfo(beanClass, stopClass);
            BEAN_INFO_CACHE.put(key, beanInfo);

            // 清空 Introspector 本身的缓存
            Class<?> classForFlush = beanClass;
            do {
                Introspector.flushFromCaches(classForFlush);
                classForFlush = classForFlush.getSuperclass();
            } while (classForFlush != null);
        }

        return beanInfo;
    }


    /**
     * /**
     * 仅获取指定类本身的BeanInfo
     *
     * @param beanClass 指定类
     * @return BeanInfo
     * @throws IntrospectionException IntrospectionException
     * @see #getBeanInfo(Class, Class)
     */
    public static BeanInfo getSelfBeanInfo(Class<?> beanClass) throws IntrospectionException {
        return getBeanInfo(beanClass, beanClass.getSuperclass());
    }

    /**
     * 获取对象指定属性的值
     *
     * @param propertyName 属性名
     * @param bean 对象
     * @param beanClass 对象类型
     * @param stopClass 终止类，必须为 beanClass 的父类，若为 null 或 beanClass 的直接父类 则只检索 beanClass 本身的属性
     * @return 属性值
     * @throws IntrospectionException IntrospectionException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static Object getPropertyValue(String propertyName, Object bean, Class<?> beanClass, Class<?> stopClass)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = getBeanInfo(beanClass, stopClass);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (StringAide.equals(propertyName, propertyDescriptor.getName())) {
                return propertyDescriptor.getReadMethod().invoke(bean);
            }
        }
        return null;
    }

    /**
     * 获取对象指定属性的值
     *
     * @param propertyName 属性名
     * @param bean 对象
     * @param beanClass 对象类型
     * @return 属性值
     * @throws IntrospectionException IntrospectionException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     * @see #getPropertyValue(String, Object, Class, Class)
     */
    public static Object getPropertyValue(String propertyName, Object bean, Class<?> beanClass)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        return getPropertyValue(propertyName, bean, beanClass, null);
    }
}