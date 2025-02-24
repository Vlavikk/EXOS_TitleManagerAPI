package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractAnimationTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

import java.util.List;

public class ExBossBarAnimation extends AbstractAnimationTitle {
    {
        setType(TitleType.BOSS_BAR);
        setAnimation(true);
    }
    public ExBossBarAnimation(List<?> frames, int time, int delayBetweenFrame, IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setIgnoredType(ignoredType);
    }
    public ExBossBarAnimation(List<?> frames,int time,int delayBetweenFrame,boolean forced, IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setForced(forced);
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
                SendPacket.updateNameBossBar(titlePlayer.getPlayer(),getListFrames().get(frame));
                if (time >= getTime()){
                    titlePlayer.next(getType());
                    return;
                }
                time = time+getDelayBetweenFrame();
                frame++;
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,getDelayBetweenFrame());
        setTask(task);

    }
}
