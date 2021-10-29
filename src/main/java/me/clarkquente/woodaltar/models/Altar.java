package me.clarkquente.woodaltar.models;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.base.Preconditions;
import lombok.*;
import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.api.WoodAltarAPI;
import me.clarkquente.woodaltar.api.events.AltarDestroyEvent;
import me.clarkquente.woodaltar.configuration.GeneralValue;
import me.clarkquente.woodaltar.configuration.MessageValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Altar {

    @NonNull private final String id;
    @NonNull private Location location;
    @NonNull private double maxHealth, actualHealth;
    @NonNull private int minutesToRespawn;
    @NonNull private List<ItemStack> rewards;
    private Hologram hologram;
    @NonNull private boolean alive, automaticallyStart;

    private BukkitTask task;
    private EnderCrystal enderCrystal;
    private int lineHealth;
    private String textLineHealth;

    public void createAltar() {
        Preconditions.checkNotNull(getLocation());
        if(task != null) task.cancel();

        task = Bukkit.getScheduler().runTaskTimer(WoodAltar.getInstance(), () -> {

            if(isAlive()) return;
            setActualHealth(getMaxHealth());

            enderCrystal = (EnderCrystal) getLocation().getWorld().spawnEntity(new Location(
                    getLocation().getWorld(), getLocation().getX(),
                    getLocation().getWorld().getHighestBlockAt(getLocation()).getY() + 1.0D,
                    getLocation().getZ()), EntityType.ENDER_CRYSTAL);

            enderCrystal.setMetadata(
                    WoodAltarAPI.getInstance().getMetadataKey(), new FixedMetadataValue(WoodAltar.getInstance(), id)
            );

            hologram = HologramsAPI.createHologram(WoodAltar.getInstance(), enderCrystal.getLocation().add(0, 3.0D, 0));

            int i = 0;
            for(String line : GeneralValue.get(GeneralValue::hologram)) {
                hologram.insertTextLine(i, line
                        .replace("{vida}", String.valueOf(actualHealth))
                        .replace("{maximo-vida}", String.valueOf(maxHealth)));

                if(line.contains("{vida}")) {
                    setLineHealth(i);
                    setTextLineHealth(line);
                }
                i++;
            }

            setAlive(true);

        }, automaticallyStart ? 0 : getMinutesToRespawn() * 60L * 20L, getMinutesToRespawn() * 60L * 20L);
    }

    public void destroyAltar(Player player) {
        Preconditions.checkNotNull(getLocation());
        if(!isAlive()) return;

        AltarDestroyEvent altarDestroyEvent = new AltarDestroyEvent(player, this);
        Bukkit.getPluginManager().callEvent(altarDestroyEvent);
        if(altarDestroyEvent.isCancelled()) return;

        hologram.delete();
        enderCrystal.remove();

        getRewards().forEach(it -> {
            getLocation().getWorld().dropItemNaturally(getLocation(), it);
        });

        MessageValue.get(MessageValue::altarDestroyed).forEach(msg -> {
            Bukkit.broadcastMessage(msg.replace("{player}", player.getName()));
        });

        setAlive(false);
    }

    public void unspawnAltar() {
        if(!isAlive()) return;

        enderCrystal.remove();
        hologram.delete();

        setAlive(false);
    }

    public void damage(int damage) {
        setActualHealth(getActualHealth() - damage);

        hologram.getLine(getLineHealth()).removeLine();
        hologram.insertTextLine(getLineHealth(), getTextLineHealth()
                .replace("{vida}", String.valueOf(getActualHealth()))
                .replace("{maximo-vida}", String.valueOf(getMaxHealth())));
    }
}
