package vlavik.exos_titlemanagerapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;

public class TitleStartEvent extends Event {
    private TitleEditable.Type type;
    private final Player player;
    private Object text;
    public TitleStartEvent(Player player,Object objectTitle){
        this.player = player;
        if (objectTitle == null){
            this.text = null;
            this.type = null;
        }
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
