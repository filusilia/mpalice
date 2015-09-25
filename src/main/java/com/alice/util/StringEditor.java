package com.alice.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StringEditor {

    public static String convertText(String text, boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {

        String value = text;
        if (StringUtils.isNotBlank(text)) {
            if (!escapeHTML) {
                value = StringEscapeUtils.escapeHtml(value);
            }
            if (escapeJavaScript) {
                value = StringEscapeUtils.escapeJavaScript(value);
            }
            if (escapeSQL) {
                value = StringEscapeUtils.escapeSql(value);
            }
        }
        return value;
    }


    public static String reverseText(String text, boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {

        String value = text;
        if (StringUtils.isNotBlank(text)) {
            if (escapeHTML) {
                value = StringEscapeUtils.unescapeHtml(value);
            }
            if (escapeJavaScript) {
                value = StringEscapeUtils.unescapeJavaScript(value);
            }
        }
        return value;
    }

//    public static void main(String args[]) {
//        System.out.println(convertText("中国人", true, false, false));
//        System.out.println(reverseText("&#20013;&#22269;&#20154;", true, false, false));
//    }
}
