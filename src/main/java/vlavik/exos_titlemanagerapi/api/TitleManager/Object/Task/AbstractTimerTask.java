package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;

import java.util.Optional;

public abstract class AbstractTimerTask {

    private final int period = 2; //раз в какое время сохраняется текущее значение

    private int time = 0;
    private Optional<BukkitTask> task = Optional.empty();
    protected void startTimer(){
        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                time = time + period;
            }
        }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(), period, period);
        task = Optional.of(bukkitTask);
    }
    protected void stopTimer(){
        task.ifPresent(BukkitTask::cancel);
    }
    public int getTimerTime(){
        return time;
    }
}
