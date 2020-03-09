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
package org.apenk.carefree.cloud;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.apenk.carefree.CarefreeRegistry;
import org.apenk.carefree.aide.CollectionAide;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Kweny
 * @since 0.0.1
 */
class CarefreeCloudConfigNacosLoader extends CarefreeCloudConfigLoader {
    private NacosConfigManager nacosConfigManager;

    CarefreeCloudConfigNacosLoader(CarefreeCloudProperties carefreeCloudProperties, NacosConfigManager nacosConfigManager) {
        super(carefreeCloudProperties);
        this.nacosConfigManager = nacosConfigManager;
    }

    @Override
    CarefreeRegistry load() {
        NacosConfigProperties nacosConfigProperties;
        if (nacosConfigManager == null || (nacosConfigProperties = nacosConfigManager.getNacosConfigProperties()) == null) {
            logger.warn("no properties of nacos config found, can't load config from nacos.");
            return CarefreeRegistry.EMPTY;
        }

        // 获取 nacos 服务连接实例，用于请求配置文件
        ConfigService configService = nacosConfigManager.getConfigService();
        if (configService == null) {
            logger.warn("no instance of nacos config service found, can't load config from nacos");
            return CarefreeRegistry.EMPTY;
        }

        List<CarefreeCloudProperties.Nacos.Position> positions = resolveConfigPositions();
        if (CollectionAide.isEmpty(positions)) {
            logger.warn("no config of nacos found, cannot load config from nacos");
            return CarefreeRegistry.EMPTY;
        }

        CarefreeRegistry carefreeRegistry = new CarefreeRegistry();

        long timeout = nacosConfigProperties.getTimeout();
        for (CarefreeCloudProperties.Nacos.Position position : positions) {
            String configData = null;
            try {
                configData = configService.getConfig(position.getDataId(), position.getGroup(), timeout);
                if (StringUtils.isNotBlank(configData)) {
                    carefreeRegistry.register(position.getKey(), ConfigFactory.parseString(configData));
                }
            } catch (NacosException e) {
                logger.error(e,"get data from Nacos error, dataId:{}", position.getDataId());
            } catch (Exception e) {
                logger.error(e,"parse data from Nacos error, dataId:{}, data:{}", position.getDataId(), configData);
            }
        }

        return carefreeRegistry;
    }

    private List<CarefreeCloudProperties.Nacos.Position> resolveConfigPositions() {
        List<CarefreeCloudProperties.Nacos.Position> allPositions = new LinkedList<>();

        CarefreeCloudProperties.Nacos nacos = carefreeCloudProperties.getNacos();
        if (nacos == null) {
            return allPositions;
        }

        // carefree.cloud.nacos.position
        if (StringUtils.isNotBlank(nacos.getPosition())) {
            String[] sections = StringUtils.split(nacos.getPosition(), ",");
            Arrays.stream(sections).filter(StringUtils::isNotBlank).forEach(section -> {
                CarefreeCloudProperties.Nacos.Position position = new CarefreeCloudProperties.Nacos.Position();

                int separatorIndex = section.indexOf("/");
                if (separatorIndex < 0) {
                    position.setGroup(CarefreeCloudProperties.Nacos.DEFAULT_GROUP);
                    position.setDataId(section);
                } else if (separatorIndex == 0) {
                    position.setGroup(CarefreeCloudProperties.Nacos.DEFAULT_GROUP);
                    position.setDataId(section.substring(1));
                } else {
                    position.setGroup(section.substring(0, separatorIndex));
                    position.setDataId(section.substring(separatorIndex + 1));
                }

                String key = position.getDataId();
                int lastPointIndex = key.lastIndexOf(".");
                if (lastPointIndex > 0) {
                    key = key.substring(0, lastPointIndex);
                }
                position.setKey(key);

                allPositions.add(position);
            });
        }

        // carefree.cloud.nacos.positions
        if (CollectionAide.isNotEmpty(nacos.getPositions())) {
            for (CarefreeCloudProperties.Nacos.Position position : nacos.getPositions()) {
                if (StringUtils.isBlank(position.getKey())) {
                    String key = position.getDataId();
                    int lastPointIndex = key.lastIndexOf(".");
                    if (lastPointIndex > 0) {
                        key = key.substring(0, lastPointIndex);
                    }
                    position.setKey(key);
                }
                allPositions.add(position);
            }
        }

        return allPositions;
    }
}