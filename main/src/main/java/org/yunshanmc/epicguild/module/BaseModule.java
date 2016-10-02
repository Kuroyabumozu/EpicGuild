package org.yunshanmc.epicguild.module;

import org.yunshanmc.ycl.command.CommandManager;

/**
 * 基模块
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public abstract class BaseModule implements Module {
    
    protected final ModuleCommandManager commandManager = new ModuleCommandManager();
    
    private ModuleDescription description;
    
    @Override
    public final String getName() {
        return this.getDescription().getName();
    }
    
    @Override
    public final ModuleDescription getDescription() {
        return this.description;
    }
    
    /**
     * @see Module#getCommandManager()
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Module)) return false;
        return this.getName().equals(((Module) obj).getName());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
