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
 * TODO-Kweny NamingConvention
 *
 * @author Kweny
 * @since 0.0.1
 */
public class NamingConvention {

    public static final LowerCamel LOWER_CAMEL = new LowerCamel();
    public static final UpperCamel UPPER_CAMEL = new UpperCamel();
    public static final Snake SNAKE = new Snake();
    public static final Kebab KEBAB = new Kebab();
    public static final Separator SEPARATOR = new Separator();

    // TODO-Kweny replace with surito beginning
    private static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

    public static int getLength(String input) {
        return input == null ? 0 : input.length();
    }
    // TODO-Kweny replace with surito ending

    private static final int MODE_KEEP = 0;
    private static final int MODE_LOWER = 1;
    private static final int MODE_UPPER = 2;

    private static final char SEPARATOR_SNAKE = '_';
    private static final char SEPARATOR_KEBAB = '-';

    public static class LowerCamel {
        public String fromSnake(String input) {
            return separator2Camel(input, SEPARATOR_SNAKE, MODE_LOWER);
        }

        public String fromKebab(String input) {
            return separator2Camel(input, SEPARATOR_KEBAB, MODE_LOWER);
        }

        public String fromSeparator(String input, char separator) {
            return separator2Camel(input, separator, MODE_LOWER);
        }
    }

    public static class UpperCamel {
        public String fromSnake(String input) {
            return separator2Camel(input, SEPARATOR_SNAKE, MODE_UPPER);
        }

        public String fromKebab(String input) {
            return separator2Camel(input, SEPARATOR_KEBAB, MODE_UPPER);
        }

        public String fromSeparator(String input, char separator) {
            return separator2Camel(input, separator, MODE_UPPER);
        }
    }

    public static class Snake {
        /**
         * <p>
         *     <ul>
         *         <li>MAC to mac</li>
         *         <li>remoteURL to remote_url</li>
         *         <li>remoteURLID to remote_urlid</li>
         *         <li>randomSQLText to random_sql_text</li>
         *         <li>ONETestString to one_test_string</li>
         *         <li>ONEtestString to on_etest_string</li>
         *         <li>oneTestSTRING to one_test_string</li>
         *     </ul>
         * </p>
         */
        public String fromCamel(String input) {
            return camel2Separator(input, SEPARATOR_SNAKE, MODE_LOWER);
        }

        /**
         * <p>
         *     <ul>
         *         <li>MAC to MAC</li>
         *         <li>remoteURL to REMOTE_URL</li>
         *         <li>remoteURLID to REMOTE_URLID</li>
         *         <li>randomSQLText to RANDOM_SQL_TEXT</li>
         *         <li>ONETestString to ONE_TEST_STRING</li>
         *         <li>ONEtestString to ON_ETEST_STRING</li>
         *         <li>oneTestSTRING to ONE_TEST_STRING</li>
         *     </ul>
         * </p>
         */
        public String fromCamelInUpperCase(String input) {
            return camel2Separator(input, SEPARATOR_SNAKE, MODE_UPPER);
        }

        /**
         * <p>
         *     <ul>
         *         <li>MAC to MAC</li>
         *         <li>remoteURL to remote_URL</li>
         *         <li>remoteURLID to remote_URLID</li>
         *         <li>randomSQLText to random_SQL_Text</li>
         *         <li>ONETestString to ONE_Test_String</li>
         *         <li>ONEtestString to ON_Etest_String</li>
         *         <li>oneTestSTRING to one_Test_STRING</li>
         *     </ul>
         * </p>
         */
        public String fromCamelKeepCase(String input) {
            return camel2Separator(input, SEPARATOR_SNAKE, MODE_KEEP);
        }

        public String fromKebab(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, SEPARATOR_SNAKE).toLowerCase();
        }

        public String fromKebabInUpperCase(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, SEPARATOR_SNAKE).toUpperCase();
        }

        public String fromKebabKeepCase(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, SEPARATOR_SNAKE);
        }

        public String fromSeparator(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_SNAKE).toLowerCase();
        }

        public String fromSeparatorInUpperCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_SNAKE).toUpperCase();
        }

        public String fromSeparatorKeepCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_SNAKE);
        }
    }

    public static class Kebab {
        public String fromCamel(String input) {
            return camel2Separator(input, SEPARATOR_KEBAB, MODE_LOWER);
        }

        public String fromCamelInUpperCase(String input) {
            return camel2Separator(input, SEPARATOR_KEBAB, MODE_UPPER);
        }

        public String fromCamelKeepCase(String input) {
            return camel2Separator(input, SEPARATOR_KEBAB, MODE_KEEP);
        }

        public String fromSnake(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, SEPARATOR_KEBAB).toLowerCase();
        }

        public String fromSnakeInUpperCase(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, SEPARATOR_KEBAB).toUpperCase();
        }

        public String fromSnakeKeepCase(String input) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, SEPARATOR_KEBAB);
        }

        public String fromSeparator(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_KEBAB).toLowerCase();
        }

        public String fromSeparatorInUpperCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_KEBAB).toUpperCase();
        }

        public String fromSeparatorKeepCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(separator, SEPARATOR_KEBAB);
        }
    }

    public static class Separator {
        public String fromCamel(String input, char separator) {
            return camel2Separator(input, separator, MODE_LOWER);
        }

        public String fromCamelInUpperCase(String input, char separator) {
            return camel2Separator(input, separator, MODE_UPPER);
        }

        public String fromCamelKeepCase(String input, char separator) {
            return camel2Separator(input, separator, MODE_KEEP);
        }

        public String fromSnake(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, separator).toLowerCase();
        }

        public String fromSnakeInUpperCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, separator).toUpperCase();
        }

        public String fromSnakeKeepCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_SNAKE, separator);
        }

        public String fromKebab(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, separator).toLowerCase();
        }

        public String fromKebabInUpperCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, separator).toUpperCase();
        }

        public String fromKebabKeepCase(String input, char separator) {
            return isEmpty(input) ? input : input.replace(SEPARATOR_KEBAB, separator);
        }

        public String fromSeparator(String input, char fromSeparator, char separator) {
            return isEmpty(input) ? input : input.replace(fromSeparator, separator).toLowerCase();
        }

        public String fromSeparatorInUpperCase(String input, char fromSeparator, char separator) {
            return isEmpty(input) ? input : input.replace(fromSeparator, separator).toUpperCase();
        }

        public String fromSeparatorKeepCase(String input, char fromSeparator, char separator) {
            return isEmpty(input) ? input : input.replace(fromSeparator, separator);
        }
    }

    // ----- commons -----
    private static String camel2Separator(String input, char separator, int mode) {
        int length = getLength(input);
        if (length == 0) {
            return input;
        }

        final StringBuilder builder = new StringBuilder(length * 2);
        int upperCount = 0;
        for (int i = 0; i < length; i++) {
            char chr = input.charAt(i);
            char lowerChr = Character.toLowerCase(chr);

            if (lowerChr == chr) { // 意味着 chr 原本就是小写
                if (upperCount > 1) {
                    builder.insert(builder.length() - 1, separator);
                }
                upperCount = 0;
            } else { // 意味着 chr 原本是大写
                if (upperCount == 0 && i > 0) {
                    builder.append(separator);
                }
                ++ upperCount;
            }
            builder.append(mode == MODE_LOWER ? lowerChr : mode == MODE_UPPER ? Character.toUpperCase(chr) : chr);
        }

        return builder.toString();
    }

    public static String separator2Camel(String input, char separator, int mode) {
        int length = getLength(input);
        if (length == 0) {
            return input;
        }

        String separatorStr = String.valueOf(separator);
        while (input.startsWith(separatorStr)) {
            input = input.substring(1);
        }
        while (input.endsWith(separatorStr)) {
            input = input.substring(0, input.length() - 1);
        }

        final StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char chr = input.charAt(i);
            if (chr == separator) {
                builder.append(Character.toUpperCase(input.charAt(++i)));
                continue;
            }
            builder.append(Character.toLowerCase(chr));
        }

        if (mode == MODE_LOWER) {
            builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
        }
        if (mode == MODE_UPPER) {
            builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(NamingConvention.SNAKE.fromCamel("MAC"));
        System.out.println(NamingConvention.SNAKE.fromCamel("remoteURL"));
        System.out.println(NamingConvention.SNAKE.fromCamel("remoteURLID"));
        System.out.println(NamingConvention.SNAKE.fromCamel("randomSQLText"));
        System.out.println(NamingConvention.SNAKE.fromCamel("ONETestString"));
        System.out.println(NamingConvention.SNAKE.fromCamel("ONEtestString"));
        System.out.println(NamingConvention.SNAKE.fromCamel("oneTestSTRING"));

        System.out.println();

        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("MAC"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("remoteURL"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("remoteURLID"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("randomSQLText"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("ONETestString"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("ONEtestString"));
        System.out.println(NamingConvention.SNAKE.fromCamelInUpperCase("oneTestSTRING"));

        System.out.println();

        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("MAC"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("remoteURL"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("remoteURLID"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("randomSQLText"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("ONETestString"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("ONEtestString"));
        System.out.println(NamingConvention.SNAKE.fromCamelKeepCase("oneTestSTRING"));
    }
}
