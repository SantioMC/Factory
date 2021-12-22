package me.santio.factory.listeners;

import me.santio.factory.FactoryLib;
import me.santio.factory.events.FactoryBlockBreakEvent;
import me.santio.factory.events.FactoryBlockInteractEvent;
import me.santio.factory.events.FactoryBlockPlaceEvent;
import me.santio.factory.models.BlockTile;
import me.santio.factory.models.FactoryBlock;
import me.santio.factory.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class CustomBlockListener implements Listener {
    
    private final ArrayList<UUID> cooldown = new ArrayList<>();
    
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.getItemMeta() == null) return;
        
        String id = NBTUtils.getCustomItem(item.getItemMeta());
        if (id == null) return;
        
        event.setCancelled(true);
        FactoryBlock block = FactoryLib.getBlocks().get(id);
        if (block == null) return;
    
        BlockTile tile = new BlockTile(event.getBlock().getLocation(), block);
    
        FactoryBlockPlaceEvent placeEvent = new FactoryBlockPlaceEvent(event.getBlockPlaced().getLocation(), tile);
        Bukkit.getPluginManager().callEvent(placeEvent);
        
        if (placeEvent.isCancelled()) tile.delete();
        else if (event.getPlayer().getGameMode() != GameMode.CREATIVE) item.setAmount(item.getAmount() - 1);
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        BlockTile tile = FactoryLib.getTiles().get(event.getBlock().getLocation());
        if (tile == null) return;
        
        event.setCancelled(true);
    
        FactoryBlockBreakEvent breakEvent = new FactoryBlockBreakEvent(event.getBlock().getLocation(), tile);
        Bukkit.getPluginManager().callEvent(breakEvent);
        
        if (!breakEvent.isCancelled()) {
            tile.delete();
    
            World world = event.getBlock().getWorld();
            if (tile.getBlock().getDropItem() != null)
                world.dropItem(tile.getLocation().clone().add(0.5, 0.5, 0.5), tile.getBlock().getDropItem());
        }
    }
    
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;
        
        BlockTile tile = FactoryLib.getTiles().get(event.getClickedBlock().getLocation());
        if (tile == null) return;
        
        event.setCancelled(true);
    
        if (cooldown.contains(event.getPlayer().getUniqueId())) return;
        cooldown.add(event.getPlayer().getUniqueId());
        
        FactoryBlockInteractEvent interactEvent = new FactoryBlockInteractEvent(event.getPlayer(), tile);
        Bukkit.getPluginManager().callEvent(interactEvent);
    
        new BukkitRunnable() {
            @Override public void run() { cooldown.remove(event.getPlayer().getUniqueId()); }
        }.runTaskLater(FactoryLib.getInstance(), 2);
    }
    
    @EventHandler
    public void onTileDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) event.getEntity();
        
        if (NBTUtils.getBlockTile(armorStand) != null) event.setCancelled(true);
    }
    
    
    
}
