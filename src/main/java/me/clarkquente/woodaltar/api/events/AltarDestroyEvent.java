package me.clarkquente.woodaltar.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class AltarDestroyEvent extends Event implements Cancellable {

    private final Player player;
    private final Altar altar;
    private boolean cancelled;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
