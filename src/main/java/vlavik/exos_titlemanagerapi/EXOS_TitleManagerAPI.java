package vlavik.exos_titlemanagerapi;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import vlavik.exos_titlemanagerapi.Comand.Comand;
import vlavik.exos_titlemanagerapi.api.Title.Object.ExCustomTitle;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

import java.util.ArrayList;
import java.util.Arrays;


public final class EXOS_TitleManagerAPI extends JavaPlugin{
    private static EXOS_TitleManagerAPI main;

    @Override
    public void onEnable() {
        main = this;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().init();
        getCommand("titleManager").setExecutor(new Comand());

        for (Player player : Bukkit.getOnlinePlayers()){
            TitlePlayer player1 = new TitlePlayer(player);
           player1.send(TitleEditable.Type.BOSS_BAR,"1",20, ExCustomTitle.IgnoredType.SAVE);
            player1.sendAnimation(TitleEditable.Type.TITLE,new ArrayList<>(Arrays.asList(
                    "1",
                    "2",
                    "3"))
            ,80,10);
            player1.send(TitleEditable.Type.ACTIONBAR,"1",41, ExCustomTitle.IgnoredType.SAVE);
            player1.send(TitleEditable.Type.ACTIONBAR,"11",41, ExCustomTitle.IgnoredType.SAVE);
            player1.removeQueue(TitleEditable.Type.TITLE,1);
            player1.send(TitleEditable.Type.ACTIONBAR,"2",50);
            player1.send(TitleEditable.Type.ACTIONBAR,"3",71);

            player1.send(TitleEditable.Type.TITLE,"2",22);
        }
    }

    @Override
    public void onDisable() {
        for (TitlePlayer player : TitlePlayer.titlePlayers.values()){
            player.cancel(true, TitleEditable.Type.TITLE, TitleEditable.Type.ACTIONBAR, TitleEditable.Type.BOSS_BAR);
        }
    }

    public static EXOS_TitleManagerAPI getInstance() {
        return main;
    }
}
