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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * logger wrapper
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeLogger {
    private static final ConcurrentHashMap<Object, CarefreeLogger> LOGGER_CACHE = new ConcurrentHashMap<>();

    public static CarefreeLogger getLogger(final String name) {
        return LOGGER_CACHE.computeIfAbsent(name, key -> new CarefreeLogger(name));
    }

    private final Logger logger;
    private final String prefix;

    private CarefreeLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
        this.prefix = "[" + name + "] ";
    }

    public void warn(String msg, Object... args) {
        logger.warn(prefix.concat(msg), args);
    }

    public void error(Throwable t, String msg, Object... args) {
        if (logger.isErrorEnabled()) {
            String message = ParameterizeMessageFormatter.format(prefix.concat(msg), args);
            logger.error(message, t);
        }
    }
}
