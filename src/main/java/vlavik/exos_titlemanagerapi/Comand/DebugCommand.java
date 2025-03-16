package vlavik.exos_titlemanagerapi.Comand;

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
        }
        return false;
    }
}
