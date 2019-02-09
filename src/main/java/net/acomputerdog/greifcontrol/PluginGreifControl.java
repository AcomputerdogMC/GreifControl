package net.acomputerdog.greifcontrol;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class PluginGreifControl extends JavaPlugin implements Listener {

    //based on Materials enum indexes, value determines if it can be exploded
    private Material[] materials;
    private boolean[] containers;

    @Override
    public void onEnable() {
        containers = new boolean[Material.values().length];
        materials = new Material[]{
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.DISPENSER,
                Material.DROPPER,
                Material.HOPPER,
                Material.ENCHANTING_TABLE,
                Material.FURNACE,
                Material.BREWING_STAND,
                Material.CAULDRON,
                Material.JUKEBOX,
                Material.SHULKER_BOX};

        for (Material mat : materials) {
            containers[mat.ordinal()] = true;
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        containers = null;
        materials = null;

        HandlerList.unregisterAll((JavaPlugin) this);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        // remove any containers from the explosion list
        e.blockList().removeIf(b -> containers[e.getBlock().getType().ordinal()]);
    }
}
