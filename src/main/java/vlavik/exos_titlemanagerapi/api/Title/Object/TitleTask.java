package vlavik.exos_titlemanagerapi.api.Title.Object;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.Title.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.Title.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.Title.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TitleTask {
    private final TitlePlayer titlePlayer;
    private final Player player;
    private final List<ExCustomTitle> list;
    private final CopyOnWriteArrayList<TitleTask> tasks;
    private final TitleType type;
    private final Task task = new Task();
    private final Handler handler = new Handler();
    private final AnimationTools animationTools = new AnimationTools();

    public TitleTask(TitleType type, Player player, List<ExCustomTitle> list) {
        this.player = player;
        this.titlePlayer = TitlePlayer.getTitlePlayer(player);
        this.tasks  = titlePlayer.getTasks();
        this.list = list;
        this.type = type;
    }

    class Task{
        public BukkitTask runnable;
        public BukkitTask timer;
        public ExCustomTitle title;
        public int time;

        public void skip(){
                remove();
                if (!list.isEmpty()){
                    send();
                    if (titlePlayer.isEgoistTask() && title.getIgnoreOtherType() == IgnoredType.NONE) handler.afterIgnoredOther();
                }
                else{
                    if (titlePlayer.isEgoistTask()) handler.afterIgnoredOther();
                    cancel();
                }
        }
        public void send(){
            if (!list.isEmpty()) title = list.get(0);
            else {cancelTitle(true);return;}
            cancel(runnable);
            resetTimer();
            handler.sendMessage(title);
        }
        public void saveTitle(){
            if (list.isEmpty() || title == null || title.getTime() == -1) return;
            if (title.getTime() - time <= 20){ //Если время у текста остается не больше 20 тиков он удаляется
                remove();
            }else {
                title.setTime(title.getTime() - time);
            }
        }
        public void remove(){
            if (title != null){
                list.remove(title);
                title = null;
            }
            if (list.isEmpty()) cancelTitle(true);
        }
        public void cancel(BukkitTask... tasks){
            if (tasks.length == 0) {
                if (task.runnable != null && !task.runnable.isCancelled()) runnable.cancel();
                if (task.timer != null && !task.timer.isCancelled()) timer.cancel();
                if (animationTools.animation != null && !animationTools.animation.isCancelled()) animationTools.animation.cancel();
            }else {
                for (BukkitTask bukkitTask : tasks){
                    if (bukkitTask != null && !bukkitTask.isCancelled()) bukkitTask.cancel();
                }
            }
        }
        public void startTimer(){
            timer = new BukkitRunnable() {
                @Override
                public void run() {
                    time++;
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),1,1);
        }
        public void resetTimer(){
            cancel(timer);
            startTimer();
            time = 0;
        }
    }
    class Handler{
        public Component getComponent(ExCustomTitle title){
            return title.getText() instanceof Component ?
                    (Component) title.getText() : Utils.convertToComponent((String) title.getText());
        } //Конверт в Component
        public List<Component> getListComponent(ExCustomTitle title){
            List<Component> list = new ArrayList<>();
            if (title.getAnimationFrame().get(0) instanceof Component){
                for (Object object : title.getAnimationFrame()){
                    Component text = (Component) object;
                    list.add(text);
                }
            }
            else {
                for (Object object : title.getAnimationFrame()){
                    String text = (String) object;
                    list.add(Utils.convertToComponent(text));
                }
            }
            return list;
        }
        public void executorIgnoredOther(ExCustomTitle title){
            IgnoredType ignoredType = title.getIgnoreOtherType();
            if (title.getIgnoreOtherType() != IgnoredType.NONE){
                titlePlayer.setEgoistTask(true);
                for (TitleTask task : tasks){
                    if (task.getType() == title.getType()) continue;
                    switch (ignoredType) {
                        case SAVE: task.task.saveTitle();
                            break;
                        case DELETE: task.task.remove();
                            break;
                    }
                    task.task.cancel();
                    task.handler.sendVoidMessage();
                }
            }
        } //Останавливает все тайтлы по мимо основного;
        public void afterIgnoredOther(){
            titlePlayer.setEgoistTask(false);
            for (TitleTask task : tasks) {
                if (task.getType() == type) continue;
                if (!titlePlayer.isEgoistTask()) task.task.send();
            }
        }
        public void sendMessage(ExCustomTitle title){
            handler.executorIgnoredOther(title);
            BukkitTask bukkitTask = null;
            boolean infinite = title.getTime() == -1;
            switch (type){
                case TITLE:
                    if (title.isAnimation()){
                        bukkitTask = handler.sendAnimation(title,infinite);
                        animationTools.sendAnimatedTitle(title);
                    }else bukkitTask = handler.sendTitle(title,infinite);
                    break;
                case BOSS_BAR:
                    if (title.isAnimation()){
                        bukkitTask = handler.sendAnimation(title,infinite);
                        animationTools.sendAnimatedBossBar(title);
                    }else bukkitTask = handler.sendBossBar(title,infinite);
                    break;
                case ACTIONBAR:
                    if (title.isAnimation()){
                        bukkitTask = handler.sendAnimation(title,infinite);
                        animationTools.sendAnimatedActionBar(title);
                    }else bukkitTask  = handler.sendActionBar(title,infinite);
                    break;
            }
            if (bukkitTask != null) task.runnable = bukkitTask;
        }
        public void sendVoidMessage(){
            switch (type){
                case TITLE: SendPacket.sendTitle(player,Component.text(" ")); break;
                case BOSS_BAR: SendPacket.removeBossBar(player); break;
                case ACTIONBAR: SendPacket.sendActionBar(player,Component.text(" "));break;
            }
        }
        public BukkitTask sendAnimation(ExCustomTitle title,boolean isInfinite){
            return new BukkitRunnable() {
                @Override
                public void run() {
                    if (isInfinite) return;
                    animationTools.animation.cancel();
                    cancel();
                    task.skip();
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),title.getTime(),60);
        }
        public BukkitTask sendTitle(ExCustomTitle title,boolean isInfinite){
            int period = isInfinite ? 60 : title.getTime();
            Component text = getComponent(title);
                return new BukkitRunnable() {
                    private boolean skip = false;
                    @Override
                    public void run() {
                        if (isInfinite) SendPacket.sendTitle(player,text,period);
                        else {
                            if (!skip){
                            SendPacket.sendTitle(player, text, title.getTime());
                            skip = true;
                            }else{
                                task.skip();
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        }
        public BukkitTask sendBossBar(ExCustomTitle title,boolean isInfinite){
            Component text = getComponent(title);
            if (isInfinite) SendPacket.sendBossBar(player,text);
            else {
                SendPacket.sendBossBar(player,text);
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        SendPacket.removeBossBar(player);
                        task.skip();
                        cancel();
                    }
                }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),title.getTime(),2);
            }
            return null;
        }
        public BukkitTask sendActionBar(ExCustomTitle title,boolean isInfinite){
            Component text = getComponent(title);
            int titleTime = title.getTime();
            int period = isInfinite ? 40 :
                    (titleTime <= 40 ? titleTime : (titleTime % 5 == 0 ? (titleTime % 40 == 0 ? 40 : 5) : (titleTime % 2 == 0 ? 2 : 1)));
            int cycleCount = isInfinite ? 0 : titleTime / period;
            return new BukkitRunnable() {
            private int cycle = 0;
                @Override
                public void run() {
                    if (isInfinite) SendPacket.sendActionBar(player,text);
                    else {
                        if (cycle >= cycleCount){
                            task.skip();
                            cancel();
                            return;
                        }
                        if ((cycle * period) % 40 == 0){
                            SendPacket.sendActionBar(player,text);
                        }
                        cycle++;
                    }
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        }
    }
    class AnimationTools{
        public BukkitTask animation;
        public void sendAnimatedActionBar(ExCustomTitle title){
            List<Component> list = handler.getListComponent(title);
            animation = new BukkitRunnable() {
                private int frame = 0;
                @Override
                public void run() {
                    if (frame >= list.size()) frame = 0;
                    SendPacket.sendActionBar(player,list.get(frame));
                    frame++;
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,title.getDelayAnimation());
        }
        public void sendAnimatedBossBar(ExCustomTitle title){
            List<Component> list = handler.getListComponent(title);
            animation = new BukkitRunnable() {
                private int frame = 0;
                @Override
                public void run() {
                    if (frame >= list.size()) frame = 0;
                    SendPacket.updateNameBossBar(player,list.get(frame));
                    frame++;
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,title.getDelayAnimation());
        }
        public void sendAnimatedTitle(ExCustomTitle title){
            List<Component> list = handler.getListComponent(title);
            animation = new BukkitRunnable() {
                private int frame = 0;
                @Override
                public void run() {
                    if (frame >= list.size()) frame = 0;
                    SendPacket.sendTitle(player,list.get(frame),title.getDelayAnimation()+1);
                    frame++;
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,title.getDelayAnimation());
        }
    }
    public void start(){
        task.send();
    }
    public void addTitle(ExCustomTitle title){
        if (title.getTime() == 0) return;
        if (title.isForced()){
            list.add(0,title);
        }else {
            list.add(title);
        }
    }
    public void cancelTitle(boolean fullStop){
        if (fullStop){
            handler.afterIgnoredOther();
            task.cancel();
            tasks.remove(this);
            handler.sendVoidMessage();
            return;
        }
        list.remove(task.title);
        task.skip();
    }
    public TitleType getType() {
        return type;
    }
    public List<ExCustomTitle> getList() {
        return list;
    }
}
