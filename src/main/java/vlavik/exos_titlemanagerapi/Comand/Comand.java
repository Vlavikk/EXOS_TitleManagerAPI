package vlavik.exos_titlemanagerapi.Comand;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTimeUpdate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Comand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
//        World world = Bukkit.getWorld("world"); // Название вашего мира
//        long currentTime = world.getGameTime(); // Получение текущего полного времени
//        PacketEvents.getAPI().getPlayerManager().sendPacket(player,new
//                WrapperPlayServerTimeUpdate(currentTime-25L,world.getTime(),true));
        player.sendMessage(Component.text("попа проверка").color(TextColor.color(78,92,36)));
        return false;
    }
}
