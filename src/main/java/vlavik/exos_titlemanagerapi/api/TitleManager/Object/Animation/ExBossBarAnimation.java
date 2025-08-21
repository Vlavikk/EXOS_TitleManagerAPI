package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractAnimationTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExBossBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExBossBarAnimation extends ExBossBar {
    {
        setType(TitleType.BOSS_BAR);
        setAnimation(true);
    }
    protected List<Component> listFrames;
    private int delayBetweenFrame;
    public ExBossBarAnimation(List<?> frames, int time, int delayBetweenFrame, IgnoredType... ignoredType){
        super(null,time);
        setListFrames(frames);
        setTime(time);
        setDelayBetweenFrame(delayBetweenFrame);
        setIgnoredType(ignoredType);
    }
    public ExBossBarAnimation(List<?> frames,int time,int delayBetweenFrame,boolean forced, IgnoredType... ignoredType){
        super(null,time);
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
                SendPacket.updateNameBossBar(player,getListFrames().get(frame),uuid);
                playSound(player);
                if (time >= getTime() && !isInfinity()){
                    cancelTitle(titlePlayer);
                    return;
                }
                time = time+getDelayBetweenFrame();
                frame++;
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,getDelayBetweenFrame());
        setTask(task);

    }
    public void setListFrames(List<?> listFrames){
        List<Component> outList = new ArrayList<>();
        for (Object object : listFrames){
            if (object instanceof Component)
                outList.add((Component) object);
            else if (object instanceof String)
                outList.add(Utils.convertToComponent((String) object));
        }
        this.listFrames = outList;
    }

    public void setDelayBetweenFrame(int delayBetweenFrame) {
        this.delayBetweenFrame = delayBetweenFrame;
    }
    public List<Component> getListFrames() {
        return listFrames;
    }
    public int getDelayBetweenFrame() {
        return delayBetweenFrame;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
