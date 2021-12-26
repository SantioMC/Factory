package me.santio.factory.events;

import lombok.Getter;
import me.santio.factory.models.BlockTile;
import me.santio.factory.models.FactoryItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactoryItemUseEvent extends Event {
    @Getter private static final HandlerList handlerList = new HandlerList();
    @Getter private final Player player;
    @Getter private final FactoryItem item;
    
    public FactoryItemUseEvent(Player player, FactoryItem item){
        this.player = player;
        this.item = item;
    }
    
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
