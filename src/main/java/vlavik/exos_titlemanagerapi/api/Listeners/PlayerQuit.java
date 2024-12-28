package vlavik.exos_titlemanagerapi.api.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

public class PlayerQuit implements Listener {
    @EventHandler
    private void Quit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (TitlePlayer.titlePlayers.containsKey(player.getName())){
            TitlePlayer.getTitlePlayer(player).cancel(true,
                    TitleEditable.Type.TITLE,
                    TitleEditable.Type.ACTIONBAR,
                    TitleEditable.Type.BOSS_BAR);
        }
    }
}
