package me.clarkquente.woodaltar.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.models.Altar;
import me.clarkquente.woodaltar.utils.LocationSerializer;
import me.clarkquente.woodaltar.utils.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AltarManager {

    @Getter private List<Altar> altarList;

    public AltarManager() throws IOException {
        this.altarList = new ArrayList<>();
        saveFromConfiguration();
    }

    public void add(Altar altar) {
        if(getAltar(altar.getLocation()) == null) altarList.add(altar);
    }

    public Altar getAltar(Location location) {
        return altarList.stream().filter(alt -> alt.getLocation() == location).findFirst().orElse(null);
    }

    public Altar getAltar(String id) {
        return altarList.stream().filter(alt -> alt.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public void remove(Altar altar) {
        altarList.remove(altar);
    }

    public void disable() {
        for(Altar altar : altarList) {
            if(altar.isAlive()) {
                altar.getEnderCrystal().remove();
                altar.getHologram().delete();
            }
        }

        saveInConfiguration();
    }

    @SneakyThrows
    public void saveInConfiguration() {
        File file = new File(WoodAltar.getInstance().getDataFolder(), "altares.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("Altares", "");
        config.createSection("Altares");
        config.save(file);

        for(Altar altar : altarList) {

            config.set("Altares." + altar.getId() + ".Location", LocationSerializer.getStringFromLocation(altar.getLocation()));
            config.set("Altares." + altar.getId() + ".MaxHealth", altar.getMaxHealth());
            config.set("Altares." + altar.getId() + ".Minutes", altar.getMinutesToRespawn());
            config.set(
                    "Altares." + altar.getId() + ".Items", !altar.getRewards().isEmpty() ?
                            Serializer.itemStackArrayToBase64(altar.getRewards().toArray(new ItemStack[0])) : "Sem Itens"
            );
            config.set("Altares." + altar.getId() + ".AutomaticallyStart", altar.isAutomaticallyStart());
            config.save(file);
        }
    }

    private void saveFromConfiguration() throws IOException {
        File file = new File(WoodAltar.getInstance().getDataFolder(), "altares.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(String key : config.getConfigurationSection("Altares").getKeys(false)) {
            Bukkit.getConsoleSender().sendMessage("§b" + key + " passou");

            Location location = LocationSerializer.getLocationFromString(config.getString("Altares." + key + ".Location"));
            double maxHealth = config.getDouble("Altares." + key + ".MaxHealth");
            int minutesToRespawn = config.getInt("Altares." + key + ".Minutes");
            List<ItemStack> rewards =
                    !config.getString("Altares." + key + ".Items").equalsIgnoreCase("Sem Itens") ?
                            Arrays.asList(Serializer.itemStackArrayFromBase64(config.getString("Altares." + key + ".Items"))) :
                            new ArrayList<>();
            boolean automaticallyStart = config.getBoolean("Altares." + key + ".AutomaticallyStart");

            Altar altar = new Altar(key, location, maxHealth, maxHealth, minutesToRespawn, rewards, false, automaticallyStart);
            add(altar);

            altar.createAltar();
        }
    }
}
