package vlavik.exos_titlemanagerapi.api.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import vlavik.exos_titlemanagerapi.api.Events.ExNotificationEndEvent;
import vlavik.exos_titlemanagerapi.api.Events.ExTitleEndEvent;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom.ExChatBottomNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.HashMap;

public class HashListener implements Listener {
    @EventHandler
    public void removeTitleHash(ExTitleEndEvent e){
        AbstractTitle title = e.getAbstractTitle();
        if (title.getType() == TitleType.ACTIONBAR && !title.isAnimation()){
            ExActionBar actionBar = (ExActionBar) title;
            actionBar.getExtendFromNotification().ifPresent(notification ->{
                Bukkit.getPluginManager().callEvent(new ExNotificationEndEvent(e.getTitlePlayer(),
                        actionBar.getExtendFromNotification().get()));

                if (notification instanceof ExChatBottomNotification exChatBottomNotification){
                    HashMap<TitlePlayer,ExActionBar> handlers = exChatBottomNotification.getHandlers();
                    if (handlers.containsKey(e.getTitlePlayer())){
                        if (handlers.get(e.getTitlePlayer()) == actionBar){
                            handlers.remove(e.getTitlePlayer());
                        }
                    }
                }
            });
        }
    }
}
