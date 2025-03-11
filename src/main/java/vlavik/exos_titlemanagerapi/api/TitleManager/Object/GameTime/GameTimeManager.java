package vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTimeUpdate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTimeManager implements PacketListener{
    public static HashMap<String,AsyncGameTimePlayer> playersAsyncTime = new HashMap<>();
    public static List<String> skipPaket = new ArrayList<>();
    public static void sendTime(Player player, int startTime, int activeTime){
        AsyncGameTimePlayer asyncGameTimePlayer = playersAsyncTime.getOrDefault(
                player.getName(),
                new AsyncGameTimePlayer(player));
        asyncGameTimePlayer.setStartTime(startTime);
        asyncGameTimePlayer.setActiveTime(activeTime);
        asyncGameTimePlayer.send();
        if (!skipPaket.contains(player.getName())) skipPaket.add(player.getName());
        playersAsyncTime.put(player.getName(),asyncGameTimePlayer);
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Player player = event.getPlayer();
        if (event.getPacketType() != PacketType.Play.Server.TIME_UPDATE) return;
        WrapperPlayServerTimeUpdate packet = new WrapperPlayServerTimeUpdate(event);
        if (playersAsyncTime.containsKey(player.getName())) {
            if (skipPaket.contains(player.getName()) && packet.isTickTime()){//если время в тиках значит пакет наш слоняра
                skipPaket.remove(player.getName());
            }else event.setCancelled(true);
        }
    }
}
