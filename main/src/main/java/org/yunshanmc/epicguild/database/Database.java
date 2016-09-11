package org.yunshanmc.epicguild.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.epicguild.util.Util_Exception;
import org.yunshanmc.epicguild.util.Util_Message;
import org.yunshanmc.epicguild.util.Util_String;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;
import org.yunshanmc.ycl.exception.ExceptionUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * 数据库
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public class Database {
    
    public static enum DBType {
        MySQL("mysql"),
        SQLite("sqlite");
        
        private String driver;
        
        private DBType(String driver) {
            this.driver = driver;
        }
        
        public String getDriver() {
            return this.driver;
        }
        
        public static DBType matchType(String dbType) {
            for (DBType type : DBType.values()) {
                if (type.toString().equalsIgnoreCase(dbType)) {
                    return type;
                }
            }
            Util_Message.errorConsole("database.type.unsupport", dbType);
            return null;
        }
    }
    
    /** 数据库配置 */
    private final ReadOnlyConfiguration dbConfig;
    
    public DBType currentType;
    
    private ConnectionSource connectionSource;
    
    /**
     * @param dbConfig
     *            数据库配置
     */
    protected Database(ReadOnlyConfiguration dbConfig) {
        this.dbConfig = dbConfig;
    }
    
    public boolean connect(String dbType) {
        if (this.isConnected()) {
            return true;
        }
        this.currentType = DBType.matchType(dbType);
        if (this.currentType == null) return false;
        String dbUrl = this.getDatabaseUrl();
        if (dbUrl == null) return false;
        Util_Message.debugConsole(2, "database.connect.tryConnect", dbType, dbUrl);
        try {
            this.connectionSource = new JdbcConnectionSource(dbUrl);
            return true;
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e, "connect", dbType, dbUrl);
        }
        return false;
    }
    
    private String getDatabaseUrl() {
        String url = "jdbc:" + this.currentType.getDriver() + ":";
        ReadOnlyConfiguration cfg = this.dbConfig.getReadOnlyConfiguration(this.currentType.name());
        if (cfg == null) {
            Util_Message.errorConsole("database.buildUrl.missingConfig.driver", this.currentType);
            return null;
        }
        switch (this.currentType) {
            case SQLite: {
                String path = cfg.getString("path");
                if (Util_String.isNullOrEmpty(path)) {
                    Util_Message.errorConsole("database.buildUrl.missingConfig.SQLite", path);
                    return null;
                }
                Util_Message.debugConsole(2, "database.buildUrl.build.SQLite", path);
                File dbFile = new File(EpicGuildAPI.getInstance().getDataFolder(), path);
                dbFile.getParentFile().mkdirs();
                url += dbFile.getAbsolutePath();
                break;
            }
            case MySQL: {
                String host = cfg.getString("host");
                String port = cfg.getString("port");
                String dbName = cfg.getString("database-name");
                String user = cfg.getString("user");
                String psd = cfg.getString("password");
                if (Util_String.isNullOrEmpty(host, port, dbName) || user == null || psd == null) {
                    Util_Message.errorConsole("database.buildUrl.missingConfig.MySQL", host, port, dbName, user, psd);
                    return null;
                }
                Util_Message.debugConsole(2, "database.buildUrl.build.MySQL", host, port, dbName, user, psd);
                url += "//" + host + ":" + port + "/" + dbName + "?user=" + user + "&password=" + psd
                        + "&createDatabaseIfNotExist=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
                break;
            }
            default:
                Util_Message.bug("database.buildUrl.unsupport", this.currentType);
                return null;
        }
        return url;
    }
    
    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }
    
    public boolean isConnected() {
        return this.connectionSource != null && this.connectionSource.isOpen();
    }
    
    public void dispose() {
        try {
            if (this.connectionSource != null) {
                this.connectionSource.close();
            }
        } catch (SQLException e) {
            ExceptionUtils.handle(e);
            Util_Message.warningConsole("finalize.database.closeConnection");
        } finally {
            this.connectionSource = null;
        }
    }
}
