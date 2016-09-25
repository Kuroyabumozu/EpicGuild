package org.yunshanmc.epicguild.module;

import java.io.File;
import java.util.Map;

/**
 * 模块管理器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年6月30日
 */
public interface ModuleManager {
    
    /**
     * 加载指定的模块
     *
     * @param type
     *     模块类型
     * @param moduleFile
     *     模块文件
     *
     * @return 加载好的模块
     */
    Module loadModule(String type, File moduleFile);
    
    /**
     * 卸载指定的模块
     * <p>
     * 若指定的模块未加载则直接return
     *
     * @param moduleName
     *     模块名
     */
    void unloadModule(String moduleName);
    
    /**
     * 查看一个模块是否已加载
     *
     * @param moduleName
     *     模块名
     *
     * @return 模块已加载返回true，未加载返回false
     */
    boolean isModuleLoaded(String moduleName);
    
    /**
     * 获取指定的模块
     *
     * @param moduleName
     *     模块名
     *
     * @return 指定的模块，若模块为加载则返回null
     */
    Module getModule(String moduleName);
    
    /**
     * 获取所有已加载的模块
     *
     * @return 已加载模块的集合，K->模块名，V->模块
     */
    Map<String, Module> getLoadedModules();
    
    /**
     * 注册模块加载器
     *
     * @param type
     *     该加载器所加载的模块的类型
     * @param loader
     *     要注册的模块加载器
     */
    void registerLoader(String type, ModuleLoader loader);
    
    /**
     * 反注册模块加载器
     *
     * @param type
     *     模块类型
     *     要反注册的模块加载器所加载的模块的类型
     */
    void unregisterLoader(String type);
}
