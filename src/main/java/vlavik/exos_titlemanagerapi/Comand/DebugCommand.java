package vlavik.exos_titlemanagerapi.Comand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.Arrays;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
            titlePlayer.getTitlePatterns().sendDarkInTitle(60,20,60,true);
//            ItemStack itemStack = new ItemStack(Material.PAPER);
//            itemStack.editMeta(meta -> meta.setDisplayName("dwadawdwadawdaw"));
//            byte[] d = itemStack.serializeAsBytes();
//            Bukkit.broadcastMessage(Arrays.toString(d));
//            player.getInventory().addItem(ItemStack.deserializeBytes(d));
        }
        return false;
    }
}
