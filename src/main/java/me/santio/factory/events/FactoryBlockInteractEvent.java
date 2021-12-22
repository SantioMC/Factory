package me.santio.factory.events;

import lombok.Getter;
import lombok.Setter;
import me.santio.factory.models.BlockTile;
import me.santio.factory.models.FactoryBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactoryBlockInteractEvent extends Event {
    @Getter private static final HandlerList handlerList = new HandlerList();
    @Getter private final Player player;
    @Getter private final BlockTile tile;
    
    public FactoryBlockInteractEvent(Player player, BlockTile tile){
        this.player = player;
        this.tile = tile;
    }
    
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
