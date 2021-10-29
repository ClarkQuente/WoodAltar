package me.clarkquente.woodaltar.listeners;

import me.clarkquente.woodaltar.api.WoodAltarAPI;
import me.clarkquente.woodaltar.configuration.MessageValue;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AltarInventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getPlayer() instanceof Player) {

            Player p = (Player) e.getPlayer();

            if(e.getView().getTitle().startsWith("§8Recompensas do Altar: ")) {
                Inventory inventory = e.getInventory();

                int i = 0;
                for(ItemStack item : inventory.getContents())
                    if(item == null || item.getType() == Material.AIR)
                        i++;

                boolean isEmpty = i == inventory.getSize();
                if(isEmpty) return;

                String altarId = e.getView().getTitle().split(":")[1].replace(" ", "");
                Altar altar = WoodAltarAPI.getInstance().getAltar(altarId);
                if(altar == null) return;

                List<ItemStack> items = new ArrayList<>();
                Arrays.stream(inventory.getContents()).forEach(it -> {
                    if(it != null && it.getType() != Material.AIR) items.add(it);
                });

                altar.setRewards(items);
                p.sendMessage(MessageValue.get(MessageValue::rewardsChanged));
            }
        }
    }
}
