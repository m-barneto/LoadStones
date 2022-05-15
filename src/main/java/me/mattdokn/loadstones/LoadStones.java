package me.mattdokn.loadstones;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LoadStones extends JavaPlugin {
    public static LoadStones plugin = null;
    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        List<String> invalidLoaders = new ArrayList<>();
        for (String loader : Util.loadstones) {
            try {
                LoadStoneData data = LoadStoneData.fromString(loader);
                Chunk chunk = getServer().getWorld(data.world).getChunkAt(data.chunkX, data.chunkZ);
                chunk.load();
                chunk.setForceLoaded(true);
            } catch (NullPointerException e) {
                invalidLoaders.add(loader);
                e.printStackTrace();
            }
        }
        Util.loadstones.removeAll(invalidLoaders);
        Util.saveData();
    }
}
