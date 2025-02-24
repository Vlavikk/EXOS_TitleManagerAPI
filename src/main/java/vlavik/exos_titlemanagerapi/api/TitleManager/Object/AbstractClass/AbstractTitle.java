package vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task.AbstractTask;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

public abstract class AbstractTitle extends AbstractTask {
    private int time;
    private boolean isForced;
    private IgnoredType ignoredType;
    private boolean isSending = false;
    private int gameTime = 0;
    private boolean isAnimation = false;
    private TitleType type;
    public boolean send(TitlePlayer player){
        if (!isSending){
            sendLogic(player);
            isSending = true;
            return true;
        }
        return false;
    }
    public void pause(IgnoredType action){
        canselTask();
        stopTimer();

        int deleteTime = time;
        switch (action){
            case DELETE -> deleteTime ++;// больше не проиграется и удалиться из списка PS: time + 1 никогда не может быть такого
            case SAVE -> deleteTime = 20;} //если остаточное время больше 20, то сохраниться : нет
        time = time - getTimerTime() > deleteTime ? time - getTimerTime() : 0;
        isSending = false;
    }
    public void stop(){
        canselTask();
        stopTimer();
        time = 0;
        isSending = false;
    }
    public abstract void sendLogic(TitlePlayer player);
    public void sendVoidMassage(TitlePlayer titlePlayer){
        Player player = titlePlayer.getPlayer();
        switch (type){
            case TITLE: SendPacket.sendTitle(player,Component.text(" "),0,1,0); break;
            case BOSS_BAR: SendPacket.removeBossBar(player); break;
            case ACTIONBAR: SendPacket.sendActionBar(player,Component.text(" "));break;
        }
    }
    public boolean isInfinity(){
        return time == -1;
    }
    public boolean isSetGameTime(){
        return gameTime > 0;
    }
    public boolean isAnimation() {
        return isAnimation;
    }
    public int getTime() {
        return time;
    }
    public boolean isForced() {
        return isForced;
    }
    public IgnoredType getIgnoredType() {
        return ignoredType;
    }
    public int getGameTime() {
        return gameTime;
    }
    public TitleType getType() {
        return type;
    }
    public void setForced(boolean forced) {
        isForced = forced;
    }
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void setType(TitleType type) {
        this.type = type;
    }
    public void setAnimation(boolean animation) {
        isAnimation = animation;
    }
    public void setIgnoredType(IgnoredType... ignoredType) {
        IgnoredType type = IgnoredType.NONE;
        if (ignoredType.length != 0) type = ignoredType[0];
        this.ignoredType = type;
    }
}
