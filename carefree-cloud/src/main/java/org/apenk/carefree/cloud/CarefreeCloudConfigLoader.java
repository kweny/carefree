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

import org.apenk.carefree.CarefreeRegistry;
import org.apenk.carefree.helper.CarefreeLogger;

/**
 * @author Kweny
 * @since 0.0.1
 */
abstract class CarefreeCloudConfigLoader {

    public static CarefreeLogger logger = CarefreeLogger.getLogger("carefree.cloud");

    protected CarefreeCloudProperties carefreeCloudProperties;

    protected CarefreeCloudConfigLoader(CarefreeCloudProperties carefreeCloudProperties) {
        this.carefreeCloudProperties = carefreeCloudProperties;
    }

    abstract CarefreeRegistry load();

    static class _NoLoader extends CarefreeCloudConfigLoader {

        _NoLoader(CarefreeCloudProperties carefreeCloudProperties) {
            super(carefreeCloudProperties);
        }

        @Override
        CarefreeRegistry load() {
            return CarefreeRegistry.EMPTY;
        }
    }
}