package org.yunshanmc.epicguild.command;

import org.bukkit.command.CommandSender;

/**
 * 命令拦截器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年8月7日
 */
public abstract class CommandInterceptor {
    
    /**
     * 对拦截到的命令进行处理
     * 
     * @param sender
     *            命令发送者
     * @param args
     *            用户输入的命令参数
     * @param state 当前的拦截状态(true为允许命令执行，false为不允许)
     * @return {@link InterceptorResult 命令拦截器的处理结果}
     */
    public abstract InterceptorResult handle(CommandSender sender, String[] args, boolean state);
}
