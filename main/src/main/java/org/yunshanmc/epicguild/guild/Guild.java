package org.yunshanmc.epicguild.guild;

import java.util.HashSet;
import java.util.Set;

import org.yunshanmc.epicguild.guildmember.GuildMember;

import com.google.common.collect.Sets;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 公会
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月6日
 * <p>
 */
@DatabaseTable(tableName = "guilds")
public class Guild implements GuildInfo, GuildAction {
    
    /** 公会名 */
    @DatabaseField(columnName = "name", id = true)
    private final String name;
    /** 公会所有者(公会会长) */
    @DatabaseField(columnName = "owner", canBeNull = false)
    private String       owner;
    /** 公会创建时间 */
    @DatabaseField(columnName = "create_time", canBeNull = false)
    private final long   createTime;
    
    private Set<GuildMember> members = Sets.newHashSet();
    
    @SuppressWarnings("unused")
    private Guild() {// orm 用
        this(null, null, 0);
    }
    
    protected Guild(String name, String owner, long createTime) {
        this.name = name;
        this.owner = owner;
        this.createTime = createTime;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildInfo#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildInfo#getCreateTime()
     */
    @Override
    public long getCreateTime() {
        return this.createTime;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildInfo#getOwner()
     */
    @Override
    public String getOwner() {
        return this.owner;
    }
    
    /**
     * @see org.yunshanmc.epicguild.guild.GuildInfo#getMembers()
     */
    @Override
    public Set<GuildMember> getMembers() {
        return new HashSet<>(this.members);
    }
    
}
