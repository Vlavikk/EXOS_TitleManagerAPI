package vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass;

import net.kyori.adventure.text.Component;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.Optional;


public abstract class AbstractDefaultTitle extends AbstractTitle {
    protected Component text;
    public <T> void setText(T text){
        Optional<Component> component = Optional.empty();
        if (text instanceof String){
            component = Optional.of(Utils.convertToComponent((String) text));
        }else if (text instanceof Component){
            component = Optional.of((Component) text);
        }
        component.ifPresent(value -> this.text = value);
    }

    public Component getText() {
        return text;
    }

}
