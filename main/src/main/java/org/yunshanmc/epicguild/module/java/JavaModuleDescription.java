package org.yunshanmc.epicguild.module.java;

import org.yunshanmc.epicguild.module.InvalidDescriptionException;
import org.yunshanmc.epicguild.module.ModuleDescription;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

/**
 * 模块的描述
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class JavaModuleDescription extends ModuleDescription {
    
    private String main;
    
    /**
     * 从配置中加载模块描述
     *
     * @param cfg 读取模块描述的配置
     *
     * @return 加载好的模块描述
     *
     * @throws InvalidDescriptionException 模块描述不符合要求时抛出
     */
    public static JavaModuleDescription loadFromConfiguration(ReadOnlyConfiguration cfg) throws InvalidDescriptionException {
        JavaModuleDescription des = new JavaModuleDescription();
        ModuleDescription baseDes = ModuleDescription.loadFromConfiguration(cfg);
    
        // 读取Java模块主类
        String main = cfg.getString("main");
        if (main == null) {
            throw new InvalidDescriptionException("notDefineMain", des.name);
        }
        des.main = main;
        
        des.name = baseDes.getName();
        des.description = baseDes.getDescription();
        des.depend = baseDes.getDepend();
        des.softDepend = baseDes.getSoftDepend();
        
        return des;
    }
    
    public String getMain() {
        return this.main;
    }
    
}
