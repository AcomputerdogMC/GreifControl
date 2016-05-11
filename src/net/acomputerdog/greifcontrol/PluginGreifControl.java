package net.acomputerdog.greifcontrol;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class PluginGreifControl extends JavaPlugin implements Listener {

    //based on Materials enum indexes, value determines if it can be exploded
    private Material[] materials;
    private boolean[] containers;

    @Override
    public void onEnable() {
        containers = new boolean[Material.values().length];
        materials = new Material[]{Material.CHEST, Material.TRAPPED_CHEST, Material.DISPENSER, Material.FURNACE, Material.DROPPER, Material.ENCHANTMENT_TABLE, Material.ANVIL, Material.HOPPER};
        for (Material mat : materials) {
            containers[mat.ordinal()] = true;
        }
        //Material[] mats = Material.values();
        /*
        for (int i = 0; i < mats.length; i ++) {
            Material m = mats[i];
            //true if material is a container
            containers[i] = (m == Material.CHEST || m == Material.TRAPPED_CHEST || m == Material.DISPENSER || m == Material.FURNACE || m == Material.DROPPER || m == Material.ENCHANTMENT_TABLE || m == Material.ANVIL || m == Material.HOPPER);
        }
        */

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        containers = null;
        materials = null;
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        List<Block> blocks = new ArrayList<>(e.blockList().size());
        blocks.addAll(e.blockList());
        e.blockList().clear();
        //if not a container
        blocks.stream().filter(b -> !containers[e.getBlock().getType().ordinal()]).forEach(b -> {
            e.blockList().add(b);
        });
    }
}
