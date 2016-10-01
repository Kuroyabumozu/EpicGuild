package org.yunshanmc.epicguild.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

import java.util.UUID;

/**
 * Bukkit工具
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月13日
 * <p>
 */
public final class Util_Bukkit {
    
    private static boolean OnlineMode;
    private static boolean UUIDFetcherInit = false;
    
    private Util_Bukkit() {//禁止实例化
    }
    
    /**
     * 执行异步循环任务
     *
     * @param task
     *     要执行的任务
     * @param delay
     *     首次执行的延时
     * @param period
     *     执行周期
     *
     * @return 包含任务ID的BukkitTask
     *
     * @see BukkitScheduler#runTaskTimerAsynchronously(Plugin, Runnable, long, long)
     */
    public static BukkitTask runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(EpicGuildAPI.getInstance(), task, delay, period);
    }
    
    /**
     * 执行异步循环任务
     * <p>
     * <b>使用5分钟的延时和周期</b>
     *
     * @param task
     *     要执行的任务
     *
     * @return 包含任务ID的BukkitTask
     *
     * @see #runTaskTimerAsynchronously(Runnable, long, long)
     */
    public static BukkitTask runTaskTimerAsynchronously5Min(Runnable task) {
        return runTaskTimerAsynchronously(task, 20 * 60 * 5, 20 * 60 * 5);
    }
    
    /**
     * 触发事件
     *
     * @param event
     *     要触发的事件
     */
    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
    
    /**
     * 触发异步事件
     *
     * @param event
     *     要触发的异步事件
     */
    public static void callAsyncEvent(final Event event) {
        Bukkit.getScheduler().runTaskAsynchronously(EpicGuildAPI.getInstance(),
                                                    () -> Bukkit.getPluginManager().callEvent(event));
    }
    
    /**
     * 获取玩家UUID
     *
     * @param player
     *     玩家
     *
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
     *     玩家名
     *
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
