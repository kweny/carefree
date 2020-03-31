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
 * <h3>参数化消息格式化工具</h3>
 *
 * <p>
 *     消息模板中的形参以一对空花括号 {@code {}} 表示（占位符），多余的占位符将被保留，多余的实参将被忽略。
 * </p>
 *
 * @author Kweny
 * @since 0.0.1
 */
public class ParameterizeMessageFormatter {

    private static final String INDENT = "    "; // 缩进，4个空格

    private final String pattern;
    private final String[] arguments;
    private transient Object[] objectArguments;
    private transient int indentLevel;
    private transient String formattedMessage;

    public ParameterizeMessageFormatter(final String pattern, final Object... args) {
        this.pattern = pattern;
        this.objectArguments = args;
        this.arguments = _InternalParameterizeMessageFormatAssist.objectsToStrings(args);
    }

    public ParameterizeMessageFormatter(final int indentLevel, final String pattern, final Object... args) {
        this.indentLevel = indentLevel;
        this.pattern = pattern;
        this.objectArguments = args;
        this.arguments = _InternalParameterizeMessageFormatAssist.objectsToStrings(args);
    }

    public ParameterizeMessageFormatter(final String pattern, final String... args) {
        this.pattern = pattern;
        this.arguments = args;
    }

    public ParameterizeMessageFormatter(final int indentLevel, final String pattern, final String... args) {
        this.indentLevel = indentLevel;
        this.pattern = pattern;
        this.arguments = args;
    }

    public String getPattern() {
        return pattern;
    }

    public Object[] getArguments() {
        return objectArguments != null ? objectArguments : arguments;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public String getFormattedMessage() {
        if (formattedMessage == null) {
            formattedMessage = _InternalParameterizeMessageFormatAssist.formatMessage(pattern, arguments);
            if (indentLevel > 0) {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < indentLevel; i++) {
                    result.append(INDENT);
                }
                formattedMessage = result.append(formattedMessage).toString();
            }
        }
        return formattedMessage;
    }

    public static String format(final String pattern, final Object... args) {
        String[] arguments = _InternalParameterizeMessageFormatAssist.objectsToStrings(args);
        return _InternalParameterizeMessageFormatAssist.formatStringArgs(pattern, arguments);
    }

    public static String format(final int indentLevel, final String pattern, final Object... args) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            result.append(INDENT);
        }
        return result.append(format(pattern, args)).toString();
    }

}