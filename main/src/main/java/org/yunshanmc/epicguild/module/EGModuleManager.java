package org.yunshanmc.epicguild.module;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Atomics;
import org.yunshanmc.epicguild.module.java.JavaModuleLoader;
import org.yunshanmc.epicguild.util.Util_Bukkit;
import org.yunshanmc.epicguild.util.Util_Exception;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 模块管理器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月6日
 */
public class EGModuleManager implements ModuleManager {
    
    private Map<String, Module>       modules = Maps.newHashMap();
    private Map<String, ModuleLoader> loaders = Maps.newHashMap();
    
    private ModuleDirWatcher dirWatcher;
    private Map<String, File> moduleFiles = Maps.newHashMap();
    
    /**
     * @param moduleDir
     *     模块安装文件夹
     */
    public EGModuleManager(Path moduleDir) {
        JavaModuleLoader javaLoader = JavaModuleLoader.getInstance();
        this.registerLoader("Java", javaLoader);
        
        try {
            this.dirWatcher = new ModuleDirWatcher(moduleDir, this);
            this.dirWatcher.start();
            Util_Bukkit.runTaskTimerAsynchronously5Min(() -> {
                synchronized (this.moduleFiles) {
                    this.moduleFiles = Maps.newHashMap(Maps.filterValues(this.moduleFiles, File::isFile));
                }
            });
        } catch (IOException e) {
            //TODO warning级提示
        }
    }
    
    @Override
    public Module loadModule(String moduleName) {
        Module module = this.modules.get(moduleName);
        if (module != null) return module;// 模块已加载
        
        File moduleFile = this.moduleFiles.get(moduleName);
        ModuleDescription des;
        try {
            des = ModuleDescription.loadFromConfiguration(
                ReadOnlyConfiguration.loadConfiguration(new FileInputStream(moduleFile)));
        } catch (InvalidDescriptionException e) {
            Util_Exception.handle(e);
            return null;
        } catch (FileNotFoundException e) {
            Util_Exception.handle(
                new InvalidModuleException(e, "fileNotFound", moduleName, moduleFile.getAbsolutePath()));
            return null;
        }
        
        AtomicReference<String> depend = Atomics.newReference();
        // 加载所有依赖模块
        if (!Iterables.all(des.getDepend(), name -> {
            depend.set(name);
            return this.loadModule(name) != null;
        })) {
            // 任意依赖模块加载失败，则本模块加载失败
            Util_Exception.handle(new UnknownDependencyException(moduleName, depend.get()));
            return null;
        }
        
        // 加载软依赖模块(软依赖模块加载失败不影响本模块的加载)
        des.getSoftDepend().forEach(this::loadModule);
        
        // 使用指定类型的模块加载器加载
        String type = des.getType();
        ModuleLoader loader = this.loaders.get(type);
        
        try {
            module = loader.loadModule(moduleName, moduleFile);
            if (module != null) {
                try {
                    module.onLoad();
                } catch (Throwable t) {
                    throw new InvalidModuleException(t, "onLoadMethodError", moduleName);
                }
                this.modules.put(module.getName(), module);
                return module;
            } else {
                throw new InvalidModuleException("loaderLoadFail", type, moduleName);
            }
        } catch (InvalidModuleException e) {
            Util_Exception.handle(e);
            if (module != null) module.onUnload();// 让模块关闭onLoad中打开的资源
            return null;
        }
    }
    
    @Override
    public void unloadModule(String moduleName) {
        Module module = this.modules.remove(moduleName);
        if (module != null) {
            module.onUnload();
            this.loaders.get(module.getDescription().getType()).unloadModule(moduleName);
        }
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
        type = type.toLowerCase();
        if (!this.loaders.containsKey(type)) {
            this.loaders.put(type, loader);
        }
    }
    
    @Override
    public void unregisterLoader(String type) {
        this.loaders.remove(type.toLowerCase());
    }
    
    protected void addModuleFile(File file) {
        ModuleDescription des;
        try {
            ZipFile zip = new ZipFile(file);
            ZipEntry entry = zip.getEntry("module.info");
            if (entry == null) return;
            des = ModuleDescription.loadFromConfiguration(
                ReadOnlyConfiguration.loadConfiguration(zip.getInputStream(entry)));
        } catch (ZipException e) {
            // 非有效模块文件，忽略错误
            return;
        } catch (InvalidDescriptionException e) {
            Util_Exception.handle(e);
            return;
        } catch (IOException e) {
            Util_Exception.handle(new InvalidModuleException(e, "IOException", file.getAbsolutePath()));
            return;
        }
        this.moduleFiles.put(des.getName(), file);
    }
    
}
