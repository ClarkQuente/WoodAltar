package me.clarkquente.woodaltar.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.clarkquente.woodaltar.api.events.controller.EventController;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
@AllArgsConstructor
public class AltarDestroyEvent extends EventController implements Cancellable {

    private final Player player;
    private final Altar altar;

}
