package vlavik.exos_titlemanagerapi.api.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom.ExChatBottomNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class ExNotificationEndEvent extends Event {
    public TitlePlayer titlePlayer;
    public ExChatBottomNotification abstractNotification;
    public ExNotificationEndEvent(TitlePlayer titlePlayer, ExChatBottomNotification abstractNotification){
        this.titlePlayer = titlePlayer;
        this.abstractNotification = abstractNotification;
    }

    public TitlePlayer getTitlePlayer() {
        return titlePlayer;
    }

    public ExChatBottomNotification getAbstractNotification() {
        return abstractNotification;
    }

    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}
}
