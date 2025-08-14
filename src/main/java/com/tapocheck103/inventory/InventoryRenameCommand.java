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
        if (args.length < 1) {
            sender.sendMessage("§eИспользование: /inventoryrename <имя> [ник]");
            return true;
        }

        // Определяем игрока, чьи предметы меняем
        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[args.length - 1]); // последний аргумент как ник
            if (target == null) {
                sender.sendMessage("§cИгрок не найден!");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cТолько игрок может выполнить без указания цели!");
                return true;
            }
            target = (Player) sender;
        }

        // Собираем имя
        String name = String.join(" ", args.length >= 2 ?
                java.util.Arrays.copyOf(args, args.length - 1) : args);

        Map<Integer, ItemMeta> saved = new HashMap<>();

        // Сохраняем и меняем имена
        for (int i = 0; i < target.getInventory().getSize(); i++) {
            ItemStack item = target.getInventory().getItem(i);
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
                ItemStack item = target.getInventory().getItem(e.getKey());
                if (item != null && item.getType() != Material.AIR)
                    item.setItemMeta(e.getValue());
            }
            target.sendMessage("§eОригинальные имена предметов восстановлены!");
        }, 60L);

        target.sendMessage("§aВсе предметы временно переименованы!");
        return true;
    }
}
