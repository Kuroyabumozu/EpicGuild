package org.yunshanmc.epicguild.module;

import com.google.common.collect.Lists;
import org.yunshanmc.ycl.config.ReadOnlyConfiguration;

import java.util.List;

/**
 * 模块描述
 * <p>
 * <p>
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/9/25.
 */
public class ModuleDescription {
    
    protected String type;
    protected String name;
    protected String description;
    
    protected List<String> depend;
    protected List<String> softDepend;
    
    /**
     * 从配置中加载模块描述
     *
     * @param cfg
     *     读取模块描述的配置
     *
     * @return 加载好的模块描述
     *
     * @throws InvalidDescriptionException
     *     模块描述不符合要求时抛出
     */
    public static ModuleDescription loadFromConfiguration(ReadOnlyConfiguration cfg)
        throws InvalidDescriptionException {
        ModuleDescription des = new ModuleDescription();
        
        // 读取模块名
        String name = cfg.getString("name");
        if (name == null) {
            throw new InvalidDescriptionException("notDefineName", "Unknown");
        } else if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
            throw new InvalidDescriptionException("invalidName", name);
        }
        des.name = name.replace(' ', '_');
        
        // 读取模块类型，默认为类型为Java
        String type = cfg.getString("type", "Java");
        if (!type.matches("^[A-Za-z]+$")) {
            throw new InvalidDescriptionException("UnknownType", name, type);
        }
        des.name = type.toLowerCase();
        
        // 不强制要求存在的属性
        des.description = cfg.getString("description");
        des.depend = Lists.transform(cfg.getStringList("depend"), n -> n.replace(' ', '_'));
        des.softDepend = Lists.transform(cfg.getStringList("softDepend"), n -> n.replace(' ', '_'));
        
        return des;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public List<String> getDepend() {
        return this.depend;
    }
    
    public List<String> getSoftDepend() {
        return this.softDepend;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (!(obj instanceof ModuleDescription)) return false;
        return this.name.equals(((ModuleDescription) obj).name);
    }
    
}
