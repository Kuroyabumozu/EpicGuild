package org.yunshanmc.epicguild.command;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.epicguild.guild.Guild;
import org.yunshanmc.epicguild.guildmember.GuildMember;
import org.yunshanmc.ycl.command.simple.ArgConverterManager;
import org.yunshanmc.ycl.command.simple.SimpleCommand;

import java.util.List;

/**
 * EpicGuild命令 <br>
 * 包装了一些快捷方法
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月30日
 */
public abstract class EGCommand extends SimpleCommand {
    
    private List<CommandInterceptor> interceptors = Lists.newLinkedList();
    
    public EGCommand(String name, ArgConverterManager argConverterManager) {
        super(name, argConverterManager);
    }
    
    // 添加拦截动作
    @Override
    protected void executeCommand(CommandSender sender, String... args) {
        boolean canExecute = true;
        for (CommandInterceptor interceptor : this.interceptors) {
            InterceptorResult res = interceptor.handle(sender, args, canExecute);
            switch (res) {
                case ALLOW: {
                    canExecute = true;
                    break;
                }
                case DENY: {
                    canExecute = false;
                    break;
                }
                case STOP: {
                    return;
                }
                case COUTINUE:
                default:
                    break;
            }
        }
        super.executeCommand(sender, args);
    }
    
    /**
     * 添加命令拦截器
     * 
     * @param interceptor
     *            要添加的命令拦截器
     */
    public void addInterceptor(CommandInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
    
    /**
     * 移除命令拦截器
     * 
     * @param interceptor
     *            要移除的命令拦截器
     */
    public void removeInterceptor(CommandInterceptor interceptor) {
        this.interceptors.remove(interceptor);
    }
    
    /**
     * 重载命令
     * @return 是否重载成功(true成功)
     */
    public boolean reload() {
        return true;
    }
    
    /**
     * 代理方法
     * 
     * @see org.yunshanmc.epicguild.guild.GuildManager#getGuild(String)
     */
    protected Guild getGuild(String name) {
        return EpicGuildAPI.getGuildManager().getGuild(name);
    }
    
    /**
     * 代理方法
     * 
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberManager#getGuildMember(String)
     */
    protected GuildMember getGuildMember(String name) {
        return EpicGuildAPI.getGuildMemberManager().getGuildMember(name);
    }
    
    /**
     * 代理方法<br>
     * 此方法不会返回null
     * 
     * @see org.yunshanmc.epicguild.guildmember.GuildMemberManager#getGuildMember(org.bukkit.OfflinePlayer)
     */
    protected GuildMember getGuildMember(Player player) {
        return EpicGuildAPI.getGuildMemberManager().getGuildMember(player);
    }
}
