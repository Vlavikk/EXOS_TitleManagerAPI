package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractAnimationTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.List;

public class ExActionBarAnimation extends AbstractAnimationTitle {
    {
        setType(TitleType.ACTIONBAR);
        setAnimation(true);
    }

    public ExActionBarAnimation(List<?> frames, int time, int delayBetweenFrame, IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setIgnoredType(ignoredType);
    }
    public ExActionBarAnimation(List<?> frames, int time, int delayBetweenFrame,boolean forced ,IgnoredType... ignoredType){
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setForce(forced);
        setIgnoredType(ignoredType);

    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        Player player = titlePlayer.getPlayer();
        BukkitTask task = new BukkitRunnable() {
            private int frame = 0;
            private int time = 0;
            @Override
            public void run() {
                if (frame >= getListFrames().size()) frame = 0;
                SendPacket.sendActionBar(player,getListFrames().get(frame));
                playSound(player);
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
}
