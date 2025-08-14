package com.tapocheck103.inventory;

import org.bukkit.plugin.java.JavaPlugin;
import com.tapocheck103.inventory.ItemDuplicatorCommand;
import com.tapocheck103.inventory.InventoryRenameCommand;

public class InventoryBugs extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("duplicate").setExecutor(new ItemDuplicatorCommand());
        getCommand("inventoryrename").setExecutor(new InventoryRenameCommand(this));
        getLogger().info("InventoryBugs включен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("InventoryBugs выключен!");
    }
}
