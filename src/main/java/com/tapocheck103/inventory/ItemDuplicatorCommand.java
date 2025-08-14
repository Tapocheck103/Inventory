package com.tapocheck103.inventory;

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
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду может выполнить только игрок!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("§eИспользование: /duplicate <номер_слота 0-9>");
            return true;
        }

        int slot;
        try {
            slot = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cНомер слота должен быть числом от 0 до 9!");
            return true;
        }

        if (slot < 0 || slot > 9) {
            player.sendMessage("§cНомер слота должен быть в диапазоне 0-9!");
            return true;
        }

        PlayerInventory inv = player.getInventory();
        ItemStack item = inv.getItem(slot);

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage("§cВ указанном слоте нет предмета!");
            return true;
        }

        ItemStack copy = item.clone();
        inv.addItem(copy);
        player.sendMessage("§aПредмет успешно дублирован!");
        return true;
    }
}
