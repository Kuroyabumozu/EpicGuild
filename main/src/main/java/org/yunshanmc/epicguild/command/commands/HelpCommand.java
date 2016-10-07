package org.yunshanmc.epicguild.command.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.yunshanmc.epicguild.EpicGuildAPI;
import org.yunshanmc.epicguild.command.EGCommand;
import org.yunshanmc.epicguild.command.EGCommandManager;
import org.yunshanmc.epicguild.util.Util_Bukkit;
import org.yunshanmc.ycl.command.BaseCommandManager;
import org.yunshanmc.ycl.command.Command;
import org.yunshanmc.ycl.command.simple.ArgConverterManager;
import org.yunshanmc.ycl.utils.reflect.ReflectionUtils;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助命令
 * <p>
 * 作者： Yun-Shan <br>
 * 创建日期： 2016/10/2.
 */
public class HelpCommand extends EGCommand {
    
    private static final int PAGE_ITEMS_COUNT = 10;// 每页数量
    
    private boolean inited = false;
    private List<Map.Entry<String, Object[]>> helps;
    
    public HelpCommand(ArgConverterManager argConverterManager) {
        super("help", argConverterManager);
    }
    
    @CommandHandler(needSender = true)
    public void showHelp(CommandSender sender, @OptionalArgStart int page, boolean refreshCache) {
        if (refreshCache && !Util_Bukkit.checkPermission(sender, "help.refreshCache")) {
            super.messager.info(sender, "help.noRefreshPerm");
            return;
        }
        if (!this.inited) init();
        int allPage = this.helps.size() / PAGE_ITEMS_COUNT;
        if (allPage == 0) allPage = 1;
        if (page <= 0) {
            page = 1;
        } else if (page > allPage) {
            page = allPage;
        }
        super.messager.info(sender, "help.head", page, allPage);
        int start = (page - 1) * PAGE_ITEMS_COUNT;
        int end = start + PAGE_ITEMS_COUNT;
        if (end > this.helps.size()) end = this.helps.size();
        for (; start < end; start++) {
            Map.Entry<String, Object[]> help = this.helps.get(start);
            super.messager.info(sender, help.getKey(), help.getValue());
        }
        super.messager.info(sender, "help.foot", page, allPage);
    }
    
    @SuppressWarnings("unchecked")
    private void init() {
        EGCommandManager commandManager = EpicGuildAPI.getCommandManager();
        Map<String, Command> commands = (Map<String, Command>) ReflectionUtils.getFieldValue(BaseCommandManager.class,
                                                                                             "commands",
                                                                                             commandManager);
        Map<String, Map<String, Command>> subCommands = (Map<String, Map<String, Command>>) ReflectionUtils.getFieldValue(
            BaseCommandManager.class, "subCommands", commandManager);
        
        List<Map.Entry<String, Command>> helps = Lists.newArrayList();
        Iterables.all(commands.values(), cmd -> {
            helps.add(new AbstractMap.SimpleEntry(cmd.getName(), cmd));// 添加根命令
            Map<String, Command> subs = subCommands.get(cmd.getName());
            if (subs != null && !subs.isEmpty()) {// 若有子命令则添加子命令
                Iterables.all(subs.values(), sub -> {
                    helps.add(new AbstractMap.SimpleEntry(cmd.getName() + " " + sub.getName(), sub));
                    return true;
                });
            }
            return true;
        });
        this.helps = Lists.transform(helps, entry -> {
            String key = "help.command." + entry.getKey().replace(' ', '.');
            Command cmd = entry.getValue();
            Object[] args = new Object[] { entry.getKey(), cmd.getUsage(), cmd.getDescription() };
            return new AbstractMap.SimpleEntry(key, args);
        });
        this.inited = true;
    }
}
