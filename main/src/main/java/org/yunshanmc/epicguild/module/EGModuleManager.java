package org.yunshanmc.epicguild.module;

import com.google.common.collect.Maps;
import org.yunshanmc.epicguild.util.Util_Message;

import java.io.File;
import java.util.Collections;
import java.util.Map;

/**
 * 模块管理器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public class EGModuleManager implements ModuleManager {
    
    //TODO 一大波注释待填补
    private Map<String, Module>       modules = Maps.newHashMap();
    private Map<String, ModuleLoader> loaders = Maps.newHashMap();
    
    @Override
    public Module loadModule(String type, File moduleFile) {// 依次使用注册的模块加载器尝试加载模块
        ModuleLoader loader = this.loaders.get(type);
        Module module = null;
        try {
            module = loader.loadModule(moduleFile);
        } catch (InvalidModuleException e) {
            e.printStackTrace();
            Util_Message.errorConsole("module.load.loadModuleError", e.getMessage());
            return null;
        }
        if (module != null) {
            try {
                module.onLoad();
            } catch (Throwable t) {
                t.printStackTrace();
                Util_Message.errorConsole("module.load.onLoadError", moduleFile.getAbsolutePath());
                return null;
            }
            return module;
        }
        return null;
    }
    
    @Override
    public void unloadModule(String moduleName) {
        // TODO: Implement this method
    }
    
    @Override
    public boolean isModuleLoaded(String moduleName) {
        return this.getModule(moduleName) != null;
    }
    
    @Override
    public Module getModule(String moduleName) {
        return this.modules.get(moduleName);
    }
    
    @Override
    public Map<String, Module> getLoadedModules() {
        return Collections.unmodifiableMap(this.modules);
    }
    
    @Override
    public void registerLoader(String type, ModuleLoader loader) {
        if (!this.loaders.containsKey(type)) {
            this.loaders.put(type, loader);
        }
    }
    
    @Override
    public void unregisterLoader(String type) {
        this.loaders.remove(type);
    }
}
