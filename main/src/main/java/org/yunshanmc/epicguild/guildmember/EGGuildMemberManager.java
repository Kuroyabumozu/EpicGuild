package org.yunshanmc.epicguild.guildmember;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.yunshanmc.epicguild.util.Util_Bukkit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EGGuildMemberManager implements GuildMemberManager {
    
    private Map<UUID, GuildMember> members = Maps.newHashMap();
    
    public EGGuildMemberManager() {
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberManager#getGuildMember(java.lang.String)
     */
    @Override
    public GuildMember getGuildMember(String playerName) {
        return this.getGuildMember(Bukkit.getOfflinePlayer(playerName));
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberManager#getGuildMember(org.bukkit.OfflinePlayer)
     */
    @Override
    public GuildMember getGuildMember(OfflinePlayer player) {
        if (!player.hasPlayedBefore()) return null;
        GuildMember member = this.members.get(Util_Bukkit.getPlayerUUID(player));
        if (member == null) {
            member = this.createGuildMember(player);
        }
        return member;
    }
    
    // 创建公会成员
    private GuildMember createGuildMember(OfflinePlayer player) {
        GuildMember member = new GuildMember(player);
        this.members.put(member.getUUID(), member);
        return member;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberManager#getMembers()
     */
    @Override
    public List<GuildMember> getMembers() {
        return Lists.newLinkedList(this.members.values());
    }
    
}