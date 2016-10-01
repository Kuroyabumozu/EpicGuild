package org.yunshanmc.epicguild.module.java;

import com.google.common.collect.Maps;
import org.yunshanmc.epicguild.module.InvalidDescriptionException;
import org.yunshanmc.epicguild.module.InvalidModuleException;
import org.yunshanmc.epicguild.module.ModuleLoader;
import org.yunshanmc.epicguild.util.Util_Bukkit;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * 模块加载器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class JavaModuleLoader implements ModuleLoader {
    
    private static JavaModuleLoader Instance;
    
    private Map<String, ModuleClassLoader>       loaders = Maps.newHashMap();
    private Map<String, WeakReference<Class<?>>> classes = Maps.newConcurrentMap();
    
    private JavaModuleLoader() {// 单例模式
        Util_Bukkit.runTaskTimerAsynchronously5Min(() -> {
            synchronized (this.classes) {
                this.classes = Maps.filterValues(this.classes, ref -> ref.get() != null);
            }
        });
    }
    
    public static JavaModuleLoader getInstance() {
        if (Instance == null) {
            Instance = new JavaModuleLoader();
        }
        return Instance;
    }
    
    @Override
    public JavaModule loadModule(String moduleName, File moduleFile) throws InvalidModuleException {
        JavaModuleDescription des;
        try {
            des = JavaModuleDescription.loadFromConfiguration(
                ReadOnlyConfiguration.loadConfiguration(new FileInputStream(moduleFile)));
        } catch (InvalidDescriptionException e) {
            throw new InvalidModuleException(e, "invalidDescription");
        } catch (FileNotFoundException e) {
            throw new InvalidModuleException(e, "fileNotFound", moduleName, moduleFile.getAbsolutePath());
        }
        
        ModuleClassLoader loader;
        try {
            loader = new ModuleClassLoader(des, moduleFile, this.getClass().getClassLoader());
        } catch (Throwable e) {
            if (e instanceof InvalidModuleException) {
                throw (InvalidModuleException) e;
            } else {
                throw new InvalidModuleException(e, "unknownException", moduleName);
            }
        }
        this.loaders.put(des.getName(), loader);
        
        return loader.getModule();
    }
    
    @Override
    public void unloadModule(String name) {
        this.loaders.remove(name);
    }
}
