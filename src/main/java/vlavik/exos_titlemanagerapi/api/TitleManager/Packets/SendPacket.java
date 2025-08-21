package vlavik.exos_titlemanagerapi.api.TitleManager.Packets;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class SendPacket {
    public static void sendActionBar(Player player, Component text){
        PacketEvents.getAPI().getPlayerManager().
                sendPacket(player,new WrapperPlayServerActionBar(text));
    }

    public static void sendTitle(Player player, Component text, int timeFadeIn, int time, int timeFadeUot, Optional<Component> subTitle){
        setTitleTime(player,timeFadeIn,time,timeFadeUot);
        subTitle.ifPresent(t -> setSubTitle(player,t));
        WrapperPlayServerSetTitleText title = new WrapperPlayServerSetTitleText(
                text
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,title);
    }
    private static void setTitleTime(Player player,int timeFadeIn, int time,int timeFadeUot){
        WrapperPlayServerSetTitleTimes title = new WrapperPlayServerSetTitleTimes(
                timeFadeIn,
                time,
                timeFadeUot
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,title);
    }
    public static void setSubTitle(Player player,Component subtitle){
        WrapperPlayServerSetTitleSubtitle title = new WrapperPlayServerSetTitleSubtitle(
                subtitle
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,title);
    }
    public static void sendBossBar(Player player,Component text,UUID uuid){
        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
                uuid,
                WrapperPlayServerBossBar.Action.ADD
        );
        bossBar.setColor(BossBar.Color.YELLOW);
        bossBar.setOverlay(BossBar.Overlay.PROGRESS);
        bossBar.setHealth(1);
        bossBar.setTitle(text);
        bossBar.setFlags(EnumSet.noneOf(BossBar.Flag.class));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
    public static void removeBossBar(Player player,UUID uuid){
        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
                uuid,
                WrapperPlayServerBossBar.Action.REMOVE
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
    public static void updateNameBossBar(Player player,Component newTitle,UUID uuid){
        removeBossBar(player,uuid);
        sendBossBar(player,newTitle,uuid);
//        WrapperPlayServerBossBar bossBar = new WrapperPlayServerBossBar(
//                player.getUniqueId(),
//                WrapperPlayServerBossBar.Action.ADD
//        );
//        bossBar.setTitle(newTitle);
//        PacketEvents.getAPI().getPlayerManager().sendPacket(player,bossBar);
    }
}
