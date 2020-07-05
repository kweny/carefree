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

package org.apenk.carefree.remote;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * @author Kweny
 * @since 0.0.1
 */
@EnableConfigurationProperties(CarefreeRemoteProperties.class)
@ConfigurationProperties(CarefreeRemoteProperties.PREFIX)
public class CarefreeRemoteProperties {
    public static final String PREFIX = "carefree.cloud";

    private String serverType;
    private Nacos nacos;
    private Custom custom;

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Nacos getNacos() {
        return nacos;
    }

    public void setNacos(Nacos nacos) {
        this.nacos = nacos;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }

    public static class Nacos {
        // TODO-Kweny 除了 custom、http、git 等通用方式，将 nacos、spring config 等需要依赖第三方配置中心的种类独立出去
        public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

        private String position;
        private List<Position> positions;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public List<Position> getPositions() {
            return positions;
        }

        public void setPositions(List<Position> positions) {
            this.positions = positions;
        }

        public static class Position {
            private String key;
            private String dataId;
            private String group = DEFAULT_GROUP;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getDataId() {
                return dataId;
            }

            public void setDataId(String dataId) {
                this.dataId = dataId;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

        }
    }

    public static class Http {
        // TODO-Kweny CarefreeCloudProperties.Http
    }

    public static class Socket {
        // TODO-Kweny CarefreeCloudProperties.Socket
    }

    public static class Git {
        // TODO-Kweny CarefreeCloudProperties.Git
    }

    public static class Custom {
        // TODO-Kweny CarefreeCloudProperties.Custom
    }
}