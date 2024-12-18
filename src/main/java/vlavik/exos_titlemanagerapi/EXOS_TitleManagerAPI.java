package vlavik.exos_titlemanagerapi;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.bstats.bukkit.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import vlavik.exos_titlemanagerapi.Comand.Comand;
import vlavik.exos_titlemanagerapi.api.Title.Object.TitleTask;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;


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
            player1.send(TitleEditable.Type.ACTIONBAR,"gegege",50);
            player1.send(TitleEditable.Type.TITLE,"dddddddd",30);
            player1.send(TitleEditable.Type.BOSS_BAR,"яяяDWAD",20,false);
//            Bukkit.getScheduler().runTaskLater(this, new Runnable() {
//                @Override
//                public void run() {
//
            player1.send(TitleEditable.Type.TITLE,"яяяяя",22);
//                }
//            },2);
        }
    }

    @Override
    public void onDisable() {
        for (TitlePlayer player : TitlePlayer.titlePlayers.values()){
            player.cancel(true, TitleEditable.Type.TITLE, TitleEditable.Type.ACTIONBAR, TitleEditable.Type.BOSS_BAR);
        }
        // Plugin shutdown logic
    }

    public static EXOS_TitleManagerAPI getInstance() {
        return main;
    }
}
