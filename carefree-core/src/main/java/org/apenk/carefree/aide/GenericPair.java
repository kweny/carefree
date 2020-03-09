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

import java.io.Serializable;
import java.util.Objects;

/**
 * 泛型数据对
 *
 * @author Kweny
 * @since 2018-12-02 0:22
 */
public class GenericPair<F, S> implements Serializable {
    private static final long serialVersionUID = -7581687404620307675L;

    public static <F, S> GenericPair<F, S> newInstance(F first, S second) {
        return new GenericPair<>(first, second);
    }

    public static <F, S> GenericPair<F, S> newInstance() {
        return new GenericPair<>();
    }

    private F first;
    private S second;

    public GenericPair() {}

    public GenericPair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public GenericPair<F, S> pair(F first, S second) {
        this.first = first;
        this.second = second;
        return this;
    }

    public GenericPair<F, S> first(F first) {
        this.first = first;
        return this;
    }

    public GenericPair<F, S> second(S second) {
        this.second = second;
        return this;
    }

    public F first() {
        return this.first;
    }

    public S second() {
        return this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GenericPair<?, ?> that = (GenericPair<?, ?>) o;

        return Objects.equals(first, that.first) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}