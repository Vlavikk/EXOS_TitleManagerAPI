package vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTimeUpdate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task.AbstractTask;

public class AsyncGameTimePlayer extends AbstractTask {
    private final Player player;
    private int startTime;
    private int activeTime;
    public AsyncGameTimePlayer(Player player){
        this.player = player;
    }
    public void send(){
        cancelTask();
        sendPacket();
        startTask();
    }
    private void sendPacket(){
        WrapperPlayServerTimeUpdate packet = new WrapperPlayServerTimeUpdate(startTime, player.getLocation().getWorld().getTime(),true);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player,packet);
    }
    private void startTask(){
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                GameTimeManager.playersAsyncTime.remove(player.getName());
                cancel();
            }
        }.runTaskLater(EXOS_TitleManagerAPI.getInstance(),activeTime);
        setTask(task);
    }
    private void cancelTask(){
        canselTask();
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }
}
