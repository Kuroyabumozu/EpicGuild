package org.yunshanmc.epicguild.module.java;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 模块类加载器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class ModuleClassLoader extends URLClassLoader {
    
    public ModuleClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
}
