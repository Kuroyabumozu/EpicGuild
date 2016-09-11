package org.yunshanmc.epicguild.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

/**
 * Bukkit工具
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public final class Util_Bukkit {
    
    private Util_Bukkit() {//禁止实例化
    }
    
    /**
     * 触发事件
     * 
     * @param event
     *            要触发的事件
     */
    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
    
    /**
     * 触发异步事件
     * 
     * @param event
     *            要触发的异步事件
     */
    public static void callAsyncEvent(final Event event) {
        Bukkit.getScheduler().runTaskAsynchronously(EpicGuildAPI.getInstance(), new Runnable() {
            
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(event);
            }
        });
    }
    
    private static boolean OnlineMode;
    private static boolean UUIDFetcherInit = false;
    
    /**
     * 获取玩家UUID
     * @param player 玩家
     * @return 玩家UUID
     */
    public static UUID getPlayerUUID(OfflinePlayer player) {
        if (!UUIDFetcherInit) initUUIDFetcher();
        if (OnlineMode) {
            return UUIDFetcher.getOnlinePlayerUUID(player);
        } else {
            return UUIDFetcher.getOfflinePlayerUUID(player);
        }
    }
    
    /**
     * 根据玩家名获取玩家UUID
     * 
     * @param playerName
     *            玩家名
     * @return 玩家UUID
     */
    public static UUID getPlayerUUID(String playerName) {
        return getPlayerUUID(Bukkit.getOfflinePlayer(playerName));
    }
    
    /**
     * 初始化UUID提取器
     */
    public static void initUUIDFetcher() {
        if (UUIDFetcherInit) return;
        ReadOnlyConfiguration cfg = EpicGuildAPI.getConfigManager().getPluginConfig();
        boolean online = cfg.getBoolean("uuid.online-mode", false);// 默认使用盗版模式
        if (online != Bukkit.getOnlineMode()) {// 服务器是盗版模式，配置却是正版模式，或者相反
            Util_Message.warningConsole("init.onlinemode.inconsistent", Bukkit.getOnlineMode(), online);
        }
        OnlineMode = online;
        long cacheTime = cfg.getLong("uuid.cache-time", 1000 * 60 * 60 * 24/* 默认缓存一天 */);
        UUIDFetcher.setCacheTime(cacheTime);
        UUIDFetcherInit = true;
    }
    
}
