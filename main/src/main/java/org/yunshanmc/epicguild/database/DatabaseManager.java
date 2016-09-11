package org.yunshanmc.epicguild.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.yunshanmc.epicguild.guild.Guild;
import org.yunshanmc.epicguild.guildmember.GuildMember;
import org.yunshanmc.epicguild.util.Util_Exception;
import org.yunshanmc.epicguild.util.Util_Message;
import org.yunshanmc.ycl.config.ConfigManager;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 数据库管理器
 */
public class DatabaseManager {
    
    private static final List<Class<?>> dataClasses = Lists.newLinkedList();
    
    static {
        dataClasses.add(Guild.class);
        dataClasses.add(GuildMember.class);
    }
    
    private final static String DBVersion = "2.0";
    
    private Database              database;
    private ReadOnlyConfiguration dbConfig;
    
    private String tablePrefix;
    
    private Map<Class<?>, Dao<?, ?>> daoCache = Maps.newHashMap();
    
    public DatabaseManager(ConfigManager configManager) {
        this.dbConfig = configManager.getPluginConfig().getReadOnlyConfiguration("database");
    }
    
    /**
     * 初始化数据库
     * 
     * @return 成功初始化返回true,否则返回false
     */
    public boolean init() {
        if (this.database != null && this.database.isConnected()) return true;
        if (this.dbConfig == null) {
            Util_Message.errorConsole("database.init.missingConfig");
            return false;
        }
        this.database = new Database(this.dbConfig);
        Util_Message.debugConsole(1, "database.init.connect");
        if (!this.database.connect(this.dbConfig.getString("type"))) {
            Util_Message.errorConsole("database.init.fail.connect");
            return false;
        }
        DatabaseHelper.setConnectionSource(this.database.getConnectionSource());
        
        this.tablePrefix = this.dbConfig.getString("table-prefix", "");
        try {
            DatabaseHelper.createDao(DatabaseVersion.class, this.tablePrefix);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        if (!this.confirmVersion()) return false;
        
        Util_Message.debugConsole(1, "database.init.initDao", this.tablePrefix);
        if (!this.initDao()) {
            Util_Message.errorConsole("database.init.fail.initDao");
            return false;
        }
        return true;
    }
    
    //初始化Data Access Objects
    private boolean initDao() {
        try {
            for (Class<?> cls : dataClasses) {
                Util_Message.debugConsole(2, "database.initDao.init", cls.getName());
                DatabaseHelper.createDao(cls, this.tablePrefix);
            }
            return true;
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e, "initDao");
            return false;
        }
    }
    
    /**
     * 确认数据库版本是否正确
     * 
     * @return 版本正确返回true，否则返回false
     */
    public boolean confirmVersion() {
        DatabaseVersion current = null;
        try {
            current = DatabaseHelper.getDao(DatabaseVersion.class).queryForId(DB_VERSION_ID);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        if (current == null) {
            Util_Message.warningConsole("database.version.write", DBVersion);
            return this.writeDBVersion();
        } else if (!DBVersion.equals(current.version)) {//实际版本与当前版本不相等
            Util_Message.errorConsole("database.version.unsupport", current.version, DBVersion);
            return false;
        } else {
            Util_Message.debugConsole(2, "database.version.state", DBVersion);
            return true;
        }
    }
    
    private boolean writeDBVersion() {
        return DatabaseHelper.create(new DatabaseVersion());
    }
    
    public void dispose() {
        this.daoCache.clear();
        if (this.database != null) {
            this.database.dispose();
            this.database = null;
        }
    }
    
    private static final String DB_VERSION_ID = "version";
    
    @DatabaseTable(tableName = "db_version")
    private static class DatabaseVersion {
        
        /** 用于禁止重复 */
        @DatabaseField(id = true, columnName = "name", canBeNull = false)
        protected final String name = DB_VERSION_ID;
        
        /** 数据库版本 */
        @DatabaseField(columnName = "version", canBeNull = false)
        protected String version = DBVersion;
    }
}
