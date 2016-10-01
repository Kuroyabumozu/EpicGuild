package org.yunshanmc.epicguild.module;

import org.yunshanmc.epicguild.util.EpicGuildException;

import java.util.ArrayList;

/**
 * 无效的模块描述异常
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class InvalidDescriptionException extends EpicGuildException {
    
    public InvalidDescriptionException(String reason, String moduleName, Object... otherArgs) {
        this(null, reason, moduleName, otherArgs);
    }
    
    public InvalidDescriptionException(Throwable cause, String reason, String moduleName, Object... otherArgs) {
        super(cause, "module.load.invalidDescription." + reason, new ArrayList<Object>(){{
            this.add(moduleName);
            for (Object obj : otherArgs) {
                this.add(obj);
            }
        }}.toArray());
    }
}
