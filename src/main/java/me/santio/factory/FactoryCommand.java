package me.santio.factory;

import me.santio.factory.models.ItemContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FactoryCommand implements CommandExecutor, TabCompleter {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§b/factory §3help §7Opens this menu");
            sender.sendMessage("§b/factory §3get <item> §7Fetches you a item made by a mod");
        } else if (args[0].equalsIgnoreCase("get")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cThis command can only be used by players!");
                return true;
            }
            
            if (args.length < 2) {
                sender.sendMessage("§c/factory "+args[0]+" <item>");
                return true;
            }
    
            ItemContext item = FactoryLib.getItems().get(args[1]);
            if (item == null) item = FactoryLib.getBlocks().get(args[1]);
            if (item == null) {
                sender.sendMessage("§cThe item §n"+args[1]+"§c does not exist!");
                return true;
            }
            
            ((Player) sender).getInventory().addItem(item.generateItem());
            sender.sendMessage("§bFactory §8| §7You were successfully given a(n) §b"+item.getName());
            
        } else {
            sender.sendMessage("§cUnknown argument! Use §n/factory help§c for help!");
        }
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) return Arrays.asList("help", "get");
        if (args.length == 2) {
            switch(args[0].toLowerCase()) {
                case("help"):
                    break;
                case("get"):
                    ArrayList<String> ids = new ArrayList<>(FactoryLib.getItems().keySet());
                    ids.addAll(new ArrayList<>(FactoryLib.getBlocks().keySet()));
                    return ids;
            }
        }
        
        return Collections.emptyList();
    }
    
}
