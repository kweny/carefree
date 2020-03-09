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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <h3>参数化消息格式化工具的内部辅助处理类</h3>
 *
 * @author Kweny
 * @since 0.0.1
 */
class _InternalParameterizeMessageFormatAssist {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String RECURSION_PREFIX = "[...";
    private static final String RECURSION_SUFFIX = "...]";
    private static final String BRACKET_LEFT = "[";
    private static final String BRACKET_RIGHT = "]";
    private static final String BRACE_LEFT = "{";
    private static final String BRACE_RIGHT = "}";
    private static final String COMMA_SPACE = ", ";
    private static final String EQUAL_SIGN = "=";
    private static final String ERROR_PREFIX = "[!!!";
    private static final String ERROR_SEPARATOR = "=>";
    private static final String ERROR_MSG_SEPARATOR = ":";
    private static final String ERROR_SUFFIX = "!!!]";
    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final char ESCAPE_CHAR = '\\';

    static String formatMessage(final String pattern, final String[] arguments) {
        return formatStringArgs(pattern, arguments);
    }

    static String formatStringArgs(final String pattern, final String[] arguments) {
        int length;
        if (pattern == null || (length = pattern.length()) == 0 || arguments == null || arguments.length == 0) {
            return pattern;
        }
        return formatStringArgs0(pattern, length, arguments);
    }

    private static String formatStringArgs0(final String pattern, final int length, final String[] arguments) {
        final char[] result = new char[length + sumStringLengths(arguments)];
        int pos = 0;
        int escapeCounter = 0;
        int currentArgument = 0;
        int i = 0;
        for (; i < length - 1; i++) {
            final char curChar = pattern.charAt(i);
            if (curChar == ESCAPE_CHAR) {
                escapeCounter ++;
            } else {
                if (isDelimPair(curChar, pattern.charAt(i + 1))) {
                    i ++;

                    pos = writeEscapedEscapeChars(escapeCounter, result, pos);

                    if (isOdd(escapeCounter)) {
                        pos = writeDelimPair(result, pos);
                    } else {
                        pos = writeArgOrDelimPair(arguments, currentArgument, result, pos);
                        currentArgument ++;
                    }
                } else {
                    pos = handleLiteralChar(result, pos, escapeCounter, curChar);
                }
                escapeCounter = 0;
            }
        }
        pos = handleRemainingCharIfAny(pattern, length, result, pos, escapeCounter, i);
        return new String(result, 0, pos);
    }

    private static int sumStringLengths(final String[] arguments) {
        int result = 0;
        for (String argument : arguments) {
            result += String.valueOf(argument).length();
        }
        return result;
    }

    private static int handleLiteralChar(final char[] result, int pos, final int escapeCounter, final char curChar) {
        pos = writeUnescapedEscapeChars(escapeCounter, result, pos);
        result[pos++] = curChar;
        return pos;
    }

    private static int handleRemainingCharIfAny(final String messagePattern, final int length, final char[] result, int pos, int escapeCounter, int i) {
        if (i == length - 1) {
            final char curChar = messagePattern.charAt(i);
            pos = handleLastChar(result, pos, escapeCounter, curChar);
        }
        return pos;
    }

    private static int handleLastChar(final char[] result, int pos, final int escapeCounter, final char curChar) {
        if (curChar == ESCAPE_CHAR) {
            pos = writeUnescapedEscapeChars(escapeCounter + 1, result, pos);
        } else {
            pos = handleLiteralChar(result, pos, escapeCounter, curChar);
        }
        return pos;
    }

    private static boolean isDelimPair(final char c1, final char c2) {
        return c1 == DELIM_START && c2 == DELIM_STOP;
    }

    private static int writeDelimPair(final char[] result, int pos) {
        result[pos++] = DELIM_START;
        result[pos++] = DELIM_STOP;
        return pos;
    }

    private static int writeArgOrDelimPair(final String[] arguments, final int currentArgument, final char[] result, int pos) {
        if (currentArgument < arguments.length) {
            pos = writeArgAt0(arguments, currentArgument, result, pos);
        } else {
            pos = writeDelimPair(result, pos);
        }
        return pos;
    }

    private static int writeArgAt0(final String[] arguments, final int currentArgument, final char[] result, final int pos) {
        final String arg = String.valueOf(arguments[currentArgument]);
        int argLen = arg.length();
        arg.getChars(0, argLen, result, pos);
        return pos + argLen;
    }

    private static int writeEscapedEscapeChars(final int escapeCounter, final char[] result, final int pos) {
        final int escapedEscapes = escapeCounter >> 1;
        return writeUnescapedEscapeChars(escapedEscapes, result, pos);
    }

    private static int writeUnescapedEscapeChars(int escapeCounter, char[] result, int pos) {
        while (escapeCounter > 0) {
            result[pos++] = ESCAPE_CHAR;
            escapeCounter --;
        }
        return pos;
    }

    private static boolean isOdd(final int number) {
        return (number & 1) == 1;
    }

    static String[] objectsToStrings(final Object[] args) {
        if (args == null) {
            return null;
        }

//        final int placeholderNumber = countPlaceholders(pattern);
        int argNumber = args.length;
        String[] stringArgs = new String[argNumber];
        for (int i = 0; i < argNumber; i++) {
            stringArgs[i] = deepToString(args[i]);
        }
        return stringArgs;
    }

/*  占位符数量算出来没卵用，暂时注释并保留，有用再开 by Kweny
    static int countPlaceholders(final String pattern) {
        if (pattern == null) {
            return 0;
        }

        final int delim = pattern.indexOf(DELIM_START);
        if (delim == -1) {
            return 0;
        }

        int count = 0;
        boolean isEscaped = false;
        for (int i = 0; i < pattern.length(); i++) {
            final char c = pattern.charAt(i);
            if (c == ESCAPE_CHAR) {
                isEscaped = !isEscaped;
            } else if (c == DELIM_START) {
                if (!isEscaped && i < pattern.length() - 1 && pattern.charAt(i + 1) == DELIM_STOP) {
                    count ++;
                    i ++;
                }
                isEscaped = false;
            } else {
                isEscaped = false;
            }
        }

        return count;
    }
*/

    private static String deepToString(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        final StringBuilder builder = new StringBuilder();
        final Set<String> dejaVu = new HashSet<>();
        recursiveDeepToString(obj, builder, dejaVu);
        return builder.toString();
    }

    private static void recursiveDeepToString(final Object obj, final StringBuilder builder, final Set<String> dejaVu) {
        // 如果是 null 或者 String 时，直接转为 String 追加
        if (obj == null || obj instanceof String) {
            builder.append(obj);
            return;
        }

        // 如果是 Date 类型日期，格式化后追加
        if (obj instanceof Date) {
            final Date date = (Date) obj;
            final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            builder.append(format.format(date));
            return;
        }

        // 如果是可递归循环的集合（Array、Map、Collection），递归追加
        if (obj.getClass().isArray()) {
            appendArray(obj, builder, dejaVu, obj.getClass());
            return;
        }
        if (obj instanceof Map) {
            appendMap(obj, builder, dejaVu);
            return;
        }
        if (obj instanceof Collection) {
            appendCollection(obj, builder, dejaVu);
            return;
        }

        // 其它情况，尝试用 Object.toString 方法转字符串并追加，若失败则添加错误信息并追加
        try {
            builder.append(obj.toString());
        } catch (Throwable t) {
            builder.append(ERROR_PREFIX);
            builder.append(identityString(obj));
            builder.append(ERROR_SEPARATOR);
            final String msg = t.getMessage();
            final String className = t.getClass().getName();
            builder.append(className);
            if (!className.equals(msg)) {
                builder.append(ERROR_MSG_SEPARATOR);
                builder.append(msg);
            }
            builder.append(ERROR_SUFFIX);
        }
    }

    private static void appendArray(final Object obj, final StringBuilder builder, final Set<String> dejaVu, final Class<?> objClass) {
        if (objClass == byte[].class) {
            builder.append(Arrays.toString((byte[]) obj));
        } else if (objClass == short[].class) {
            builder.append(Arrays.toString((short[]) obj));
        } else if (objClass == int[].class) {
            builder.append(Arrays.toString((int[]) obj));
        } else if (objClass == long[].class) {
            builder.append(Arrays.toString((long[]) obj));
        } else if (objClass == float[].class) {
            builder.append(Arrays.toString((float[]) obj));
        } else if (objClass == double[].class) {
            builder.append(Arrays.toString((double[]) obj));
        } else if (objClass == boolean[].class) {
            builder.append(Arrays.toString((boolean[]) obj));
        } else if (objClass == char[].class) {
            builder.append(Arrays.toString((char[]) obj));
        } else {
            final String id = identityString(obj);
            if (dejaVu.contains(id)) {
                builder.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
            } else {
                dejaVu.add(id);
                final Object[] objArray = (Object[]) obj;
                builder.append(BRACKET_LEFT);
                for (int i = 0; i < objArray.length; i++) {
                    final Object current = objArray[i];
                    if (i > 0) {
                        builder.append(COMMA_SPACE);
                    }
                    recursiveDeepToString(current, builder, new HashSet<>(dejaVu));
                }
                builder.append(BRACKET_RIGHT);
            }
        }
    }

    private static void appendMap(final Object obj, final StringBuilder builder, final Set<String> dejaVu) {
        final String id = identityString(obj);
        if (dejaVu.contains(id)) {
            builder.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
        } else {
            dejaVu.add(id);
            final Map<?, ?> objMap = (Map<?, ?>) obj;
            builder.append(BRACE_LEFT);
            boolean first = true;
            for (final Map.Entry<?, ?> current : objMap.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    builder.append(COMMA_SPACE);
                }
                final Object key = current.getKey();
                final Object value = current.getValue();
                recursiveDeepToString(key, builder, new HashSet<>(dejaVu));
                builder.append(EQUAL_SIGN);
                recursiveDeepToString(value, builder, new HashSet<>(dejaVu));
            }
            builder.append(BRACE_RIGHT);
        }
    }

    private static void appendCollection(final Object obj, final StringBuilder builder, final Set<String> dejaVu) {
        final String id = identityString(obj);
        if (dejaVu.contains(id)) {
            builder.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
        } else {
            dejaVu.add(id);
            final Collection<?> objCollection = (Collection<?>) obj;
            builder.append(BRACKET_LEFT);
            boolean first = true;
            for (final Object current : objCollection) {
                if (first) {
                    first = false;
                } else {
                    builder.append(COMMA_SPACE);
                }
                recursiveDeepToString(current, builder, new HashSet<>(dejaVu));
            }
            builder.append(BRACKET_RIGHT);
        }
    }

    private static String identityString(final Object obj) {
        return obj == null ? null : obj.getClass().getName() + "@" + Integer.toHexString(Objects.hashCode(obj));
    }
}