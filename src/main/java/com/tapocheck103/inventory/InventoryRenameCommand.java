package com.tapocheck103.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryRenameCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public InventoryRenameCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length < 1) { p.sendMessage("§e/inventoryrename <имя>"); return true; }

        String name = String.join(" ", args);
        Map<Integer, ItemMeta> saved = new HashMap<>();

        // Сохраняем и меняем имена
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack item = p.getInventory().getItem(i);
            if (item != null && item.getType() != Material.AIR) {
                saved.put(i, item.getItemMeta().clone());
                ItemMeta m = item.getItemMeta();
                m.setDisplayName(name);
                item.setItemMeta(m);
            }
        }

        // Через 3 сек возвращаем оригинал
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Map.Entry<Integer, ItemMeta> e : saved.entrySet()) {
                ItemStack item = p.getInventory().getItem(e.getKey());
                if (item != null && item.getType() != Material.AIR)
                    item.setItemMeta(e.getValue());
            }
            p.sendMessage("§eИмена восстановлены!");
        }, 60L);

        p.sendMessage("§aПредметы переименованы!");
        return true;
    }
}
