package vlavik.exos_titlemanagerapi.api.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    private void Quit(PlayerQuitEvent e){
        Player player = e.getPlayer();

    }
}
