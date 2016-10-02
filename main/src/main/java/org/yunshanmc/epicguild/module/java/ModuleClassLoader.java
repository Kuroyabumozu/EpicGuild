package org.yunshanmc.epicguild.module.java;

import com.google.common.collect.Maps;
import org.yunshanmc.epicguild.module.InvalidModuleException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * 模块类加载器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
final class ModuleClassLoader extends URLClassLoader {
    
    private final JavaModule module;
    
    private final JavaModuleDescription description;
    
    private final Map<String, Class<?>> classes = Maps.newConcurrentMap();
    
    public ModuleClassLoader(JavaModuleDescription description, File moduleFile, ClassLoader parent)
        throws InvalidModuleException, MalformedURLException {
        super(new URL[] { moduleFile.toURI().toURL() }, parent);
        this.description = description;
        
        try {
            Class<? extends JavaModule> mainClass;
            Class<?> cls = Class.forName(description.getName(), true, this);
            mainClass = cls.asSubclass(JavaModule.class);
            this.module = mainClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new InvalidModuleException(e, "mainClass.NotFound", description.getMain());
        } catch (ClassCastException e) {
            throw new InvalidModuleException(e, "mainClass.NotExtendJavaModule", description.getMain());
        } catch (InstantiationException e) {
            throw new InvalidModuleException(e, "mainClass.InstantiationFail", description.getMain(), e.getMessage());
        } catch (IllegalAccessException e) {
            throw new InvalidModuleException(e, "mainClass.NoPublicConstructor", description.getMain());
        }
    }
    
    JavaModule getModule() {
        return this.module;
    }
}
