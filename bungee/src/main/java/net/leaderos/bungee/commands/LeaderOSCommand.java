package net.leaderos.bungee.commands;

import net.leaderos.bungee.Bungee;
import net.leaderos.bungee.helper.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author poyrazinan
 * @since 1.0
 */
public class LeaderOSCommand extends Command {

    /**
     * Constructor of author command
     * @param name of command
     */
    public LeaderOSCommand(String name) {
        super(name);
    }

    /**
     * Default command of auth
     * @param sender executor
     * @param args command args
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equals("reload")) {
            if (sender.hasPermission("leaderos.reload")) {
                Bungee.getInstance().getConfigFile().load(true);
                Bungee.getInstance().getLangFile().load(true);
                Bungee.getInstance().getModulesFile().load(true);
                Bungee.getModuleManager().reloadModules();
                ChatUtil.sendMessage(sender, "{prefix} &aPlugin reloaded successfully.");
            }
            else
                ChatUtil.sendMessage(sender, Bungee.getInstance().getLangFile().getMessages().getCommand().getNoPerm());
        }
        else
            ChatUtil.sendMessage(sender, Bungee.getInstance().getLangFile().getMessages().getCommand().getInvalidArgument());
    }
}