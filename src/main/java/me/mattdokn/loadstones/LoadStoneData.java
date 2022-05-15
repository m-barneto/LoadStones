package me.mattdokn.loadstones;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

public class LoadStoneData {
    public String world;
    public int chunkX;
    public int chunkZ;
    public LoadStoneData(String world, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }
    public LoadStoneData(Block block) {
        this(block.getWorld().getName(), block.getChunk().getX(), block.getChunk().getZ());
    }
    public static LoadStoneData fromString(String string) {
        String[] s = string.split(",");
        return new LoadStoneData(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
    }
    @Override
    public String toString() {
        return this.world + "," + this.chunkX  + "," + this.chunkZ;
    }
}
