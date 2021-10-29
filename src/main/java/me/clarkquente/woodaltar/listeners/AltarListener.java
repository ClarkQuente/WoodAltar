package me.clarkquente.woodaltar.listeners;

import lombok.val;
import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.api.WoodAltarAPI;
import me.clarkquente.woodaltar.api.events.AltarDamageEvent;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AltarListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof EnderCrystal && e.getDamager() instanceof Player) {

            EnderCrystal enderCrystal = (EnderCrystal) e.getEntity();
            Player player = (Player) e.getDamager();

            if(enderCrystal.hasMetadata(WoodAltarAPI.getInstance().getMetadataKey())) {
                e.setCancelled(true);

                Altar altar = WoodAltarAPI.getInstance().getAltar(
                        enderCrystal.getMetadata(WoodAltarAPI.getInstance().getMetadataKey()).get(0).asString()
                );

                if(altar == null) return;

                AltarDamageEvent altarDamageEvent = new AltarDamageEvent(player, altar);
                Bukkit.getPluginManager().callEvent(altarDamageEvent);
                if(altarDamageEvent.isCancelled()) return;

                double damage = e.getFinalDamage();

                if(altar.getActualHealth() - (int) damage <= 0) altar.destroyAltar(player);
                else altar.damage((int) damage);
            }
        }
    }
}