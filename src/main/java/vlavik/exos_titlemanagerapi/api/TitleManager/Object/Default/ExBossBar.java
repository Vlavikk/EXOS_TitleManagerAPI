package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractDefaultTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.UUID;

public class ExBossBar extends AbstractDefaultTitle {
    protected UUID uuid;
    {
        setType(TitleType.BOSS_BAR);
        uuid = UUID.randomUUID();
    }
    public <T> ExBossBar(T text, int time, IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setIgnoredType(ignoredType);
    }
    public <T> ExBossBar(T text,int time,boolean forced,IgnoredType... ignoredType){
        setText(text);
        setTime(time);
        setForce(forced);
        setIgnoredType(ignoredType);
    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        Player player = titlePlayer.getPlayer();
        if (isInfinity()) SendPacket.sendBossBar(player,getText(),uuid);
        else {
            SendPacket.sendBossBar(player,getText(),uuid);
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    cancelTitle(titlePlayer);
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),getTime(),2);
            playSound(player);
            setTask(task);
        }
    }
    public <T> void updateName(TitlePlayer titlePlayer,T newName){
        setText(newName);
        SendPacket.updateNameBossBar(titlePlayer.getPlayer(),getText(),uuid);
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
