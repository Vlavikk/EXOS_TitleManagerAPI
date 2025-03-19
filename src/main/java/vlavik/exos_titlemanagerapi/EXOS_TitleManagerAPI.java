package vlavik.exos_titlemanagerapi;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vlavik.exos_titlemanagerapi.Comand.DebugCommand;
import vlavik.exos_titlemanagerapi.api.Listeners.HashListener;
import vlavik.exos_titlemanagerapi.api.Listeners.PlayerQuit;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime.GameTimeManager;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.Objects;


public final class EXOS_TitleManagerAPI extends JavaPlugin{
    private static EXOS_TitleManagerAPI main;

    @Override
    public void onEnable() {
        main = this;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().init();
        PacketEvents.getAPI().getEventManager().registerListener(new GameTimeManager(), PacketListenerPriority.NORMAL);
        Bukkit.getPluginManager().registerEvents( new PlayerQuit(),this);
        Bukkit.getPluginManager().registerEvents( new HashListener(),this);
        Objects.requireNonNull(getCommand("title-manager")).setExecutor(new DebugCommand());
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
