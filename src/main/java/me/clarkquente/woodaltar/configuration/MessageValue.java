package me.clarkquente.woodaltar.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("mensagens.yml")
@ConfigSection("Mensagens")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageValue implements ConfigurationInjectable {

    @Getter private static final MessageValue instance = new MessageValue();

    @ConfigField("SemPermissao") private String noPermission;
    @ConfigField("AltarExistente") private String altarAlreadyExists;
    @ConfigField("AltarInexistente") private String altarNotFound;
    @ConfigField("AltarCriado") private String altarCreated;
    @ConfigField("AltarVivo") private String altarAlive;
    @ConfigField("AltarMorto") private String altarNotAlive;
    @ConfigField("AltarDesespawnado") private String altarUnspawned;
    @ConfigField("LocalAlterado") private String localChanged;
    @ConfigField("RecompensasAlteradas") private String rewardsChanged;
    @ConfigField("TempoAlterado") private String timeChanged;
    @ConfigField("VidaMaximaAlterada") private String maxHealthChanged;
    @ConfigField("AutoStartAlterado") private String autoStartChanged;
    @ConfigField("NaoPodeAlterar") private String cantChange;
    @ConfigField("AltarDeletado") private String altarDeleted;
    @ConfigField("AltarList") private String altarList;
    @ConfigField("SemAltares") private String noAltares;
    @ConfigField("TeleportadoComSucesso") private String teleportedToAltar;
    @ConfigField("NumeroInvalido") private String invalidNumber;
    @ConfigField("AltarApareceu") private List<String> altarSpawned;
    @ConfigField("AltarDestruido") private List<String> altarDestroyed;
    @ConfigField("AltarAjuda") private List<String> altarHelp;

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }
}