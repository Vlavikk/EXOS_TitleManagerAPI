package vlavik.exos_titlemanagerapi.api.Title.Object;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;

import java.util.Collection;
import java.util.List;

public class ExCustomTitle {
    private final TitleEditable.Type type;
    private final Object text;
    private int time;
    private final boolean isAnimation;
    private final boolean isForced;
    private final IgnoredType ignoreOtherType;
    public ExCustomTitle(TitleEditable.Type type, Object text, int time, boolean forced, IgnoredType ignoredType){
        this.type = type;
        if (text instanceof String || text instanceof Component ||text instanceof List){
            this.text = text;
        }
        else{
            throw new IllegalArgumentException("Текстом для "+type.toString()+" может быть String/Component/List<>");
        }
        ignoreOtherType = ignoredType;
        isForced = forced;
        isAnimation = text instanceof List;
        this.time = time;
    }
    public enum IgnoredType {
        NONE,
        DELETE,
        SAVE;
    }

    public Object getText() {
        return text;
    }

    public TitleEditable.@NotNull Type getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isForced() {
        return isForced;
    }

    public IgnoredType getIgnoreOtherType() {
        return ignoreOtherType;
    }
}
