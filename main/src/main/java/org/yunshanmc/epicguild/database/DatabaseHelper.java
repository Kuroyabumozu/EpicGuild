package org.yunshanmc.epicguild.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.yunshanmc.epicguild.util.Util_Exception;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;

/**
 * 数据库帮助类
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public final class DatabaseHelper {
    
    private DatabaseHelper() {
    }
    
    private static ConnectionSource Conn;
    
    private static final Map<Class, Dao> daoCache = Maps.newHashMap();
    
    public static <T, ID> Dao<T, ID> getDao(Class<T> dataClass) {
        return daoCache.get(dataClass);
    }
    
    public static void setConnectionSource(ConnectionSource connection) {
        Conn = connection;
    }
    
    public static <T, ID> Dao<T, ID> createDao(Class<T> dataClass, String tablePrefix) throws SQLException {
        Dao<T, ID> dao = getDao(dataClass);
        if (dao != null) return dao;
        DatabaseTableConfig<T> tableConfig = new DatabaseTableConfig<>(dataClass, null);
        if (!Strings.isNullOrEmpty(tablePrefix)) {
            tableConfig.setTableName(tablePrefix + tableConfig.getTableName());
        }
        TableUtils.createTableIfNotExists(Conn, tableConfig);
        dao = DaoManager.createDao(Conn, tableConfig);
        daoCache.put(dataClass, dao);
        return dao;
    }
    
    public static <T> boolean create(T obj) {
        try {
            Dao dao = getDao(obj.getClass());
            dao.create(obj);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
    
    public static <T> boolean create(Collection<T> objs) {
        if (objs.isEmpty()) return true;
        try {
            Dao dao = getDao(objs.iterator().next().getClass());
            dao.create(objs);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
    
    public static <T> boolean update(T obj) {
        try {
            Dao dao = getDao(obj.getClass());
            dao.update(obj);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
    
    public static <T> boolean delete(T obj) {
        try {
            Dao dao = getDao(obj.getClass());
            dao.delete(obj);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
    
    public static <T> boolean delete(Collection<T> objs) {
        try {
            Dao dao = getDao(objs.iterator().next().getClass());
            dao.delete(objs);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
    
    public static <T> List<T> queryAll(Class<T> dataClass) {
        try {
            return getDao(dataClass).queryForAll();
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return new ArrayList<T>();
        }
    }
    
    public static <T, ID> T queryForID(Class<T> dataClass, ID id) {
        try {
            return getDao(dataClass).queryForId(id);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return null;
        }
    }
    
    public static <T> List<T> queryForEq(Class<T> dataClass, String fieldName, Object value) {
        try {
            return getDao(dataClass).queryForEq(fieldName, value);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return new ArrayList<T>();
        }
    }
    
    public static <T> boolean createTableIfNotExists(ConnectionSource conn, Class<T> dataClass) {
        try {
            TableUtils.createTableIfNotExists(conn, dataClass);
        } catch (SQLException e) {
            Util_Exception.handleSQLException(e);
            return false;
        }
        return true;
    }
}
