package org.yunshanmc.epicguild.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.yunshanmc.epicguild.guildmember.GuildMember;

/**
 * 玩家加入公会事件
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public class MemberJoinGuildEvent extends GuildMemberEvent implements Cancellable {
    
    private static final HandlerList handlers = new HandlerList();
    
    private boolean cancelled;
    
    /**
     * 玩家加入公会事件
     * 
     * @param member
     *            公会成员
     */
    public MemberJoinGuildEvent(GuildMember member) {
        super(member);
        super.setDescription("join", member.getName(), member.getGuild().getName());
    }
    
    /**
     * @see org.bukkit.event.Event#getHandlers()
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    /**
     * @see org.bukkit.event.Cancellable#isCancelled()
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    /**
     * @see org.bukkit.event.Cancellable#setCancelled(boolean)
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    
}
