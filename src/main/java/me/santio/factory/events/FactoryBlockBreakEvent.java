package me.santio.factory.events;

import lombok.Getter;
import lombok.Setter;
import me.santio.factory.models.BlockTile;
import me.santio.factory.models.FactoryBlock;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactoryBlockBreakEvent extends Event implements Cancellable {
    @Setter @Getter private boolean isCancelled;
    @Getter private static final HandlerList handlerList = new HandlerList();
    @Getter private final Location location;
    @Getter private final BlockTile tile;
    
    public FactoryBlockBreakEvent(Location location, BlockTile tile) {
        this.location = location;
        this.tile = tile;
        this.isCancelled = false;
    }
    
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
