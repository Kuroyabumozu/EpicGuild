package org.yunshanmc.epicguild.command;

import org.bukkit.command.CommandSender;
import org.yunshanmc.epicguild.module.Module;
import org.yunshanmc.epicguild.module.ModuleManager;
import org.yunshanmc.ycl.command.simple.SimpleCommandManager;
import org.yunshanmc.ycl.message.Messager;

import java.util.Arrays;

/**
 * EpicGuild专用的命令管理器
 * <p>
 * 作者：YunShan <br>
 * 创建日期：2016年6月30日
 */
public class EGCommandManager extends SimpleCommandManager {
    
    // 命令权限
    private static final String CMD_PERM = "epicguild.command.";
    
    private ModuleManager moduleManager;
    private EGArgConverterManager argConverterManager = new EGArgConverterManager();
    
    public EGCommandManager(ModuleManager moduleManager, Messager messager) {
        super(messager);
        this.moduleManager = moduleManager;
        this.argConverterManager = new EGArgConverterManager();
        super.setMainCommand("help");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String cmd;
        if (args.length > 0) {
            Module mod = this.moduleManager.getLoadedModules().get(args[0]);
            if (mod != null) {
                mod.getCommandManager().onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
                return true;
            } else {
                cmd = args[0];
            }
        } else {
            cmd = super.getMainCommand();
        }
        if (this.checkPermission(sender, CMD_PERM + cmd)) {
            super.onCommand(sender, command, label, args);
        } else {
            super.messager.info(sender, "command.noPerm", cmd);
        }
        return true;
    }
    
    /**
     * 检查权限
     *
     * @param sender
     *     要检查权限的CommandSender
     * @param perm
     *     要检查的权限
     *
     * @return true有权限，false没权限
     */
    private boolean checkPermission(CommandSender sender, String perm) {
        return sender.hasPermission(perm);
    }
    
    /**
     * 获取参数转换器
     *
     * @return 参数转换器
     */
    public EGArgConverterManager getArgConverterManager() {
        return this.argConverterManager;
    }
}
