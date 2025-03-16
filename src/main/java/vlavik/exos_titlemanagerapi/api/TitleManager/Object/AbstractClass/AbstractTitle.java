package vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime.GameTimeManager;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task.AbstractTask;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public abstract class AbstractTitle extends AbstractTask {
    private int time;
    private boolean isForced;
    private ForceType forceType = ForceType.SAVE;
    private IgnoredType ignoredType;
    private boolean isSending = false;
    private GameTime gameTime = new GameTime(0,0);
    private boolean isAnimation = false;
    private TitleType type;
    public void send(TitlePlayer titlePlayer){
        if (!isSending){
            if (time == 0) titlePlayer.next(type);
            else{
                if (isSetGameTime()) GameTimeManager.sendTime(titlePlayer.getPlayer(),gameTime.getStartTime(),gameTime.getActiveTime());
                sendLogic(titlePlayer);
                isSending = true;
            }
        }
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
    private record GameTime(int getStartTime,int getActiveTime){}
    public boolean isSetGameTime(){
        return gameTime.getStartTime() > 0;
    }
    public void setGameTime(int gameTime,int activeTime) {
        this.gameTime = new GameTime(gameTime,activeTime);
    }
    public int getStartGameTime() {
        return gameTime.getStartTime();
    }
    public boolean isInfinity(){
        return time == -1;
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
    public TitleType getType() {
        return type;
    }
    public void setForce(boolean forced) {
        isForced = forced;
    }
    public void setForceType(ForceType type) {
        this.forceType = type;
    }
    public ForceType getForceType() {
        return forceType;
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
    public boolean isSending() {
        return isSending;
    }
    public void setIgnoredType(IgnoredType... ignoredType) {
        IgnoredType type = IgnoredType.NONE;
        if (ignoredType.length != 0) type = ignoredType[0];
        this.ignoredType = type;
    }
}
