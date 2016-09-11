package org.yunshanmc.epicguild.guildmember;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.yunshanmc.epicguild.event.MemberJoinGuildEvent;
import org.yunshanmc.epicguild.event.MemberLeaveGuildEvent;
import org.yunshanmc.epicguild.guild.Guild;
import org.yunshanmc.epicguild.util.Util_Bukkit;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 公会成员
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月7日
 * <p>
 */
@DatabaseTable(tableName = "members")
public class GuildMember implements GuildMemberInfo, GuildMemberAction {
    
    /** 玩家UUID */
    @DatabaseField(id = true, columnName = "uuid")
    private final UUID   uuid;
    /** 玩家名 */
    @DatabaseField(unique = true, columnName = "player")
    private final String playerName;
    /** 玩家所属公会 */
    @DatabaseField(columnName = "guild", canBeNull = false, foreign = true, foreignColumnName = "name")
    private Guild        guild;
    
    @SuppressWarnings("unused")
    private GuildMember() {// orm 用
        this.playerName = null;
        this.uuid = null;
    }
    
    protected GuildMember(String playerName) {
        this.playerName = playerName;
        this.uuid = Util_Bukkit.getPlayerUUID(playerName);
    }
    
    protected GuildMember(OfflinePlayer player) {
        this.playerName = player.getName();
        this.uuid = Util_Bukkit.getPlayerUUID(player);
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberInfo#getUUID()
     */
    @Override
    public UUID getUUID() {
        return this.uuid;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberInfo#getName()
     */
    @Override
    public String getName() {
        return this.playerName;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberInfo#hasGuild()
     */
    @Override
    public boolean hasGuild() {
        return this.guild != null;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberInfo#getGuild()
     */
    @Override
    public Guild getGuild() {
        return this.guild;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberAction#joinGuild(org.yunshanmc.epicguild.guild.Guild)
     */
    @Override
    public boolean joinGuild(Guild guild) {
        if (this.hasGuild()) return false;
        MemberJoinGuildEvent event = new MemberJoinGuildEvent(this);
        Util_Bukkit.callEvent(event);
        if (!event.isCancelled()) {
            this.guild = guild;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberAction#leaveGuild()
     */
    @Override
    public boolean leaveGuild() {
        if (!this.hasGuild()) return false;
        MemberLeaveGuildEvent event = new MemberLeaveGuildEvent(this);
        Util_Bukkit.callEvent(event);
        if (!event.isCancelled()) {
            this.guild = null;
            return true;
        } else {
            return false;
        }
    }
    
}
