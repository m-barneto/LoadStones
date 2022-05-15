package me.mattdokn.loadstones;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EventListener implements Listener {
    @EventHandler
    public void OnPlayerInteractEvent(PlayerInteractEvent e) {
        if (!e.hasItem() || !e.hasBlock() || e.getAction() != Action.RIGHT_CLICK_BLOCK) return; // || !e.getPlayer().hasPermission("loadstone.create")

        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();
        LoadStoneData loadstone = new LoadStoneData(block);

        if (item.getType() != Material.NETHER_STAR) return;
        if (block.getType() != Material.LODESTONE) return;
        if (Util.loadstones.contains(loadstone.toString())) return;

        // Create loadstone
        e.getPlayer().getInventory().removeItem(new ItemStack(Material.NETHER_STAR, 1));
        e.getPlayer().sendMessage("Created a loadstone.");
        e.getPlayer().playSound(block.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, .2f, 1f);

        Util.loadstones.add(loadstone.toString());
        Util.saveData();

        Chunk chunk = block.getChunk();
        chunk.load();
        chunk.setForceLoaded(true);
    }
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != Material.LODESTONE) return;
        LoadStoneData loadstone = new LoadStoneData(e.getBlock());
        if (Util.loadstones.contains(loadstone.toString())) {
            Util.loadstones.remove(loadstone.toString());
            e.getPlayer().sendMessage("Destroyed a loadstone.");
            Util.saveData();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkUnload(ChunkUnloadEvent e) {
        final Chunk currentChunk = e.getChunk();
        final String loadstone = currentChunk.getWorld().getName() + "," + currentChunk.getX() + "," + currentChunk.getZ();
        if (Util.loadstones.contains(loadstone)) {
            LoadStones.plugin.getLogger().severe("Chunk is unloading when it should be force-loaded: " + loadstone);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldUnload(WorldUnloadEvent e) {
        final List<String> worlds = new ArrayList<>();
        for (final String loadstone : Util.loadstones) {
            final String world = loadstone.split(",")[0];
            worlds.add(world.toLowerCase());
        }
        if (worlds.contains(e.getWorld().getName().toLowerCase())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldLoad(WorldLoadEvent e) {
        for (final String loadstone : Util.loadstones) {
            LoadStoneData ls = LoadStoneData.fromString(loadstone);
            final int x = ls.chunkX;
            final int z = ls.chunkZ;
            final String world = ls.world;
            try {
                Bukkit.getServer().getWorld(world).loadChunk(x, z);
                Bukkit.getServer().getWorld(world).setChunkForceLoaded(x, z, true);
            } catch (NullPointerException ex) {
                LoadStones.plugin.getLogger().severe("World '" + world + "' doesn't exist, or isn't loaded in memory.");
            }
        }
    }
}
