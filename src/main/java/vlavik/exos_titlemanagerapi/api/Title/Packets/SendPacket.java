package vlavik.exos_titlemanagerapi.api.Title.Packets;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.EnumSet;

public class SendPacket {
    public static void sendActionBar(Player player, Component text){
        PacketEvents.getAPI().getPlayerManager().
                sendPacket(player,new WrapperPlayServerActionBar(text));
    }

    /**
     *
     * @param times лист разделенный на элементы: <p>
     *              1 - время на экране(обязательно)<p>
     *              2 - время появления(не обязательно)<p>
     *              3 - время угасания(не обязательно)<p>
     */
    public static void sendTitle(Player player,Component text,int... times){
        WrapperPlayServerSetTitleText title = new WrapperPlayServerSetTitleText(
                text
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,title);
        setTitleTime(player,times);
    }
    private static void setTitleTime(Player player,int... times){
        WrapperPlayServerSetTitleTimes title = new WrapperPlayServerSetTitleTimes(
                times.length >= 2 ? times[1] : 0,
                times.length >= 1 ? times[0] : 1,
                times.length >= 3 ? times[2] : 0
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,title);
    }
    public static void sendBossBar(Player player,Component text){
        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
                player.getUniqueId(),
                WrapperPlayServerBossBar.Action.ADD
        );
        bossBar.setColor(BossBar.Color.YELLOW);
        bossBar.setOverlay(BossBar.Overlay.PROGRESS);
        bossBar.setHealth(1);
        bossBar.setTitle(text);
        bossBar.setFlags(EnumSet.noneOf(BossBar.Flag.class));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
    public static void removeBossBar(Player player){
        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
                player.getUniqueId(),
                WrapperPlayServerBossBar.Action.REMOVE
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
    public static void updateNameBossBar(Player player,Component newTitle){
        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
                player.getUniqueId(),
                WrapperPlayServerBossBar.Action.UPDATE_TITLE
        );
        bossBar.setTitle(newTitle);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
}
