package org.yunshanmc.epicguild.module;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 模块管理器实现类
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public class EGModuleManager implements ModuleManager {
    
    //TODO 一大波注释待填补
    private Map<String, Module> modules = Maps.newHashMap();
    
    @Override
    public Module loadModule(String moduleName) {
        // TODO: Implement this method
        return null;
    }
    
    @Override
    public void unloadModule(String moduleName) {
        // TODO: Implement this method
    }
    
    @Override
    public boolean isModuleLoaded(String moduleName) {
        return this.getLoadedModule(moduleName) != null;
    }
    
    @Override
    public Module getLoadedModule(String moduleName) {
        return this.modules.get(moduleName);
    }
    
    @Override
    public Map<String, Module> getLoadedModules() {
        return Collections.unmodifiableMap(this.modules);
    }
}
