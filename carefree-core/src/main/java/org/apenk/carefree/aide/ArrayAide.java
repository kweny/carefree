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

import java.lang.reflect.Array;

/**
 * @author Kweny
 * @since 0.0.1
 */
public class ArrayAide {
    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static <T> boolean isEmpty(final T[] array) {
        return getLength(array) == 0;
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }
}
