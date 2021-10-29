package me.clarkquente.woodaltar.hooks;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import org.bukkit.Bukkit;

public class HologramHook {

    public static boolean check() {
        if (Bukkit.getPluginManager().isPluginEnabled(HolographicDisplays.getInstance())) {
            return true;
        }
        return false;
    }
}
