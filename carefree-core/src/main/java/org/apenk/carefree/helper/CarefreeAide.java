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

/**
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeAide {

    public static final String FORCE_DEFAULT = "default";
    public static final Object DEFAULT_Declaration = new CarefreeClassDeclaration();
    public static final Object DEFAULT_String = String.valueOf(527558829);
    public static final Object DEFAULT_Character = new Character('C');
    public static final Object DEFAULT_Byte = new Byte((byte) 67);
    public static final Object DEFAULT_Short = new Short((short) 829);
    public static final Object DEFAULT_Integer = new Integer(1);
    public static final Object DEFAULT_Long = new Long(527558829);
    public static final Object DEFAULT_Float = new Float(527558829);
    public static final Object DEFAULT_Double = new Double(527558829);
    public static final Object DEFAULT_Boolean = new Boolean(false);
    public static final Object DEFAULT_Object = new Object();
    public static final Object DEFAULT_Array = new Object[0];

    public static boolean isForceDefault(String value) {
        return TempCarefreeAide.equalsIgnoreCase(FORCE_DEFAULT, value);
    }

    public static boolean isDefaultValue(CarefreeClassDeclaration value) {
        return value == DEFAULT_Declaration;
    }

    public static boolean isDefaultValue(String value) {
        return value == DEFAULT_String;
    }

    public static boolean isDefaultValue(Character value) {
        return value == DEFAULT_Character;
    }

    public static boolean isDefaultValue(Byte value) {
        return value == DEFAULT_Byte;
    }

    public static boolean isDefaultValue(Short value) {
        return value == DEFAULT_Short;
    }

    public static boolean isDefaultValue(Integer value) {
        return value == DEFAULT_Integer;
    }

    public static boolean isDefaultValue(Long value) {
        return value == DEFAULT_Long;
    }

    public static boolean isDefaultValue(Float value) {
        return value == DEFAULT_Float;
    }

    public static boolean isDefaultValue(Double value) {
        return value == DEFAULT_Double;
    }

    public static boolean isDefaultValue(Boolean value) {
        return value == DEFAULT_Boolean;
    }

    public static boolean isDefaultValue(Object value) {
        return value == DEFAULT_Object;
    }

    public static boolean isDefaultValue(Object[] value) {
        return value == DEFAULT_Array;
    }
}
