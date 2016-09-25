package org.yunshanmc.epicguild.module;

import org.yunshanmc.ycl.command.CommandManager;

/**
 * 模块接口，表示一个模块
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年6月30日
 */
public interface Module {
    
    /**
     * 获取模块名
     * 
     * @return 模块名
     */
    String getName();
    
    /**
     * 获取模块描述
     *
     * @return 模块描述
     */
    ModuleDescription getDescription();
    
    /**
     * 获取该模块的命令管理器
     * 
     * @return 该模块的命令管理器
     */
    CommandManager getCommandManager();
    
    /**
     * 模块加载时被调用
     */
    void onLoad();
    
    /**
     * 模块卸载时被调用
     */
    void onUnload();
}
