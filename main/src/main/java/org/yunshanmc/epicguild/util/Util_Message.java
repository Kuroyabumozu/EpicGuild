package org.yunshanmc.epicguild.util;

import org.bukkit.command.CommandSender;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.ycl.message.MessageManager;
import org.yunshanmc.ycl.message.Messager;

/**
 * 信息工具
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public final class Util_Message {
    
    private Util_Message() {//禁止实例化
    }
    
    private static final String BUG_TIP_KEY = "message.bugTip";
    
    private static MessageManager messageManager;
    private static Messager       Messager;
    
    public static void setMessager(Messager messager) {
        Messager = messager;
    }
    
    private static void checkMessageManager() {
        if (messageManager == null) {
            messageManager = EpicGuildAPI.getMessageManager();
        }
    }
    
    /**
     * @param key
     *            本地化信息对应的键
     * @param args
     *            格式化信息所需的参数
     * @return 格式化后的本地化信息
     * @see org.yunshanmc.ycl.message.MessageManager#getMessage(java.lang.String,
     *      java.lang.Object[])
     */
    public static String getMessage(String key, Object... args) {
        checkMessageManager();
        return messageManager.getMessage(key, args);
    }
    
    /**
     * 用于快捷输出BUG信息
     * 
     * @see #warningConsole(String, Object...)
     */
    public static void bug(String msgKey, Object... args) {
        Messager.warningConsole(BUG_TIP_KEY);
        Messager.warningConsole(msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#info(org.bukkit.command.CommandSender,
     *      java.lang.String, java.lang.Object[])
     */
    public static void info(CommandSender receiver, String msgKey, Object... args) {
        Messager.info(receiver, msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#debug(int,
     *      org.bukkit.command.CommandSender, java.lang.String,
     *      java.lang.Object[])
     */
    public static void debug(int debugLevel, CommandSender receiver, String msgKey, Object... args) {
        Messager.debug(debugLevel, receiver, msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#warning(org.bukkit.command.CommandSender,
     *      java.lang.String, java.lang.Object[])
     */
    public static void warning(CommandSender receiver, String msgKey, Object... args) {
        Messager.warning(receiver, msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#error(org.bukkit.command.CommandSender,
     *      java.lang.String, java.lang.Object[])
     */
    public static void error(CommandSender receiver, String msgKey, Object... args) {
        Messager.error(receiver, msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#infoConsole(java.lang.String,
     *      java.lang.Object[])
     */
    public static void infoConsole(String msgKey, Object... args) {
        Messager.infoConsole(msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#debugConsole(int, java.lang.String,
     *      java.lang.Object[])
     */
    public static void debugConsole(int debugLevel, String msgKey, Object... args) {
        Messager.debugConsole(debugLevel, msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#warningConsole(java.lang.String,
     *      java.lang.Object[])
     */
    public static void warningConsole(String msgKey, Object... args) {
        Messager.warningConsole(msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#errorConsole(java.lang.String,
     *      java.lang.Object[])
     */
    public static void errorConsole(String msgKey, Object... args) {
        Messager.errorConsole(msgKey, args);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#setDebugLevel(int)
     */
    public static void setDebugLevel(int debugLevel) {
        Messager.setDebugLevel(debugLevel);
    }
    
    /**
     * @see org.yunshanmc.ycl.message.Messager#getDebugLevel()
     */
    public static int getDebugLevel() {
        return Messager.getDebugLevel();
    }
    
}
