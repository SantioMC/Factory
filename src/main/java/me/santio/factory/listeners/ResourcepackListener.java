package me.santio.factory.listeners;

import me.santio.factory.Factory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcepackListener implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Factory.sendResource(event.getPlayer());
    }
    
    @EventHandler
    public void onResourcePackResponse(PlayerResourcePackStatusEvent event) {
        PlayerResourcePackStatusEvent.Status status = event.getStatus();
        if (status == PlayerResourcePackStatusEvent.Status.DECLINED) event.getPlayer().kickPlayer("§cYou must accept the resource pack to play!");
        else if (status == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) event.getPlayer().sendMessage("§cFailed to download resource pack! Try again later.");
    }
    
}
