package org.yunshanmc.epicguild.guild;

import java.util.Set;

import org.yunshanmc.epicguild.guildmember.GuildMember;

/**
 * 公会信息
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月6日
 * <p>
 */
public interface GuildInfo {
    
    /**
     * 获取公会创建时间
     * 
     * @return 公会创建时间(the number of milliseconds from 1970-01-01T00:00:00Z )
     */
    long getCreateTime();
    
    /**
     * 获取公会名
     * 
     * @return 公会名
     */
    String getName();
    
    /**
     * 获取公会所有者(公会会长)
     * 
     * @return 公会所有者的名字
     */
    String getOwner();
    
    /**
     * 获取公会所有成员
     * 
     * @return 所有公会成员
     */
    Set<GuildMember> getMembers();
}
