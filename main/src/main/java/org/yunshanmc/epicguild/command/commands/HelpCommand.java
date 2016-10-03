package org.yunshanmc.epicguild.command.commands;

import org.bukkit.command.CommandSender;
import org.yunshanmc.epicguild.command.EGCommand;
import org.yunshanmc.ycl.command.simple.ArgConverterManager;

/**
 * 帮助命令
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/10/2.
 */
public class HelpCommand extends EGCommand {
    
    public HelpCommand(ArgConverterManager argConverterManager) {
        super("help", argConverterManager);
    }
    
    @CommandHandler(needSender = true)
    public void showHelp(CommandSender sender, @OptionalArgStart int page) {
        
    }
}
