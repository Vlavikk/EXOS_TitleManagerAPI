package vlavik.exos_titlemanagerapi;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vlavik.exos_titlemanagerapi.api.Listeners.PlayerQuit;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;


public final class EXOS_TitleManagerAPI extends JavaPlugin{
    private static EXOS_TitleManagerAPI main;

    @Override
    public void onEnable() {
        main = this;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().init();
        Bukkit.getPluginManager().registerEvents( new PlayerQuit(),this);
    }

    @Override
    public void onDisable() {
        TitlePlayer.titlePlayers.values().forEach(
                p -> p.cancel(TitleType.TITLE, TitleType.ACTIONBAR, TitleType.BOSS_BAR));
    }

    public static EXOS_TitleManagerAPI getInstance() {
        return main;
    }
}
