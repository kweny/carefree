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
 * 命名规范转换器
 * <p>
 * CamelCase（驼峰）、snake_case（蛇形）、spinal-case（脊柱）互相转换
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class NamingConverter {

    /**
     * 将骆驼命名法转成蛇形返回。
     * 注意：对于连续大写的情况做如下处理——
     *     <ul>
     *         <li>MAC - mac</li>
     *         <li>remoteURL - remote_url</li>
     *         <li>remoteURLID - remote_urlid</li>
     *         <li>randomSQLText - random_sql_text</li>
     *         <li>ONETestString - one_test_string</li>
     *         <li>ONEtestString - on_etest_string</li>
     *         <li>oneTestSTRING - one_test_string</li>
     *     </ul>
     *
     * @param name 驼峰标识
     * @return 蛇形标识
     */
    public static String camel2Snake(String name) {
        return camel2Separated(name, '_');
    }

    /**
     * 将骆驼命名法转成脊柱返回。
     * 注意：对于连续大写的情况做如下处理——
     *     <ul>
     *         <li>MAC - mac</li>
     *         <li>remoteURL - remote-url</li>
     *         <li>remoteURLID - remote-urlid</li>
     *         <li>randomSQLText - random-sql-text</li>
     *         <li>ONETestString - one-test-string</li>
     *         <li>ONEtestString - on-etest-string</li>
     *         <li>oneTestSTRING - one-test-string</li>
     *     </ul>
     *
     * @param name 驼峰标识
     * @return 脊柱标识
     */
    public static String camel2spinal(String name) {
        return camel2Separated(name, '-');
    }

    /**
     * 将骆驼命名法转成使用指定符号分隔的标识返回。
     * 注意：对于连续大写的情况做如下处理（假设按短横线-分隔）——
     *     <ul>
     *         <li>MAC - mac</li>
     *         <li>remoteURL - remote-url</li>
     *         <li>remoteURLID - remote-urlid</li>
     *         <li>randomSQLText - random-sql-text</li>
     *         <li>ONETestString - one-test-string</li>
     *         <li>ONEtestString - on-etest-string</li>
     *         <li>oneTestSTRING - one-test-string</li>
     *     </ul>
     *
     * @param name 驼峰标识
     * @param separator 分隔符
     * @return 分隔符标识
     */
    public static String camel2Separated(String name, char separator) {
        if (TempCarefreeAide.isBlank(name)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        boolean startUpper = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    if (!startUpper) {
                        startUpper = true;
                        if (i > 0) {
                            builder.append(separator);
                        }
                    }
                } else {
                    if (startUpper) {
                        if (builder.length() >= 2 && builder.charAt(builder.length() - 2) != separator) {
                            builder.insert(builder.length() - 1, separator);
                        }
                        startUpper = false;
                    }
                }
            }
            builder.append(c);
        }

        return builder.toString().toLowerCase();
    }

    /**
     * 将蛇形命名法转换成骆驼命名法返回
     *
     * @param name 蛇形标识
     * @return 驼峰标识
     */
    public static String snake2Camel(String name) {
        return separated2Camel(name, '_');
    }

    /**
     * 将脊柱命名法转换成骆驼命名法返回
     *
     * @param name 脊柱标识
     * @return 驼峰标识
     */
    public static String spinal2Camel(String name) {
        return separated2Camel(name, '-');
    }

    /**
     * 将使用指定分隔符分隔的标识命名法转换成骆驼命名法返回
     *
     * @param name 指定分隔符分隔的标识
     * @param separator 分隔符
     * @return 驼峰标识
     */
    public static String separated2Camel(String name, char separator) {
        if (TempCarefreeAide.isBlank(name)) {
            return null;
        }
        name = TempCarefreeAide.lowerCase(name);
        // 去掉开头的
        while (TempCarefreeAide.startsWith(name, String.valueOf(separator))) {
            name = TempCarefreeAide.substring(name, 1);
        }
        // 去掉最后的
        while (TempCarefreeAide.endsWith(name, String.valueOf(separator))) {
            name = TempCarefreeAide.substring(name, 0, name.length() - 1);
        }

        // 去掉中间的，后面首字母大写
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == separator) {
                i++;
                sb.append(Character.toUpperCase(name.charAt(i)));
                continue;
            }
            sb.append(c);
        }

        return sb.toString();
    }
}