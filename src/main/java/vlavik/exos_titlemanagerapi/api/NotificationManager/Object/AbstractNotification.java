package vlavik.exos_titlemanagerapi.api.NotificationManager.Object;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationPlace;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.Optional;

public abstract class AbstractNotification {
    private NotificationPlace place;
    private NotificationType type;
    private Component text;
    private boolean force;
    public abstract void send(TitlePlayer titlePlayer);
    public abstract Component formatter(String input);
    public <T> void setText(T text){
        Optional<String> out = Optional.empty();
        if (text instanceof String){
            out = Optional.of((String) text);
        }else if (text instanceof Component){
            TextComponent textComponent = (TextComponent) text;
            out = Optional.of( textComponent.content());
        }
        out.ifPresent(value -> this.text = formatter(value));
    }
    public void setPlace(NotificationPlace place) {
        this.place = place;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public NotificationPlace getPlace() {
        return place;
    }
    public NotificationType getType() {
        return type;
    }
    public Component getText() {
        return text;
    }
    public boolean isForce() {
        return force;
    }
    public void setForce(boolean force) {
        this.force = force;
    }
}
