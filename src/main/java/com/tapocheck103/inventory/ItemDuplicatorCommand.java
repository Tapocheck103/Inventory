package com.tapocheck103.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemDuplicatorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("§eИспользование: /duplicate <номер_слота 0-9> [ник]");
            return true;
        }

        // Определяем слот
        int slot;
        try {
            slot = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cНомер слота должен быть числом от 0 до 9!");
            return true;
        }
        if (slot < 0 || slot > 9) {
            sender.sendMessage("§cНомер слота должен быть в диапазоне 0-9!");
            return true;
        }

        // Определяем игрока
        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
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

        // Дублируем предмет
        PlayerInventory inv = target.getInventory();
        ItemStack item = inv.getItem(slot);

        if (item == null || item.getType() == Material.AIR) {
            sender.sendMessage("§cВ указанном слоте у " + target.getName() + " нет предмета!");
            return true;
        }

        inv.addItem(item.clone());
        sender.sendMessage("§aПредмет у " + target.getName() + " успешно продублирован!");
        return true;
    }
}
