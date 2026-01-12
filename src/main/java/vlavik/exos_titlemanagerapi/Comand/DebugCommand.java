package vlavik.exos_titlemanagerapi.Comand;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
//            Component component = Component.text("\uE000")
//                    .font(Key.key("minecraft", "stickers/stickers_custom"))
//                    .color(TextColor.color(78,67,4));
//            player.sendMessage(component);
        }
        return false;
    }
}
