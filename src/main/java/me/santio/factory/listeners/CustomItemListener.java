package me.santio.factory.listeners;

import me.santio.factory.events.FactoryItemUseEvent;
import me.santio.factory.models.FactoryItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemListener implements Listener {
    
    @EventHandler
    private void onRightClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                || event.getItem() == null) return;
        
        FactoryItem item = FactoryItem.fromItemStack(event.getItem());
        if (item == null) return;
    
        FactoryItemUseEvent factoryItemUseEvent = new FactoryItemUseEvent(event.getPlayer(), item);
        Bukkit.getPluginManager().callEvent(factoryItemUseEvent);
    }
    
}
