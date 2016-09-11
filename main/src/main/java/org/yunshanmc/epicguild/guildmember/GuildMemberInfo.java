package org.yunshanmc.epicguild.guildmember;

import java.util.UUID;

import org.yunshanmc.epicguild.guild.Guild;

/**
 * 公会成员信息
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月7日
 * <p>
 */
public interface GuildMemberInfo {
    
    /**
     * 获取成员的UUID
     * 
     * @return 玩家UUID
     */
    UUID getUUID();
    
    /**
     * 获取成员的名字
     * 
     * @return 玩家名
     */
    String getName();
    
    /**
     * 查看玩家是否加入了公会
     * 
     * @return 玩家已加入公会返回true，玩家未加入任何公会返回false
     */
    boolean hasGuild();
    
    /**
     * 获取玩家所在公会
     * 
     * @return 玩家所在的公会
     */
    Guild getGuild();
}
