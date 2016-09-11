package org.yunshanmc.epicguild.module;

import org.bukkit.plugin.java.JavaPlugin;
import org.yunshanmc.ycl.command.CommandManager;
import org.yunshanmc.ycl.command.simple.SimpleCommandManager;

/**
 * 模块命令管理器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年6月30日
 */
public class ModuleCommandManager extends SimpleCommandManager {
    
    /**
     * 模块命令管理器禁止管理插件的主命令
     */
    @Override
    public CommandManager setHandleCommand(String handle, JavaPlugin plugin) {
        throw new UnsupportedOperationException();
    }
}
