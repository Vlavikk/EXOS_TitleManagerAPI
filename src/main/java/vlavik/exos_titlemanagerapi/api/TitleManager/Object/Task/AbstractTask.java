package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task;

import org.bukkit.scheduler.BukkitTask;

import java.util.Optional;

public abstract class AbstractTask extends AbstractTimerTask{
//    private int period;
//    private int delay;
    private Optional<BukkitTask> task;
    protected void canselTask(){
        task.ifPresent(t -> {
            if (!t.isCancelled()) t.cancel();
        });
    }

//    protected int getPeriod() {
//        return period;
//    }
//    protected void setPeriod(int period) {
//        this.period = period;
//    }
//    protected int getDelay() {
//        return delay;
//    }
//    protected void setDelay(int delay) {
//        this.delay = delay;
//    }

    protected void setTask(BukkitTask task) {
        this.task = Optional.of(task);
    }
}
