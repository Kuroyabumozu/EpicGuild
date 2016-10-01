package org.yunshanmc.epicguild.util;

import org.yunshanmc.ycl.exception.ExceptionUtils;

import java.sql.SQLException;

/**
 * 异常工具类
 */
public final class Util_Exception {
    
    private Util_Exception() {//禁止实例化
    }
    
    public static void handle(EpicGuildException exception) {
        if (exception.getCause() != null) {
            ExceptionUtils.handle(exception.getCause());// TODO 处理cause
        }
        Util_Message.errorConsole(exception.getMessageKey(), exception.getArgs());
    }
    
    /**
     * 处理SQL异常
     * <p>
     * 异常提示将使用默认的database.sqlExcetion.default
     * 
     * @param e
     *            要处理的SQL异常
     */
    public static void handleSQLException(SQLException e) {
        handleSQLException(e, "default");
    }
    
    /**
     * 处理SQL异常
     * @param e 要处理的SQL异常
     * @param sqlMsgKey 异常信息的键(自动在前面添加<code>database.sqlExcetion.</code>)
     * @param args 信息文本中的参数列表
     */
    public static void handleSQLException(SQLException e, String sqlMsgKey, Object... args) {
        Util_Message.warningConsole("database.sqlExcetion." + sqlMsgKey, args);
        e.printStackTrace();
    }
}
