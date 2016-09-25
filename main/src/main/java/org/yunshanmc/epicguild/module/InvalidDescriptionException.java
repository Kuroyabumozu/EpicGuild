package org.yunshanmc.epicguild.module;

import org.yunshanmc.epicguild.util.EpicGuildException;

/**
 * 无效的模块描述异常
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class InvalidDescriptionException extends EpicGuildException {
    
    public InvalidDescriptionException(String messageKey, Object... args) {
        this(null, "module.load.invalidDescription." + messageKey, args);
    }
    
    public InvalidDescriptionException(Throwable cause, String messageKey, Object... args) {
        super("module.load.invalidDescription." + messageKey, args);
    }
}
