package me.mattdokn.loadstones;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Util {
    public static File dataFile = new File(LoadStones.plugin.getDataFolder(), "data.yml");
    public static FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
    public static HashSet<String> loadstones = new HashSet<>(data.getStringList("loadstones"));
    static {
        data.addDefault("loadstones", new ArrayList<String>());
        if (data != null && dataFile != null) {
            try {
                data.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void saveData() {
        data.set("loadstones", new ArrayList<>(loadstones));
        if (data == null || dataFile == null) return;
        try {
            data.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
