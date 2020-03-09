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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import org.apenk.carefree.aide.IntrospectAide;
import org.apenk.carefree.aide.ObjectAide;
import org.apenk.carefree.aide.StringAide;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * carefree 工具方法
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeAssistance {
    public static Set<String> getConfigRootKeys(Config config) {
        Set<String> rootKeys = new HashSet<>();
        for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
            String key = entry.getKey();
            int pointIndex = key.indexOf(".");
            if (pointIndex > 0) {
                rootKeys.add(key.substring(0, pointIndex));
            }
        }
        return rootKeys;
    }

    public static <T> void loadReference(Class<T> clazz, T target, T another) throws Exception {
        BeanInfo targetBean = IntrospectAide.getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] beanProperties = targetBean.getPropertyDescriptors();
        for (PropertyDescriptor beanProperty : beanProperties) {
            Method writeMethod = beanProperty.getWriteMethod();
            Method readMethod = beanProperty.getReadMethod();
            if (writeMethod == null || readMethod == null) {
                continue;
            }

            Object value = readMethod.invoke(target);
            if (ObjectAide.isNull(value)) {
                Object anotherValue = readMethod.invoke(another);
                if (ObjectAide.isNotNull(anotherValue)) {
                    writeMethod.invoke(target, anotherValue);
                }
            }
        }
    }

    public static <T> T fromConfig(Class<T> clazz, Config config) throws Exception {
        T bean = clazz.getConstructor().newInstance();
        loadBeanProperties(clazz, bean, config, null, null);
        return bean;
    }

    public static <T> T fromConfig(Class<T> clazz, Config config, String prefix) throws Exception {
        T bean = clazz.getConstructor().newInstance();
        loadBeanProperties(clazz, bean, config, prefix, null);
        return bean;
    }

    public static <T> T fromConfig(Class<T> clazz, Config config, String prefix, String defaultPrefix) throws Exception {
        T bean = clazz.getConstructor().newInstance();
        loadBeanProperties(clazz, bean, config, prefix, defaultPrefix);
        return bean;
    }

    public static void loadBeanProperties(Class<?> clazz, Object bean, Config config, String prefix, String defaultPrefix) throws Exception {
        BeanInfo targetBean = IntrospectAide.getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] beanProperties = targetBean.getPropertyDescriptors();
        for (PropertyDescriptor beanProperty : beanProperties) {
            Method writeMethod = beanProperty.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            String name = beanProperty.getName();
            String propertyPath;

            String newPrefix = StringAide.isNotBlank(prefix) ? prefix + "." : "";
            String newDefaultPrefix = StringAide.isNotBlank(defaultPrefix) ? defaultPrefix + "." : "";

            String kebabName = NamingConverter.camel2spinal(name);
            String snakeName = NamingConverter.camel2Snake(name);

            String specifiedPropertyPath = newPrefix + name;
            String specifiedPropertyPathKebab = newPrefix + kebabName;
            String specifiedPropertyPathSnake = newPrefix + snakeName;
            String defaultPropertyPath = newDefaultPrefix + name;
            String defaultPropertyPathKebab = newDefaultPrefix + kebabName;
            String defaultPropertyPathSnake = newDefaultPrefix + snakeName;

            if (config.hasPath(specifiedPropertyPath)) {
                propertyPath = specifiedPropertyPath;
            } else if (config.hasPath(specifiedPropertyPathKebab)) {
                propertyPath = specifiedPropertyPathKebab;
            } else if (config.hasPath(specifiedPropertyPathSnake)) {
                propertyPath = specifiedPropertyPathSnake;
            } else if (StringAide.isNotBlank(newDefaultPrefix)) {
                if (config.hasPath(defaultPropertyPath)) {
                    propertyPath = defaultPropertyPath;
                } else if (config.hasPath(defaultPropertyPathKebab)) {
                    propertyPath = defaultPropertyPathKebab;
                } else if (config.hasPath(defaultPropertyPathSnake)) {
                    propertyPath = defaultPropertyPathSnake;
                } else {
                    continue;
                }
            } else {
                continue;
            }

            Class<?> propertyType = beanProperty.getPropertyType();
            if (propertyType == String.class) {

                writeMethod.invoke(bean, config.getString(propertyPath));

            } else if (propertyType == Byte.class || "byte".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getNumber(propertyPath).byteValue());

            } else if (propertyType == Short.class || "short".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getNumber(propertyPath).shortValue());

            } else if (propertyType == Integer.class || "int".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getInt(propertyPath));

            } else if (propertyType == Long.class || "long".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getLong(propertyPath));

            } else if (propertyType == Float.class || "float".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getNumber(propertyPath).floatValue());

            } else if (propertyType == Double.class || "double".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getDouble(propertyPath));

            } else if (propertyType == Boolean.class || "boolean".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getBoolean(propertyPath));

            } else if (propertyType == Character.class || "char".equals(propertyType.getName())) {

                writeMethod.invoke(bean, config.getString(propertyPath).charAt(0));

            }
        }
    }
}
