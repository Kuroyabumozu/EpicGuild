package org.yunshanmc.epicguild;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.yunshanmc.epicguild.command.EGCommandManager;
import org.yunshanmc.epicguild.database.DatabaseManager;
import org.yunshanmc.epicguild.guild.EGGuildManager;
import org.yunshanmc.epicguild.guild.GuildManager;
import org.yunshanmc.epicguild.guildmember.EGGuildMemberManager;
import org.yunshanmc.epicguild.guildmember.GuildMemberManager;
import org.yunshanmc.epicguild.mcstats.Metrics;
import org.yunshanmc.epicguild.module.EGModuleManager;
import org.yunshanmc.epicguild.module.ModuleManager;
import org.yunshanmc.epicguild.util.Util_Bukkit;
import org.yunshanmc.epicguild.util.Util_Message;
import org.yunshanmc.ycl.config.ConfigManager;
import org.yunshanmc.ycl.config.DefaultConfigManager;
import org.yunshanmc.ycl.exception.ExceptionHandler;
import org.yunshanmc.ycl.exception.ExceptionUtils;
import org.yunshanmc.ycl.locale.LocaleManager;
import org.yunshanmc.ycl.message.GroupMessageManager;
import org.yunshanmc.ycl.message.MessageManager;
import org.yunshanmc.ycl.message.Messager;
import org.yunshanmc.ycl.resource.ResourceManager;
import org.yunshanmc.ycl.resource.StandardResourceManager;

/**
 * EpicGuild插件主类
 */
public class EpicGuildPlugin extends JavaPlugin {
    
    private DatabaseManager    databaseManager;
    private GuildManager        guildManager;
    private GuildMemberManager guildMemberManager;
    private EGCommandManager     commandManager;
    private ConfigManager      configManager;
    private LocaleManager      localeManager;
    private MessageManager     messageManager;
    private ResourceManager    resourceManager;
    private ModuleManager      moduleManager;
    
    private Messager messager;
    
    @Override
    public void onLoad() {
        try {
            this.resourceManager = new StandardResourceManager(this);
            this.localeManager = new LocaleManager();
            this.messageManager = new GroupMessageManager(this.resourceManager, this.localeManager);
            this.messager = this.messageManager.createMessager();
            Util_Message.setMessager(this.messager);
            ExceptionUtils.setExceptionHandler(new ExceptionHandler() {
                
                @Override
                public void handle(Throwable t) {
                    Util_Message.errorConsole("message.inter.unhandleException.before", t.getClass().getName(),
                            t.getMessage());
                    t.printStackTrace();
                    Util_Message.errorConsole("message.inter.unhandleException.after", t.getClass().getName(),
                            t.getMessage());
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onEnable() {
        try {
            this.onEnable0();
        } catch (Throwable e) {
            Util_Message.errorConsole("init.unhandleException", e.getClass().getName(), e.getMessage());
            e.printStackTrace();
            this.setEnabled(false);
        }
    }
    
    @Override
    public void onDisable() {
        try {
            this.onDisable0();
        } catch (Throwable e) {
            Util_Message.errorConsole("finalize.unhandleException", e.getClass().getName(), e.getMessage());
            e.printStackTrace();
        }
    }
    
    //启动插件
    private void onEnable0() {
        Util_Message.infoConsole("init.plugin.init");
        
        EpicGuildAPI.setInstance(this);
        this.configManager = new DefaultConfigManager(this.resourceManager);
        
        int debug = this.configManager.getPluginConfig().getInt("debug");
        Util_Message.setDebugLevel(debug);
        
        Util_Bukkit.initUUIDFetcher();// 初始化UUID提取器
        
        this.databaseManager = new DatabaseManager(this.configManager);
        File dataPath = new File(getDataFolder(), "data");
        if (!dataPath.exists()) dataPath.mkdirs();
        
        Util_Message.infoConsole("init.database.init");
        if (!this.databaseManager.init()) {
            Util_Message.errorConsole("init.database.fail");
            this.setEnabled(false);
            return;
        }
        Util_Message.infoConsole("init.database.success");
        
        this.guildMemberManager = new EGGuildMemberManager();
        
        this.guildManager = new EGGuildManager();
        
        this.moduleManager = new EGModuleManager();
        
        this.registerCommands();
        this.registerListeners();
        
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        Util_Message.infoConsole("init.plugin.success");
    }
    
    public void onDisable0() {
        Util_Message.infoConsole("finalize.plugin.finalize");
        
        HandlerList.unregisterAll(this);
        
        Util_Message.infoConsole("finalize.database.finalize");
        this.databaseManager.dispose();
        
        Util_Message.infoConsole("finalize.plugin.success");
    }
    
    private void registerCommands() {//TODO
    }
    
    private void registerListeners() {//TODO
    }
    
    @Override
    public void saveDefaultConfig() {
        this.configManager.saveDefaultConfig("config.yml", "config.yml", false);
    }
    
    @Override
    public FileConfiguration getConfig() {
        YamlConfiguration yml = new YamlConfiguration();
        try {
            yml.loadFromString(this.configManager.getPluginConfig().toString());
        } catch (InvalidConfigurationException e) {
            ExceptionUtils.handle(e);
        }
        return yml;
    }
    
    /**
     * 获取本地化管理器
     * 
     * @return 本地化管理器
     */
    public LocaleManager getLocaleManager() {
        return this.localeManager;
    }
    
    /**
     * 获取资源管理器
     * 
     * @return 资源管理器
     */
    public ResourceManager getResourceManager() {
        return this.resourceManager;
    }
    
    /**
     * 获取数据库管理器
     * 
     * @return 数据库管理器
     */
    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }
    
    /**
     * 获取配置管理器
     * 
     * @return 配置管理器
     */
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    /**
     * 获取命令管理器
     * 
     * @return 命令管理器
     */
    public EGCommandManager getCommandManager() {
        return this.commandManager;
    }
    
    /**
     * 获取公会管理器
     * 
     * @return 公会管理器
     */
    public GuildManager getGuildManager() {
        return this.guildManager;
    }
    
    /**
     * 获取公会成员管理器
     * 
     * @return 公会成员管理器
     */
    public GuildMemberManager getGuildMemberManager() {
        return this.guildMemberManager;
    }
    
    /**
     * 获取模块管理器
     * 
     * @return 模块管理器
     */
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    /**
     * 获取信息管理器
     * 
     * @return 信息管理器
     */
    public MessageManager getMessageManager() {
        return this.messageManager;
    }
}
