package vlavik.exos_titlemanagerapi.api.TitleManager.Object.Task;

import org.bukkit.scheduler.BukkitTask;

import java.util.Optional;

public abstract class AbstractTask extends AbstractTimerTask{
    private Optional<BukkitTask> task = Optional.empty();
    protected void canselTask(){
        task.ifPresent(t -> {
            if (!t.isCancelled()) t.cancel();
        });
    }
    protected void setTask(BukkitTask task) {
        this.task = Optional.of(task);
    }
}
