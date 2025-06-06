package vlavik.exos_titlemanagerapi.api.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class PlayerConnection implements Listener {
    @EventHandler
    private void Quit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (TitlePlayer.titlePlayers.containsKey(player.getName())){
            TitlePlayer.getTitlePlayer(player).cancel(
                    TitleType.TITLE,
                    TitleType.ACTIONBAR,
                    TitleType.BOSS_BAR);
            TitlePlayer.getTitlePlayer(player).terminate();
        }
    }
}
