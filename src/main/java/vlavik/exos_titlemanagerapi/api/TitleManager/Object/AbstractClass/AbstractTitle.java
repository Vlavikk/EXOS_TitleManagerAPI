package vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.Events.ExTitleEndEvent;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExBossBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task.AbstractTask;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.Optional;

public abstract class AbstractTitle extends AbstractTask {
    private int time;
    private TitleType type;
    private boolean isForced;
    private IgnoredType ignoredType;
    private ForceType forceType = ForceType.SAVE;
    private boolean isAnimation = false;
    private Optional<SoundSettings> sound = Optional.empty();
    public abstract void sendLogic(TitlePlayer player);
    public void send(TitlePlayer titlePlayer){
        if (time == 0) titlePlayer.next(type);
        else sendLogic(titlePlayer);
    }
    public void pause(TitlePlayer titlePlayer,IgnoredType action){
        canselTask();
        stopTimer();
        if (type == TitleType.TITLE) SendPacket.setSubTitle(titlePlayer.getPlayer(),Component.text(""));

        int deleteTime = time;
        switch (action){
            case DELETE -> deleteTime ++;// больше не проиграется и удалиться из списка PS: time + 1 никогда не может быть такого
            case SAVE -> deleteTime = 20;} //если остаточное время больше 20, то сохранится : нет
        time = time - getTimerTime() > deleteTime ? time - getTimerTime() : 0;
        Bukkit.getScheduler().runTask(EXOS_TitleManagerAPI.getInstance(),()->{
            Bukkit.getPluginManager().callEvent(new ExTitleEndEvent(titlePlayer,this));
        });
    }
    public void stop(TitlePlayer titlePlayer){
        if (type == TitleType.TITLE) SendPacket.setSubTitle(titlePlayer.getPlayer(),Component.text(""));
        canselTask();
        stopTimer();
        time = 0;
        Bukkit.getScheduler().runTask(EXOS_TitleManagerAPI.getInstance(),()->{
            Bukkit.getPluginManager().callEvent(new ExTitleEndEvent(titlePlayer,this));
        });
    }

    public void sendVoidMassage(TitlePlayer titlePlayer){
        Player player = titlePlayer.getPlayer();
        switch (type){
            case TITLE: SendPacket.sendTitle(player,Component.text(""),0,1,0, Optional.empty()); break;
            case BOSS_BAR: break; //SendPacket.removeBossBar(player);
            case ACTIONBAR: SendPacket.sendActionBar(player,Component.text(" "));break;
        }
    }
    public void cancelTitle(TitlePlayer titlePlayer){
        if (this instanceof ExBossBar bossBar){
            titlePlayer.getBossBarManager().removeBossBar(bossBar);
            SendPacket.removeBossBar(titlePlayer.getPlayer(),bossBar.getUuid());
        }else {
            Optional<AbstractTitle> abstractTitle = titlePlayer.getCurrentTitle(type);
            if(abstractTitle.isPresent() && abstractTitle.get().equals(this)){
                titlePlayer.next(type);
            }else titlePlayer.getList(type).remove(this);
        }
    }
    private boolean soundAlreadyPlay = false;
    protected void playSound(Player player){
        if (this.sound.isPresent()){
            SoundSettings sound = this.sound.get();
            if (soundAlreadyPlay && sound.playedOnce()) return;
            Utils.playSoundPlayer(player,sound.sound());
            if (sound.playedOnce()) soundAlreadyPlay = true;
        }
    }
    public record SoundSettings(Sound sound,boolean playedOnce){}
    public boolean isInfinity(){
        return time == -1;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public boolean isAnimation() {
        return isAnimation;
    }
    public void setAnimation(boolean animation) {
        isAnimation = animation;
    }
    public boolean isForced() {
        return isForced;
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
    public IgnoredType getIgnoredType() {
        return ignoredType;
    }
    public void setIgnoredType(IgnoredType... ignoredType) {
        IgnoredType type = IgnoredType.NONE;
        if (ignoredType.length != 0) type = ignoredType[0];
        this.ignoredType = type;
    }
    public TitleType getType() {
        return type;
    }
    public void setType(TitleType type) {
        this.type = type;
    }
    public void setSound(Sound sound,boolean playedOnes) {
        this.sound = Optional.of(new SoundSettings(sound,playedOnes));
    }
    public Optional<SoundSettings> getSoundSettings() {
        return sound;
    }
}
