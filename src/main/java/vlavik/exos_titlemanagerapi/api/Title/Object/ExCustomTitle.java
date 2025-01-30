package vlavik.exos_titlemanagerapi.api.Title.Object;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.Title.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.Title.Enums.TitleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class ExCustomTitle {
    private final TitleType type;
    private final Object text;
    private int time;
    private final boolean isForced;
    private final IgnoredType ignoreOtherType;
    @forAnimation
    private final boolean isAnimation;
    @forAnimation
    private final int delayAnimation;
    @forAnimation
    private final List<Object> animationFrame;
    private final List<Integer> titleTimes;
    public ExCustomTitle(TitleType type, Object text, int time, boolean forced, IgnoredType ignoredType){
        this.type = type;
        if (text instanceof String || text instanceof Component ||text instanceof List){
            this.text = text;
        }
        else{
            throw new IllegalArgumentException("Текстом для "+type.toString()+" может быть только String/Component/List<>");
        }
        ignoreOtherType = ignoredType;
        isForced = forced;
        isAnimation = false;
        this.time = time;
        this.delayAnimation = 0;
        this.animationFrame = null;
        this.titleTimes = type == TitleType.TITLE ? new ArrayList<>(List.of(time)) : null;
    }
    //Для анимаций
    public ExCustomTitle(TitleType type, List<Object> list, int time, boolean forced, int delay, IgnoredType ignoredType){
        this.type = type;
        this.text = null;
        for (Object object : list){
            if (object instanceof String || object instanceof Component) continue;
            else throw new IllegalArgumentException("Листом для "+type.toString()+" может быть /List<String/Component>");
        }
        this.animationFrame = list;
        ignoreOtherType = ignoredType;
        isForced = forced;
        this.isAnimation = true;
        this.time = time;
        this.delayAnimation = delay;
        this.titleTimes = null;
    }

    //Для Title ВРЕМЕННО!!
    public ExCustomTitle(TitleType type, Object text, List<Integer> times, boolean forced, IgnoredType ignoredType){
        this.type = type;
        if (text instanceof String || text instanceof Component ||text instanceof List){
            this.text = text;
        }
        else{
            throw new IllegalArgumentException("Текстом для "+type.toString()+" может быть только String/Component/List<>");
        }
        ignoreOtherType = ignoredType;
        isForced = forced;
        isAnimation = false;
        this.delayAnimation = 0;
        this.animationFrame = null;
        this.time = 1;
        this.titleTimes = times;
    }

    public Object getText() {
        return text;
    }

    public @NotNull TitleType getType() {
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

    public int getDelayAnimation() {
        return delayAnimation;
    }

    public List<Object> getAnimationFrame() {
        return animationFrame;
    }
    public List<Integer> getTitleTimes(){
        return titleTimes;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface forAnimation{
        String message() default "";
    }
}
