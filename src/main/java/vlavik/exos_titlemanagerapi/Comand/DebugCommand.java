package vlavik.exos_titlemanagerapi.Comand;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
    private final ExChatBottomNotification notification = new ExChatBottomNotification(NotificationType.ERROR,"Игрок не в сети.Используйте команду\n/team kick <игрок>",true);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            TitlePlayer titlePlayer = TitlePlayer.getTitlePlayer(player);
//            titlePlayer.send(notification);
            titlePlayer.getTitlePatterns().sendDarkInActionBar(-1,true);
            titlePlayer.sendTitle(Component.text("\uE00C").color(TextColor.color(78,92,36)),-1);
//            ExActionBar actionBar = new ExActionBar("dwadawdwadaw",100);
//            ExChatBottomNotification b = new ExChatBottomNotification(NotificationType.ERROR,"Вы приобрели дом ёбаный йогурт",true);
//            ExChatBottomNotification b = new ExChatBottomNotification(NotificationType.ERROR,"Игрок не в сети.Используйте команду\n/team kick <игрок>",true);
//            ExChatBottomNotification a = new ExChatBottomNotification(NotificationType.ERROR,"Вы приобрели дом ёбаный йогурт",true);
//            Bukkit.getOnlinePlayers().forEach(p -> titlePlayer.send(notification));
        }
        return false;
    }
}
