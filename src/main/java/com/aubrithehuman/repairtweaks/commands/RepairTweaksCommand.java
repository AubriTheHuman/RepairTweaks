package com.aubrithehuman.repairtweaks.commands;

import com.aubrithehuman.repairtweaks.RepairTweaks;
import com.aubrithehuman.repairtweaks.data.CustomToolsHolder;
import com.aubrithehuman.repairtweaks.utilities.ChatUtil;
import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class RepairTweaksCommand {
    public RepairTweaksCommand() {
        new CommandAPICommand("repairtweaks")
            .withFullDescription("List info about the plugin.")
            .withShortDescription("List info about the plugin.")
            .withPermission("repairtweaks.admin")
            .withSubcommands(
                new CommandAPICommand("info")
                    .executes(this::info),
                new CommandAPICommand("tweaks")
                    .executes(this::tweaks),
                new CommandAPICommand("reload")
                    .executes(this::reload)
            )
            .executes(this::info)
            .register();
    }

    private void info(CommandSender sender, CommandArguments args) {
        sender.sendMessage(
            new ColorParser("%s RepairTweaks v<version> by AubriTheHuman.".formatted(ChatUtil.PREFIX))
                .parseMinimessagePlaceholder("version", RepairTweaks.getInstance().getPluginMeta().getVersion())
                .build()
        );
    }

    private void tweaks(CommandSender sender, CommandArguments args) {
        final StringBuilder msg = new StringBuilder("%s Custom repair combinations:".formatted(ChatUtil.PREFIX));

        for (Material m : CustomToolsHolder.getInstance().getCustomTools().keySet()) {
            msg.append("\n <grey>- <#7CD36E>%s <grey>+ <#7CD36E>%s".formatted(m.name(), CustomToolsHolder.getInstance().getCustomTools().get(m).name()));
        }

        sender.sendMessage(
            new ColorParser(msg.toString()).build()
        );
    }

    private void reload(CommandSender sender, CommandArguments args) {
        CustomToolsHolder.getInstance().loadCustomRepairRecipes();

        sender.sendMessage(
            new ColorParser("%s Reloaded config.".formatted(ChatUtil.PREFIX)).build()
        );
    }
}
