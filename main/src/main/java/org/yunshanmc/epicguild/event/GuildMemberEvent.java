package org.yunshanmc.epicguild.event;

import org.yunshanmc.epicguild.guildmember.GuildMember;

/**
 * 公会成员事件
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public abstract class GuildMemberEvent extends EGEvent {
    
    private final GuildMember member;
    
    /**
     * @param member
     *            与此事件相关的公会成员
     */
    protected GuildMemberEvent(GuildMember member) {
        this.member = member;
    }
    
    @Override
    protected void setDescription(String key, Object... args) {
        super.setDescription("member" + key, args);
    }
    
    /**
     * 获取与此事件相关的公会成员
     * 
     * @return 与此事件相关的公会成员
     */
    public GuildMember getMember() {
        return this.member;
    }
}
