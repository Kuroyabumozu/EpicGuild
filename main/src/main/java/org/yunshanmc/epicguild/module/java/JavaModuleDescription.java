package org.yunshanmc.epicguild.module.java;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yunshanmc.epicguild.module.InvalidDescriptionException;
import org.yunshanmc.epicguild.module.ModuleDescription;

import java.util.List;

/**
 * 模块的描述
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class JavaModuleDescription extends ModuleDescription {
    
    private String main;
    
    /**
     * 从YAML中加载模块描述
     *
     * @param yml 读取模块描述的YAML
     *
     * @return 加载好的模块描述
     *
     * @throws InvalidDescriptionException 模块描述不符合要求时抛出
     */
    public static JavaModuleDescription loadFromYaml(YamlConfiguration yml) throws InvalidDescriptionException {
        JavaModuleDescription des = new JavaModuleDescription();
        
        // 读取模块名
        String name = yml.getString("name");
        if (name == null) {
            throw new InvalidDescriptionException("notDefineName");
        } else if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
            throw new InvalidDescriptionException("invalidName", name);
        }
        des.name = name.replace(' ', '_');
        
        // 读取模块版本
        String version = yml.getString("version");
        if (version == null) {
            throw new InvalidDescriptionException("notDefineVersion");
        }
        des.version = version;
        
        // 读取Java模块主类
        String main = yml.getString("main");
        if (main == null) {
            throw new InvalidDescriptionException("notDefineMain");
        }
        des.main = main;
        
        // 不强制要求存在的属性
        des.description = yml.getString("description");
        des.depend = makeModuleNameList(yml.getStringList("depend"));
        des.softDepend = makeModuleNameList(yml.getStringList("softDepend"));
        
        return des;
    }
    
    private static List<String> makeModuleNameList(List<String> rawList) {
        if (rawList.isEmpty()) return rawList;
        List<String> list = Lists.newArrayListWithCapacity(rawList.size());
        for (String name : rawList) {
            list.add(name.replace(' ', '_'));
        }
        return list;
    }
    
    public String getMain() {
        return this.main;
    }
    
}
