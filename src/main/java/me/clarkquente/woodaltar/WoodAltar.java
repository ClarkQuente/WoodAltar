package me.clarkquente.woodaltar;

import lombok.Getter;
import lombok.SneakyThrows;
import me.clarkquente.woodaltar.commands.AltarCommand;
import me.clarkquente.woodaltar.configuration.registry.ConfigurationRegistry;
import me.clarkquente.woodaltar.hooks.HologramHook;
import me.clarkquente.woodaltar.listeners.AltarInventoryListener;
import me.clarkquente.woodaltar.listeners.AltarListener;
import me.clarkquente.woodaltar.managers.AltarManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class WoodAltar extends JavaPlugin {

    @Getter private static AltarManager altarManager;

    @SneakyThrows
    @Override
    public void onEnable() {

        saveDefaultConfig();
        ConfigurationRegistry.of(this).register();
        createAltaresConfiguration();

        if(!HologramHook.check()) {
            getLogger().info("HolographicDisplays not found, disabling plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        altarManager = new AltarManager();

        getCommand("altar").setExecutor(new AltarCommand());
        Bukkit.getPluginManager().registerEvents(new AltarListener(), this);
        Bukkit.getPluginManager().registerEvents(new AltarInventoryListener(), this);

        getLogger().info("The plugin has been started.");
    }

    @Override
    public void onDisable() {
        getAltarManager().disable();
    }

    private void createAltaresConfiguration() {
        File file = new File(getDataFolder(), "altares.yml");
        YamlConfiguration altaresConfiguration = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            try {
                file.createNewFile();

                altaresConfiguration.createSection("Altares");
                altaresConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAltaresConfiguration() {
        File file = new File(getDataFolder(), "altares.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WoodAltar getInstance() {
        return getPlugin(WoodAltar.class);
    }
}
