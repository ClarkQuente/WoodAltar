package me.clarkquente.woodaltar.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("config.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralValue implements ConfigurationInjectable {

    @Getter
    private static final GeneralValue instance = new GeneralValue();

    @ConfigField("Configuracoes.VidaPadrao") private double defaultHealth;
    @ConfigField("Configuracoes.TempoPadrao") private int defaultTime;
    @ConfigField("Configuracoes.Holograma") private List<String> hologram;

    public static <T> T get(Function<GeneralValue, T> function) {
        return function.apply(instance);
    }
}