package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractDefaultTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

public class ExActionBar extends AbstractDefaultTitle {
    {
        setType(TitleType.ACTIONBAR);
    }
    private boolean defaultTimeFadeOut = false;
    private static final short DEFAULT_DELAY_FADE_OUT = 20;

    public <T> ExActionBar(T text, int time, IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setIgnoredType(ignoredType);
    }
    public <T> ExActionBar(T text,int time,boolean forced,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setForced(forced);
        setIgnoredType(ignoredType);
    }
    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        Player player = titlePlayer.getPlayer();
        int titleTime = getTime();
        int period = isInfinity() ? 40 :
                (titleTime <= 40 ? titleTime : (titleTime % 5 == 0 ? (titleTime % 40 == 0 ? 40 : 5) : (titleTime % 2 == 0 ? 2 : 1)));
        int cycleCount = isInfinity() ? 0 : titleTime / period;
        BukkitTask task = new BukkitRunnable() {
            private int cycle = 0;
            @Override
            public void run() {
                if (isInfinity()) SendPacket.sendActionBar(player,getText());
                else {
                    if (cycle >= cycleCount){
                        titlePlayer.next(getType());
                        return;
                    }
                    if ((cycle * period) % 40 == 0){
                        SendPacket.sendActionBar(player,getText());
                    }
                    cycle++;
                }
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        setTask(task);

    }
    @Deprecated
    public void setDefaultTimeFadeOut(boolean defaultTimeFadeOut) { //TODO: Не работает
        this.defaultTimeFadeOut = defaultTimeFadeOut;
    }
    @Deprecated
    public boolean isDefaultTimeFadeOut() {
        return defaultTimeFadeOut;
    }
    @Deprecated
    public static int getDefaultTimeFadeOut(){
        return DEFAULT_DELAY_FADE_OUT;
    }

}
