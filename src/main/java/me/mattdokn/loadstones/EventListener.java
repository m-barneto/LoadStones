package me.mattdokn.loadstones;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    @EventHandler
    public void OnPlayerInteractEvent(PlayerInteractEvent e) {
        if (!e.hasItem() || !e.hasBlock()) return; // || !e.getPlayer().hasPermission("loadstone.create")
        Block block = e.getClickedBlock();
        ItemStack item = e.getItem();
        if (item.getType() != Material.NETHER_STAR) return;
        if (block.getType() != Material.LODESTONE) return;
        e.getPlayer().getInventory().removeItem(new ItemStack(Material.NETHER_STAR, 1));
        // Create loadstone
        e.getPlayer().sendMessage("Created a loadstone!");
    }
}
