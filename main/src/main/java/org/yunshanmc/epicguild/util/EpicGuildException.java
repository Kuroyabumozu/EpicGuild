package org.yunshanmc.epicguild.util;

import org.bukkit.ChatColor;

/**
 * EG的异常，包装了错误信息的获取
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class EpicGuildException extends Exception {
    
    public EpicGuildException(String messageKey, Object... args) {
        this(null, messageKey, args);
    }
    
    public EpicGuildException(Throwable cause, String messageKey, Object... args) {
        super(ChatColor.stripColor(Util_Message.getMessage(messageKey, args)), cause);
    }
}
