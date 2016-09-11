package org.yunshanmc.epicguild.guild;

import java.util.HashMap;

/**
 * 公会管理器
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月6日
 * <p>
 */
public interface GuildManager {
    
    /**
     * 根据公会名获取公会
     * 
     * @param guildName
     *            公会名
     * @return 公会对象，不存在指定公会名的公会则返回null
     */
    Guild getGuild(String guildName);
    
    /**
     * 获取所有公会
     * 
     * @return 所有公会，K->公会名，V->公会对象
     */
    HashMap<String, Guild> getGuilds();
    
}