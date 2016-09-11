package org.yunshanmc.epicguild.event;

import org.yunshanmc.epicguild.guild.Guild;

/**
 * 公会事件
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月15日
 * <p>
 */
public abstract class GuildEvent extends EGEvent {
    
    private final Guild guild;
    
    /**
     * @param guild
     *            与此事件相关的公会
     */
    protected GuildEvent(Guild guild) {
        this.guild = guild;
    }
    
    @Override
    protected void setDescription(String key, Object... args) {
        super.setDescription("guild" + key, args);
    }
    
    /**
     * 获取与此事件相关的公会
     * 
     * @return 与此事件相关的公会
     */
    public Guild getGuild() {
        return this.guild;
    }
}
