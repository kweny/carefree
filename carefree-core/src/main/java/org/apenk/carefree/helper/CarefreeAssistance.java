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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * carefree 工具方法
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeAssistance {
    private static final String FORCE_DEFAULT = "force-default";
    private static final Map<String, BeanInfo> BEAN_INFO_CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    public static BeanInfo getBeanInfo(Class<?> beanClass, Class<?> stopClass) throws IntrospectionException {
        if (stopClass == null) {
            stopClass = beanClass.getSuperclass();
        }
        String key = beanClass.getName() + "-" + stopClass.getName();
        BeanInfo beanInfo = BEAN_INFO_CACHE.get(key);

        if (beanInfo == null) {
            beanInfo = Introspector.getBeanInfo(beanClass, stopClass);
            BEAN_INFO_CACHE.put(key, beanInfo);

            Class<?> classForFlush = beanClass;
            do {
                Introspector.flushFromCaches(classForFlush);
                classForFlush = classForFlush.getSuperclass();
            } while (classForFlush != null);
        }

        return beanInfo;
    }

    public static Set<String> getConfigRoots(Config config) {
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
        BeanInfo targetBean = getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] beanProperties = targetBean.getPropertyDescriptors();
        for (PropertyDescriptor beanProperty : beanProperties) {
//            if (StringAide.equals("reference", beanProperty.getName())) {
//                continue;
//            }
            Method writeMethod = beanProperty.getWriteMethod();
            Method readMethod = beanProperty.getReadMethod();
            if (writeMethod == null || readMethod == null) {
                continue;
            }

            Object value = readMethod.invoke(target);
            if (TempCarefreeAide.isNull(value)) {
                Object anotherValue = readMethod.invoke(another);
                if (TempCarefreeAide.isNotNull(anotherValue)) {
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
        BeanInfo targetBean = getBeanInfo(clazz, Object.class);
        PropertyDescriptor[] beanProperties = targetBean.getPropertyDescriptors();
        for (PropertyDescriptor beanProperty : beanProperties) {
            Method writeMethod = beanProperty.getWriteMethod();
            if (writeMethod == null) {
                continue;
            }
            String name = beanProperty.getName();
            String propertyPath;

            String newPrefix = TempCarefreeAide.isNotBlank(prefix) ? prefix + "." : "";
            String newDefaultPrefix = TempCarefreeAide.isNotBlank(defaultPrefix) ? defaultPrefix + "." : "";

            String kebabName = NamingConvention.KEBAB.fromCamel(name);
            String snakeName = NamingConvention.SNAKE.fromCamel(name);

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
            } else if (TempCarefreeAide.isNotBlank(newDefaultPrefix)) {
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

            Object configValue = config.getValue(propertyPath).unwrapped();
            if ((configValue instanceof String)
                    && TempCarefreeAide.equalsIgnoreCase(FORCE_DEFAULT, (String) configValue)) {
                continue;
            }

            Class<?> propertyType = beanProperty.getPropertyType();
            if (propertyType == CarefreeClassDeclaration.class) {

                CarefreeClassDeclaration wrapper = new CarefreeClassDeclaration();
                Object configAny = config.getAnyRef(propertyPath);
                if (configAny instanceof String) {
                    String stringValue = (String) configAny;
                    String upperCase = stringValue.toUpperCase().trim();
                    if (upperCase.startsWith("DEFINED(") && upperCase.endsWith(")")) {
                        String definedValue = stringValue.trim().substring(8, stringValue.trim().length() - 1);
                        wrapper.setDefinedValue(definedValue);
                    } else {
                        wrapper.setClassName(stringValue);
                    }
                } else {
                    Config wrapperConfig = config.getConfig(propertyPath);
                    loadBeanProperties(CarefreeClassDeclaration.class, wrapper, wrapperConfig, null, null);
                }
                writeMethod.invoke(bean, wrapper);

            } else if (Map.class.isAssignableFrom(propertyType)) {

                @SuppressWarnings("unchecked")
                Map<Object, Object> map = (Map<Object, Object>) propertyType.getConstructor().newInstance();
                for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
                    map.put(entry.getKey(), entry.getValue().unwrapped());
                }
                writeMethod.invoke(bean, map);

            } else if (propertyType == String.class) {

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

            } else if (propertyType.isArray()) {

                writeMethod.invoke(bean, (Object) config.getAnyRefList(propertyPath).toArray());

            } else {

                writeMethod.invoke(bean, config.getAnyRef(propertyPath));

            }
        }
    }

}
