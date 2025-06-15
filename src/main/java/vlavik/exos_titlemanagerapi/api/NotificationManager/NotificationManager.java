package vlavik.exos_titlemanagerapi.api.NotificationManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import vlavik.exos_titlemanagerapi.api.Events.ExTitleEndEvent;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom.ExChatBottomNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;

import java.util.List;

public class NotificationManager implements Listener {
    @EventHandler
    public void notificationHashListener(ExTitleEndEvent e){
        if (e.getAbstractTitle() instanceof ExChatBottomNotification notification){
            List<AbstractTitle> titles = e.getTitlePlayer().getList(TitleType.ACTIONBAR);
            if (titles.size() >= 2 && titles.get(1) instanceof ExChatBottomNotification not2){
                if (not2.getOverrideText().equals(notification.getOverrideText())) return;
            }
            notification.hashClear(e.getTitlePlayer());
        }
    }
}
