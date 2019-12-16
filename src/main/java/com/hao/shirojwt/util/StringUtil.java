package com.hao.shirojwt.util;

import java.security.MessageDigest;

/**
 * String工具
 */
public class StringUtil {

    public static void main(String[] args) {
        System.out.println(isBlank("   "));
        System.out.println(clearUnescape(" aa "));
    }

    /**
     * 定义下划线
     */
    private static final char UNDERLINE = '_';

    /**
     * String为空判断(不允许空格)
     * @param str
     * @return boolean
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * String不为空判断(不允许空格)
     * @param str
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Byte数组为空判断
     * @param bytes
     * @return boolean
     */
    public static boolean isNull(byte[] bytes) {
        // 根据byte数组长度为0判断
        return bytes == null || bytes.length == 0;
    }

    /**
     * Byte数组不为空判断
     * @param bytes
     * @return boolean
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }

    /**
     * 驼峰转下划线工具
     * @param param
     * @return java.lang.String
     */
    public static String camelToUnderline(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰工具
     * @param param
     * @return java.lang.String
     */
    public static String underlineToCamel(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 在字符串两周添加''
     * @param param
     * @return java.lang.String
     */
    public static String addSingleQuotes(String param) {
        return "\'" + param + "\'";
    }

    /**
     * 清除回车，换行，制表符，空格等
     * @param str
     * @return
     */
    public static String clearUnescape(String str){
        if(isBlank(str)){
            return "";
        }
        str = str.replaceAll("(\r\n|\r|\n|\n\r|\t|\\s)", "");
        return str;
    }

    //MD5
    static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public final static String MD5(String s) {
        try {
            byte[] btInput = s.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
