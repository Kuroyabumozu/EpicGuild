package org.yunshanmc.epicguild.module;

import org.yunshanmc.ycl.command.CommandManager;

/**
 * 基模块
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public abstract class BaseModule implements Module {
    
    protected final String         name;
    protected final ModuleCommandManager commandManager;
    
    public BaseModule(String name) {
        this.name = name;
        this.commandManager = new ModuleCommandManager();
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    /**
     * @see org.yunshanmc.epicguild.module.Module#getCommandManager()
     */
    @Override
    public final CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    @Override
    public void onLoad() {
    }
    
    @Override
    public void onUnload() {
    }
}
