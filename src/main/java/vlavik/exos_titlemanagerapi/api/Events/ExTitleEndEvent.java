package vlavik.exos_titlemanagerapi.api.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class ExTitleEndEvent extends Event {
    public TitlePlayer titlePlayer;
    public AbstractTitle abstractTitle;
    public ExTitleEndEvent(TitlePlayer titlePlayer,AbstractTitle abstractTitle){
        this.titlePlayer = titlePlayer;
        this.abstractTitle = abstractTitle;
    }

    public TitlePlayer getTitlePlayer() {
        return titlePlayer;
    }
    public AbstractTitle getAbstractTitle() {
        return abstractTitle;
    }
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}
}
