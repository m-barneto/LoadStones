package me.mattdokn.loadstones;

import org.bukkit.plugin.java.JavaPlugin;

public final class LoadStones extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
}
