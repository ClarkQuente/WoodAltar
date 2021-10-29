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

    /*
     * A method to get the Metadata Key from a altar.
     *
     * @return Altar metadata key.
     */
    public String getMetadataKey() {
        return "WoodAltar";
    }

    /*
     * Get a altar from id.
     *
     * @param id the id you picked up by the metadata.
     * @return Return the altar or null if not found.
     */
    public Altar getAltar(String id) {
        return WoodAltar.getAltarManager().getAltar(id);
    }
}
