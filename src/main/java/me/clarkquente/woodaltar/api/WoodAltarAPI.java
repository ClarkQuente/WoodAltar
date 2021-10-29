package me.clarkquente.woodaltar.api;

import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.models.Altar;
import org.bukkit.Location;

public class WoodAltarAPI {

    private static WoodAltarAPI woodAltarAPI;

    private WoodAltarAPI() {}

    public static WoodAltarAPI getInstance() {
        if(woodAltarAPI == null) {
            return new WoodAltarAPI();
        }

        return woodAltarAPI;
    }

    public String getMetadataKey() {
        return "WoodAltar";
    }

    public Altar getAltar(Location location) {
        return WoodAltar.getAltarManager().getAltar(location);
    }

    public Altar getAltar(String id) {
        return WoodAltar.getAltarManager().getAltar(id);
    }
}
