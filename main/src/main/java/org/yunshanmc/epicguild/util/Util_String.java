package org.yunshanmc.epicguild.util;

import java.util.Objects;

import com.google.common.base.Strings;

/**
 * 字符串工具
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public final class Util_String {
    
    private Util_String() {//禁止实例化
    }
    
    /**
     * 对比版本号
     * <p>
     * 若第一个版本号大，则返回大于0的值 若第二个版本号大，则返回小于0的值 若两个版本号相等，则返回0
     * 
     * @param version1
     *            第一个版本号
     * @param version2
     *            第二个版本号
     * @return 对比结果
     * @throws NullPointerException
     *             任意一个参数为null时抛出
     */
    public static int compareVersion(String version1, String version2) {
        Objects.requireNonNull(version1);
        Objects.requireNonNull(version2);
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
    
    /**
     * 批量验证字符串是否为空，当至少有一个为空时返回true
     * 
     * @param strings
     *            字符串列表
     * @return 至少有一个字符串为空时返回true
     */
    public static boolean isNullOrEmpty(String... strings) {
        for (String string : strings) {
            if (Strings.isNullOrEmpty(string)) return true;
        }
        return false;
    }
}
