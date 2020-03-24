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

import org.apenk.carefree.helper.CarefreeAide;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.*;

/**
 * 定位 carefree 配置文件位置
 *
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeConfigLocator {
    /** 配置文件约定目录，优先级递增，后面的配置将覆盖前面的（若后面配置中某个属性未指定则不会覆盖） */
    private static final String[] DEFAULT_PATHS = {"classpath:/", "classpath:/config/", "file:./", "file:./config/"};
    /** 配置文件约定类型，优先级递增，后面的配置将覆盖前面的（若后面配置中某个属性未指定则不会覆盖） */
    private static final String[] DEFAULT_TYPES = {".properties", ".json", ".conf"};

    static Map<String, List<Resource>> locate(CarefreeProperties properties) {
        return resolveConfigResources(resolveConfigPositions(properties));
    }

    /**
     * 解析 carefree.position 和 carefree.positions 配置项，
     * 将其所描述的文件位置转换为 {@link CarefreeProperties.Position} 集合，
     * 需要保证先后顺序。
     */
    private static List<CarefreeProperties.Position> resolveConfigPositions(CarefreeProperties properties) {
        List<CarefreeProperties.Position> allPositions = new LinkedList<>();

        // carefree.position
        if (CarefreeAide.isNotBlank(properties.getPosition())) {
            String[] sections = CarefreeAide.split(properties.getPosition(), ",");
            Arrays.stream(sections).filter(CarefreeAide::isNotBlank).map(CarefreeAide::trim).forEach(section -> {
                CarefreeProperties.Position position = new CarefreeProperties.Position();

                // 截取路径部分（如果有的话）
                int lastSeparatorIndex = section.lastIndexOf("/");
                if (lastSeparatorIndex == 0) {
                    position.setPath("/");
                    section = section.substring(1);
                } else if (lastSeparatorIndex > 0) {
                    position.setPath(section.substring(0, lastSeparatorIndex));
                    section = section.substring(lastSeparatorIndex + 1);
                }

                // 截取扩展名部分（如果有的话）
                int lastPointIndex = section.lastIndexOf(".");
                if (lastPointIndex > 0) {
                    position.setName(section.substring(0, lastPointIndex));
                    position.setExtension(section.substring(lastPointIndex));
                } else {
                    position.setName(section);
                }

                allPositions.add(position);
            });
        }

        // carefree.positions
        if (CarefreeAide.isNotEmpty(properties.getPositions())) {
            allPositions.addAll(properties.getPositions());
        }

        return allPositions;
    }

    /**
     * 根据 {@link CarefreeProperties.Position} 集合创建 Resource 集合，
     * 结构为 < 配置文件Key, Resource列表 >，表示相同 key 的配置有多个文件，
     * 相同 key 的 Resource 列表需保证优先级从低到高的顺序，高优先级文件将覆盖低优先级中的同名属性。
     */
    private static Map<String, List<Resource>> resolveConfigResources(List<CarefreeProperties.Position> positions) {
        Map<String, List<Resource>> resourceMap = new HashMap<>();

        if (CarefreeAide.isNotEmpty(positions)) {
            positions.forEach(position -> {
                // 若未限定配置文件所在根目录，则遍历 DEFAULT_PATHS 中的默认根目录进行查找
                List<String> paths = new LinkedList<>();
                if (CarefreeAide.isNotBlank(position.getPath())) {
                    paths.add(position.getPath());
                } else {
                    paths.addAll(Arrays.asList(DEFAULT_PATHS));
                }

                // 若未限定配置文件扩展名，则遍历 DEFAULT_TYPES 中的默认扩展名进行查找
                List<String> extensions = new LinkedList<>();
                if (CarefreeAide.isNotBlank(position.getExtension())) {
                    extensions.add(position.getExtension());
                } else {
                    extensions.addAll(Arrays.asList(DEFAULT_TYPES));
                }

                List<Resource> resources = resolveOneKeyResources(position.getName(), paths, extensions);
                if (CarefreeAide.isNotEmpty(resources)) {
                    String key = CarefreeAide.defaultIfBlank(position.getKey(), position.getName());
                    resourceMap.put(key, resources);
                }
            });
        }

        return resourceMap;
    }

    /**
     * 组装配置文件完整路径并返回路径资源描述（只返回文件真实存在的），
     * root + name + extension
     */
    private static List<Resource> resolveOneKeyResources(String name, List<String> paths, List<String> extensions) {
        List<Resource> resources = new LinkedList<>();

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        paths.forEach(path -> extensions.forEach(extension -> {
            StringBuilder pathBuilder = new StringBuilder();

            pathBuilder.append(path);
            if (!path.endsWith("/")) {
                pathBuilder.append("/");
            }

            pathBuilder.append(name);

            if (!extension.startsWith(".")) {
                pathBuilder.append(".");
            }
            pathBuilder.append(extension);

            Resource resource = resourceLoader.getResource(pathBuilder.toString());
            if (resource.exists()) {
                resources.add(resource);
            }
        }));

        return resources;
    }
}
