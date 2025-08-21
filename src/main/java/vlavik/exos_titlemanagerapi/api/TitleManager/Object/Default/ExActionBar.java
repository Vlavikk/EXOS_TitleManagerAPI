package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom.ExChatBottomNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractDefaultTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class ExActionBar extends AbstractDefaultTitle {
    {
        setType(TitleType.ACTIONBAR);
    }
    private boolean defaultTimeFadeOut = false;
    private static final short DEFAULT_DELAY_FADE_OUT = 80;

    public <T> ExActionBar(T text, int time, IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setIgnoredType(ignoredType);
    }
    public <T> ExActionBar(T text,int time,boolean forced,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setForce(forced);
        setIgnoredType(ignoredType);
    }
    public <T> ExActionBar(T text,int time,boolean forced,ExChatBottomNotification exChatBottomNotification,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setForce(forced);
        setIgnoredType(ignoredType);
    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        Player player = titlePlayer.getPlayer();
        int updateTime = 30;
        int titleTime = defaultTimeFadeOut ? getTime() - (DEFAULT_DELAY_FADE_OUT - updateTime) : getTime();
        int period = isInfinity() ? updateTime :
                (titleTime <= updateTime ? titleTime : (titleTime % 5 == 0 ? (titleTime % updateTime == 0 ? updateTime : 5) : (titleTime % 2 == 0 ? 2 : 1)));
        int cycleCount = isInfinity() ? 0 : titleTime / period;
        BukkitTask task = new BukkitRunnable() {
            private int cycle = 0;
            @Override
            public void run() {
                if (isInfinity()) SendPacket.sendActionBar(player,getText());
                else {
                    if (cycle >= cycleCount){
                        if (defaultTimeFadeOut) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cancelTitle(titlePlayer);
                                }
                            }.runTaskLater(EXOS_TitleManagerAPI.getInstance(), DEFAULT_DELAY_FADE_OUT - updateTime);
                        }else titlePlayer.next(getType());
                    }
                    else if ((cycle * period) % updateTime == 0){
                        SendPacket.sendActionBar(player,getText());
                    }
                    cycle++;
                }
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        playSound(player);
        setTask(task);

    }
    public void setDefaultTimeFadeOut(boolean defaultTimeFadeOut) {
        this.defaultTimeFadeOut = defaultTimeFadeOut;
    }
    public boolean isDefaultTimeFadeOut() {
        return defaultTimeFadeOut;
    }
}
