package org.yunshanmc.epicguild.event;

import org.bukkit.event.Event;
import org.yunshanmc.epicguild.util.Util_Message;

/**
 * EpicGuild的事件
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public abstract class EGEvent extends Event {
    
    private static final String DES_KEY = "message.event.";
    
    /** 事件描述 */
    private String description;
    
    protected EGEvent() {
    }
    
    /**
     * 设置本地化的事件描述
     * 
     * @param key
     *            本地化信息对应的键
     * @param args
     *            格式化信息所需的参数
     */
    protected void setDescription(String key, Object... args) {
        this.description = Util_Message.getMessage(DES_KEY + key, args);
    }
    
    /**
     * 获取事件描述
     * 
     * @return 事件描述
     */
    public String getDescription() {
        return this.description;
    }
}
