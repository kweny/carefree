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

import java.util.List;
import java.util.regex.Pattern;

/**
 * 用于描述 carefree 配置文件位置。
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeProperties {
    public static final String PREFIX = "carefree";
    public static final Pattern ONE_POSITION_NAME_PATTERN = Pattern.compile("carefree.positions\\[\\d]");

    private String position;
    private List<Position> positions;

    @Override
    public String toString() {
        return "CarefreeProperties{" +
                "position='" + position + '\'' +
                ", positions=" + positions +
                '}';
    }

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
        private String name;
        private String extension;
        private String key;
        private String path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
}
