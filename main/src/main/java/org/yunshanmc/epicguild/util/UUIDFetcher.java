package org.yunshanmc.epicguild.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yunshanmc.ycl.exception.ExceptionUtils;

import com.google.common.collect.Maps;
import com.google.common.net.MediaType;

/**
 * UUID提取器
 * <p>
 * 作者：YunShan
 * <p>
 * 创建日期：2016年7月15日
 * <p>
 */
class UUIDFetcher {
    
    private static final String URL_NAME_TO_UUID = "https://api.mojang.com/users/profiles/minecraft/";
    
    private static final Map<String, UUIDCache> uuidCache = Maps.newHashMap();
    
    private static long cacheTime;
    
    /**
     * 设置UUID缓存时间
     * 
     * @param cacheTime
     *            要设置的缓存时间
     */
    public static void setCacheTime(long cacheTime) {
        UUIDFetcher.cacheTime = cacheTime;
    }
    
    // 获取缓存结束时间
    private static long getCacheTimeout() {
        if (cacheTime < 0) return Long.MAX_VALUE;
        return System.currentTimeMillis() + cacheTime;
    }
    
    /**
     * 获取正版玩家的UUID
     * 
     * @param player
     *            玩家对象
     * @return 玩家的正版UUID，获取失败时返回null
     */
    public static UUID getOnlinePlayerUUID(OfflinePlayer player) {
        UUID uuid = getUUIDFromCache(player.getName());
        if (uuid != null) return uuid;
        try {
            HttpURLConnection conn = createConnection(URL_NAME_TO_UUID + player.getName(), "GET", false);
            if (conn.getResponseCode() != 200) {// 可能出现：204 玩家名不存在；404 玩家名包含不合法字符(导致url解析错误)
                return null;
            }
            JSONObject res = (JSONObject) readJsonBody(conn);
            uuid = getUUIDFromString((String) res.get("id"));
            uuidCache.put((String) res.get("name"), new UUIDCache(uuid, getCacheTimeout()));
            return uuid;
        } catch (IOException | ParseException e) {
            ExceptionUtils.handle(e);
            return null;
        }
    }
    
    /**
     * 获取盗版玩家的UUID
     * 
     * @param player
     *            玩家对象列表
     * @return 玩家的盗版UUID(K->玩家名，V->UUID)
     */
    public static UUID getOfflinePlayerUUID(OfflinePlayer player) {
        UUID uuid = getUUIDFromCache(player.getName());
        if (uuid == null) {
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getName()).getBytes(StandardCharsets.UTF_8));
            uuidCache.put(player.getName(), new UUIDCache(uuid, getCacheTimeout()));
        }
        return uuid;
    }
    
    /*
     * private static final String URL_NAMES_TO_UUIDS =
     * "https://api.mojang.com/profiles/minecraft"; /** 批量获取正版玩家的UUID
     * 
     * @param players 玩家对象列表
     * 
     * @return 玩家的正版UUID(K->玩家名，V->UUID)，获取失败时返回null
     *
     * public static Map<String, UUID> getOnlinePlayersUUID(List<OfflinePlayer>
     * players) { try { HttpURLConnection conn =
     * createConnection(URL_NAMES_TO_UUIDS, "POST", false); List<String> names =
     * Lists.newArrayList(); for (OfflinePlayer p : players) {
     * names.add(p.getName()); } writeBody(conn, JSONArray.toJSONString(names));
     * if (conn.getResponseCode() != 200) {// 可能出现：400
     * 无有效Post数据(无post数据或不符合json格式) return null; } Map<String, UUID> uuids =
     * Maps.newHashMap(); JSONArray res = (JSONArray) readJsonBody(conn); for
     * (Object object : res) {
     * 
     * @SuppressWarnings("unchecked") Map<String, String> map = (Map<String,
     * String>) object; String name = map.get("name"); names.remove(name); UUID
     * uuid = getUUIDFromCache(name); if (uuid == null) { uuid =
     * getUUIDFromString(map.get("id")); uuidCache.put(name, new UUIDCache(uuid,
     * System.currentTimeMillis() + cacheTime)); } uuids.put(name, uuid); } if
     * (!names.isEmpty()) { for (String name : names) { uuids.put(name, null); }
     * } return uuids; } catch (IOException | ParseException e) {
     * ExceptionUtils.handle(e); return null; } }
     * 
     * /** 批量获取盗版玩家的UUID
     * 
     * @param player 多个玩家对象
     * 
     * @return 玩家的盗版UUID
     *
     * public static Map<String, UUID> getOfflinePlayersUUID(List<OfflinePlayer>
     * players) { Map<String, UUID> uuids = Maps.newHashMap(); for
     * (OfflinePlayer p : players) { uuids.put(p.getName(),
     * getOfflinePlayerUUID(p)); } return uuids; }
     * 
     * private static void writeBody(URLConnection connection, String contents)
     * throws IOException { try (OutputStream out =
     * connection.getOutputStream();) {
     * out.write(contents.getBytes(StandardCharsets.UTF_8)); out.flush(); } }
     */
    private static UUID getUUIDFromString(String id) {
        return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-"
                + id.substring(16, 20) + "-" + id.substring(20, 32));
    }
    
    private static UUID getUUIDFromCache(String name) {
        UUIDCache cache = uuidCache.get(name);
        if (cache.getTimeout() >= System.currentTimeMillis()) {
            return cache.getUUID();
        } else {
            uuidCache.remove(name);
            return null;
        }
    }
    
    private static Object readJsonBody(URLConnection connection) throws IOException, ParseException {
        try (InputStream in = connection.getInputStream();) {
            JSONParser parser = new JSONParser();
            return parser.parse(new InputStreamReader(in, StandardCharsets.UTF_8));
        }
    }
    
    private static HttpURLConnection createConnection(String urlStr, String method, boolean output) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", MediaType.JSON_UTF_8.toString());
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(output);
        return conn;
    }
    
    private static class UUIDCache {
        
        private final UUID uuid;
        private final long timeout;
        
        public UUIDCache(UUID uuid, long timeout) {
            this.uuid = uuid;
            this.timeout = timeout;
        }
        
        public UUID getUUID() {
            return this.uuid;
        }
        
        public long getTimeout() {
            return this.timeout;
        }
    }
}
