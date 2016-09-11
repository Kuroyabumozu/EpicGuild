package org.yunshanmc.epicguild.module;

import java.util.Collection;
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
     * @param moduleName
     *        模块名
     * @return 加载好的模块
     */
    Module loadModule(String moduleName);

    /**
     * 卸载指定的模块
     * <p>
     * 若指定的模块未加载则直接return
     * 
     * @param moduleName
     *        模块名
     */
    void unloadModule(String moduleName);

    /**
     * 查看一个模块是否已加载
     *
     * @param moduleName
     *        模块名
     * @return 模块已加载返回true，未加载返回false
     */
    boolean isModuleLoaded(String moduleName);

    /**
     * 获取指定的已加载的模块
     *
     * @param moduleName
     *        模块名
     * @return 指定的模块
     */
    Module getLoadedModule(String moduleName);

    /**
     * 获取所有已加载的模块
     * 
     * @return 已加载模块的集合，K->模块名，V->模块
     */
    Map<String, Module> getLoadedModules();
}
