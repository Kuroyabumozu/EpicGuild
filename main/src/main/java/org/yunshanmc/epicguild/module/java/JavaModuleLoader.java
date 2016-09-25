package org.yunshanmc.epicguild.module.java;

import org.bukkit.configuration.file.YamlConfiguration;
import org.yunshanmc.epicguild.module.InvalidDescriptionException;
import org.yunshanmc.epicguild.module.InvalidModuleException;
import org.yunshanmc.epicguild.module.ModuleLoader;
import org.yunshanmc.epicguild.module.ModuleManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 模块加载器
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class JavaModuleLoader implements ModuleLoader {
    
    private final File          folder;
    private final ModuleManager moduleManager;
    
    public JavaModuleLoader(File folder, ModuleManager moduleManager) {
        this.folder = folder;
        this.moduleManager = moduleManager;
    }
    
    @Override
    public JavaModule loadModule(File moduleFile) throws InvalidModuleException {
        JavaModuleDescription des;
        try {
            des = getModuleDescription(moduleFile);
        } catch (InvalidDescriptionException e) {
            throw new InvalidModuleException(e, "invalidDescription");
        }
        for (String depend : des.getDepend()) {
            if (!this.moduleManager.isModuleLoaded(depend)) {
                throw new InvalidModuleException("unknownDependency", depend);
            }
        }
        
        //TODO JavaPluginLoader line 131
        return null;
    }
    
    @Override
    public void unloadModule(String name) {
        
    }
    
    private static JavaModuleDescription getModuleDescription(File moduleFile) throws InvalidDescriptionException {
        try {
            JarFile jar = new JarFile(moduleFile);
            JarEntry entry = jar.getJarEntry("module.yml");
            if (entry == null) {
                throw new InvalidDescriptionException("missingDescription");
            }
            InputStream stream = jar.getInputStream(entry);
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(
                new InputStreamReader(stream, StandardCharsets.UTF_8));
            return JavaModuleDescription.loadFromYaml(yml);
        } catch (IOException e) {
            throw new InvalidDescriptionException(e, "invalidFile");
        }
    }
}
