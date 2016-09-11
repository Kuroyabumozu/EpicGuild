package org.yunshanmc.epicguild.guildmember;

import org.yunshanmc.epicguild.guild.Guild;

/**
 * 公会成员行为
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public interface GuildMemberAction {
    
    /**
     * 加入指定公会
     * <p>
     * 注：若玩家已加入某个公会，则会加入失败
     * 
     * @param guild
     *            要加入的公会
     * @return 加入成功返回true，加入失败返回false
     */
    boolean joinGuild(Guild guild);
    
    /**
     * 退出公会
     * <p>
     * 注：若玩家未加入任何公会，则退出失败
     *
     * @return 退出成功返回true，加入失败返回false
     */
    boolean leaveGuild();
}
