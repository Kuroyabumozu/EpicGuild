package org.yunshanmc.epicguild.guild;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 公会管理器
 */
public class EGGuildManager implements GuildManager {
    
    private Map<String, Guild> guilds = Maps.newHashMap();
    
    public EGGuildManager() {
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildManager#getGuild(java.lang.String)
     */
    @Override
    public Guild getGuild(String guildName) {
        return this.guilds.get(guildName);
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildManager#getGuilds()
     */
    @Override
    public HashMap<String, Guild> getGuilds() {
        return new HashMap<>(this.guilds);
    }
    
}
