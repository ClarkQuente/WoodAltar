package me.clarkquente.woodaltar.configuration.registry;

import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Data;
import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.configuration.GeneralValue;
import me.clarkquente.woodaltar.configuration.MessageValue;

@Data(staticConstructor = "of")
public class ConfigurationRegistry {

    private final WoodAltar plugin;

    public void register() {
        BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);

        configurationInjector.saveDefaultConfiguration(plugin,
                "mensagens.yml"
        );

        configurationInjector.injectConfiguration(
                GeneralValue.instance(),
                MessageValue.instance()
        );
    }
}