package vlavik.exos_titlemanagerapi.api.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class ExNotificationEndEvent extends Event {
    public TitlePlayer titlePlayer;
    public AbstractNotification abstractNotification;
    public ExNotificationEndEvent(TitlePlayer titlePlayer,AbstractNotification abstractNotification){
        this.titlePlayer = titlePlayer;
        this.abstractNotification = abstractNotification;
    }

    public TitlePlayer getTitlePlayer() {
        return titlePlayer;
    }

    public AbstractNotification getAbstractNotification() {
        return abstractNotification;
    }

    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}
}
