package vlavik.exos_titlemanagerapi.Comand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom.ExChatBottomNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
//            ExActionBar actionBar = new ExActionBar("dwadawdwadaw",100);
//            Bukkit.getOnlinePlayers().forEach(p -> titlePlayer.send(actionBar));
//            ExChatBottomNotification b = new ExChatBottomNotification(NotificationType.ERROR,"Вы приобрели дом ёбаный йогурт",true);
            ExChatBottomNotification b = new ExChatBottomNotification(NotificationType.ERROR,"Игрок не в сети.Используйте команду\n/team kick <игрок>",true);
//            ExChatBottomNotification a = new ExChatBottomNotification(NotificationType.ERROR,"Вы приобрели дом ёбаный йогурт",true);
            Bukkit.getOnlinePlayers().forEach(p -> titlePlayer.send(b));
            Bukkit.getScheduler().runTaskLater(EXOS_TitleManagerAPI.getInstance(), new Runnable() {
                @Override
                public void run() {
//                    Bukkit.getOnlinePlayers().forEach(p -> titlePlayer.send(a));
                }
            },10);
        }
        return false;
    }
}
