package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation;

import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractAnimationTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.List;
import java.util.Optional;

public class ExTitleAnimation extends AbstractAnimationTitle {
    {
        setType(TitleType.TITLE);
        setAnimation(true);
    }
    private final int timeFadeIn = 0; //у анимации нет появления и затухания
    private final int timeFadeOut = 0;
    private Optional<Component> subTitle = Optional.empty();
    public ExTitleAnimation(List<?> frames, int time, int delayBetweenFrame, IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setIgnoredType(ignoredType);
    }
    public ExTitleAnimation(List<?> frames,int time,int delayBetweenFrame,boolean forced,IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setForce(forced);
        setIgnoredType(ignoredType);
    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        BukkitTask task = new BukkitRunnable() {
            private int frame = 0;
            private int time = 0;
            @Override
            public void run() {
                if (frame >= getListFrames().size()) frame = 0;
                SendPacket.sendTitle(titlePlayer.getPlayer(),getListFrames().get(frame),timeFadeIn,getDelayBetweenFrame()+2,timeFadeOut,subTitle);
                if (time >= getTime() && !isInfinity()){
                    titlePlayer.next(getType());
                    return;
                }
                time = time+getDelayBetweenFrame();
                frame++;
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,getDelayBetweenFrame());
        setTask(task);

    }
    public <T> void setSubTitle(T text){
        Optional<Component> component = Optional.empty();
        if (text instanceof String){
            component = Optional.of(Utils.convertToComponent((String) text));
        }else if(text instanceof Component){
            component = Optional.of((Component) text);
        }
        if (component.isPresent()) subTitle = component;
    }
}
