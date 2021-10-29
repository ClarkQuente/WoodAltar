package me.clarkquente.woodaltar.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.clarkquente.woodaltar.api.events.controller.EventController;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class AltarDamageEvent extends EventController {

    private Player player;
    private Altar altar;

}
