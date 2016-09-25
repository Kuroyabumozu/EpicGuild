package org.yunshanmc.epicguild.module;

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
    
    protected String name;
    protected String version;
    
    protected String description;
    
    protected List<String> depend;
    protected List<String> softDepend;
    
    public String getName() {
        return this.name;
    }
    
    public String getVersion() {
        return this.version;
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
    
}
