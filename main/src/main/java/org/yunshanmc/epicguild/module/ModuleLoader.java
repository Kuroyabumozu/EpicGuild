package org.yunshanmc.epicguild.module;

import java.io.File;

/**
 * 模块加载器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public interface ModuleLoader {
    
    /**
     * 加载指定模块
     *
     * @param moduleFile
     *     要加载的模块文件
     *
     * @return 加载好的模块，若加载失败则返回null
     */
    Module loadModule(File moduleFile) throws InvalidModuleException;
    
    /**
     * 卸载指定模块
     *
     * @param name
     *     要卸载的模块名
     */
    void unloadModule(String name);
}
