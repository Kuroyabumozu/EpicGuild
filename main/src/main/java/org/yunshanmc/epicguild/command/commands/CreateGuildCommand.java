package org.yunshanmc.epicguild.command.commands;

import org.bukkit.entity.Player;
import org.yunshanmc.epicguild.command.EGCommand;
import org.yunshanmc.epicguild.guildmember.GuildMember;
import org.yunshanmc.ycl.command.simple.ArgConverterManager;

/**
 * 创建公会命令
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年7月30日
 */
public class CreateGuildCommand extends EGCommand {
    
    public CreateGuildCommand(ArgConverterManager argConverterManager) {
        super("create", argConverterManager);
    }
    
    @CommandHandler(needSender = true)
    public boolean createGuild(Player sender, String guildName) {
        GuildMember member = super.getGuildMember(sender);
        return true;
    }
}
