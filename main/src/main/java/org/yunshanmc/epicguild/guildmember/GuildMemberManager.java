package org.yunshanmc.epicguild.guildmember;

import org.bukkit.OfflinePlayer;

import java.util.List;

/**
 * 公会成员管理器
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月7日
 * <p>
 */
public interface GuildMemberManager {
    
    /**
     * 获取公会成员
     * 
     * @param player
     *            公会成员对应的玩家
     * @return 公会成员(如果玩家没进过服务器则返回null)
     */
    GuildMember getGuildMember(OfflinePlayer player);
    
    /**
     * 获取公会成员
     * 
     * @param playerName
     *            公会成员对应的玩家的名字
     * @return 公会成员(如果玩家没进过服务器则返回null)
     */
    GuildMember getGuildMember(String playerName);
    
    /**
     * 获取所有公会成员
     * 
     * @return 所有公会成员
     */
    List<GuildMember> getMembers();
}
