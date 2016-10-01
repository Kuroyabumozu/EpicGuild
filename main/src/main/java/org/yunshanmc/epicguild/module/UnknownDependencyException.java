package org.yunshanmc.epicguild.module;

import org.yunshanmc.epicguild.util.EpicGuildException;

/**
 * 未知的依赖异常
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class UnknownDependencyException extends EpicGuildException {
    
    /**
     * @param loadModule 要加载的模块
     * @param depend 导致异常抛出的未知的依赖
     */
    public UnknownDependencyException(String loadModule, String depend) {
        this(null, loadModule, depend);
    }
    
    /**
     * @param cause cause
     * @param loadModule 要加载的模块
     * @param depend 导致异常抛出的未知的依赖
     */
    public UnknownDependencyException(Throwable cause, String loadModule, String depend) {
        super("module.load.unknownDependency", loadModule, depend);
    }
}
