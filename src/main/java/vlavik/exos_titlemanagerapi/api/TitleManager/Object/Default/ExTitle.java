package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractDefaultTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.Optional;

public class ExTitle extends AbstractDefaultTitle {
    {
        setType(TitleType.TITLE);
    }
    private int timeFadeIn = 0;
    private int timeFadeOut = 0;
    private Optional<Component> subTitle = Optional.empty();
    public <T> ExTitle(T text, int time, IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setIgnoredType(ignoredType);
    }
    public <T> ExTitle(T text,int time,boolean forced,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setForce(forced);
        setIgnoredType(ignoredType);
    }
    public <T> ExTitle(T text,int timeFadeIn, int time, int timeFadeOut,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        this.timeFadeIn = timeFadeIn;
        this.timeFadeOut = timeFadeOut;
        setIgnoredType(ignoredType);
    }
    public <T> ExTitle(T text,int timeFadeIn, int time, int timeFadeOut,boolean forced,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        this.timeFadeIn = timeFadeIn;
        this.timeFadeOut = timeFadeOut;
        setForce(forced);
        setIgnoredType(ignoredType);
    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        Player player = titlePlayer.getPlayer();
        int period = isInfinity() ? 140 : getTime();    // 4 = нахлест для непрерывной анимации
        BukkitTask task = new BukkitRunnable() {        // хватило бы и 1-2 при идеально работающем сервере
            private boolean skip = false;
            @Override
            public void run() {
                if (isInfinity()) SendPacket.sendTitle(player,getText(),timeFadeIn,period+4,timeFadeOut);
                else {
                    if (!skip){
                        SendPacket.sendTitle(player,getText(),timeFadeIn,period+4,timeFadeOut);
                        skip = true;
                    }else titlePlayer.next(getType());

                }
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        setTask(task);
        startTimer();
    }


    public <T> void setSubTitle(T text){ //todo: не работает
        Optional<Component> component = Optional.empty();
        if (text instanceof String){
            component = Optional.of(Utils.convertToComponent((String) text));
        }else if(text instanceof Component){
            component = Optional.of((Component) text);
        }
        if (component.isPresent()) subTitle = component;
    }
}
