package org.yunshanmc.epicguild;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.yunshanmc.epicguild.command.EGCommandManager;
import org.yunshanmc.epicguild.database.DatabaseManager;
import org.yunshanmc.epicguild.guild.Guild;
import org.yunshanmc.epicguild.guild.GuildManager;
import org.yunshanmc.epicguild.guildmember.GuildMember;
import org.yunshanmc.epicguild.guildmember.GuildMemberManager;
import org.yunshanmc.epicguild.module.ModuleManager;
import org.yunshanmc.ycl.config.ConfigManager;
import org.yunshanmc.ycl.locale.LocaleManager;
import org.yunshanmc.ycl.message.MessageManager;
import org.yunshanmc.ycl.resource.ResourceManager;

/**
 * EpicGuild插件提供的API
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public final class EpicGuildAPI {
    
    private EpicGuildAPI() {//禁止实例化
    
    }
    
    private static EpicGuildPlugin epicguild;
    
    /**
     * 获取插件实例(用于部分BukkitAPI的Plugin形参)
     * @return
     */
    public static Plugin getInstance() {
        return epicguild;
    }
    
    /**
     * 设置插件实例(用于此API类的代理调用)
     * 
     * @param instance
     *            插件实例
     */
    protected static void setInstance(EpicGuildPlugin instance) {
        epicguild = instance;
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getLocaleManager()
     */
    public static LocaleManager getLocaleManager() {
        return epicguild.getLocaleManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getResourceManager()
     */
    public static ResourceManager getResourceManager() {
        return epicguild.getResourceManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getDatabaseManager()
     */
    public static DatabaseManager getDatabaseManager() {
        return epicguild.getDatabaseManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getConfigManager()
     */
    public static ConfigManager getConfigManager() {
        return epicguild.getConfigManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getCommandManager()
     */
    public static EGCommandManager getCommandManager() {
        return epicguild.getCommandManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getGuildManager()
     */
    public static GuildManager getGuildManager() {
        return epicguild.getGuildManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getGuildMemberManager()
     */
    public static GuildMemberManager getGuildMemberManager() {
        return epicguild.getGuildMemberManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getModuleManager()
     */
    public static ModuleManager getModuleManager() {
        return epicguild.getModuleManager();
    }
    
    /**
     * @see org.yunshanmc.epicguild.EpicGuildPlugin#getMessageManager()
     */
    public static MessageManager getMessageManager() {
        return epicguild.getMessageManager();
    }
    
    //TODO 注释
    public static Guild getGuild(String guild) {
        return getGuildManager().getGuild(guild);
    }
    
    public static GuildMember getGuildMember(String player) {
        return getGuildMemberManager().getGuildMember(player);
    }
    
    public static GuildMember getGuildMember(OfflinePlayer player) {
        return getGuildMemberManager().getGuildMember(player);
    }
}
