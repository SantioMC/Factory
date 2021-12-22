package me.santio.factory.commands;

import me.santio.factory.FactoryLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

public class ModCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§3Factory Mods §7(" + FactoryLib.getMods().size() + "): §b" +
                FactoryLib.getMods().values()
                        .stream()
                        .map(m -> m.getDescription().getName())
                        .collect(Collectors.joining("§7, §b")));
        return true;
    }
    
}
