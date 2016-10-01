package org.yunshanmc.epicguild.module;

import org.yunshanmc.epicguild.util.EpicGuildException;

/**
 * 模块无效异常
 * <p>
 * 无效包括：文件无法读取、格式错误等
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class InvalidModuleException extends EpicGuildException {
    
    public InvalidModuleException(String reason, Object... args) {
        this(null, reason, args);
    }
    
    public InvalidModuleException(Throwable cause, String reason, Object... args) {
        super(cause, "module.load.invalidModule." + reason, args);
    }
}
