package vlavik.exos_titlemanagerapi.api.Title.Object;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import vlavik.exos_titlemanagerapi.EXOS_TitleManagerAPI;
import vlavik.exos_titlemanagerapi.api.Title.Packets.SendPacket;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TitleTask {
    private final TitlePlayer titlePlayer;
    private final Player player;
    private final List<ExCustomTitle> list;
    private final ConcurrentHashMap<TitleEditable.Type, TitleTask> tasks;
    private final TitleEditable.Type type;
    private final Task task = new Task();
    private final Handler handler = new Handler();

    public TitleTask(TitleEditable.Type type, Player player, List<ExCustomTitle> list, ExCustomTitle title) {
        this.player = player;
        this.titlePlayer = TitlePlayer.getTitlePlayer(player);
        this.tasks  = titlePlayer.getTasks();
        this.list = list;
        this.type = type;
    }

    class Task{
        public BukkitTask runnable;
        public boolean isSending = false;
        public ExCustomTitle title;
        public int time = 0;

        /**
         * Сложни метод, непонятни метод <p></>
         * Посадил дед...печень
         */
        public void createTask(){
            if (!isSending){
                send();
            }
            if (title == null || title.getTime() == -1) return;
            runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!isSending){
                        send();
                    }
                    if (time > title.getTime()){
                        skip();
                    }
                    time++;
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,1);
        }
        public void skip(){
                if (title.getIgnoreOtherType() != ExCustomTitle.IgnoredType.NONE) handler.afterIgnoredOther(title);
                remove();
                if (!list.isEmpty()) title = list.get(0);
                else{
                    cancel(); return;}
                isSending = false;
                time = 0;
        }
        public void send(){
            if (!list.isEmpty()) title = list.get(0);
            else {cancelTitle(true);return;}
            if (title.getTime() == -1){
                if (runnable != null) cancel();
            }
            handler.sendMessage(title);
            isSending = true;
        }
        public void saveTitle(){
            if (list.isEmpty()) return;
            if (title.getTime() - time <= 20){ //Если время у текста остается не больше 20 тиков он удаляется
                remove();
            }else {
                title.setTime(title.getTime() - time);
            }
        }
        public void remove(){
            list.remove(title);
            if (list.isEmpty()) cancelTitle(true);
        }
        public void cancel(){
            task.runnable.cancel();
        }
    }
    class Handler{
        public Component getComponent(ExCustomTitle title){
            return title.getText() instanceof Component ?
                    (Component) title.getText() : Utils.convertToComponent((String) title.getText());
        } //Конверт в Component
        public void executorIgnoredOther(ExCustomTitle title){
            ExCustomTitle.IgnoredType ignoredType = title.getIgnoreOtherType();
            if (title.getIgnoreOtherType() != ExCustomTitle.IgnoredType.NONE){
                titlePlayer.setEgoistTask(true);
                for (TitleTask task : tasks.values()){
                    if (task.getType() == title.getType()) continue;
                    Bukkit.broadcastMessage("от "+title.getType()+ " сохраняю "+task.getType());
                    switch (ignoredType) {
                        case SAVE: task.task.runnable.cancel(); task.task.saveTitle();
                            break;
                        case DELETE: task.task.runnable.cancel(); task.task.remove();
                            break;
                    }
                    task.handler.sendVoidMessage();
                }
            }
        } //Останавливает все тайтлы по мимо основного;
        public void afterIgnoredOther(ExCustomTitle title){
            titlePlayer.setEgoistTask(false);
            for (TitleTask task : tasks.values()) {
                if (task.getType() == title.getType()) continue;
                task.task.createTask();
            }
        }
        public void sendMessage(ExCustomTitle title){
            handler.executorIgnoredOther(title);
            BukkitTask bukkitTask = null;
            boolean infinite = title.getTime() == -1;
            switch (type){
                case TITLE:
                    bukkitTask = handler.sendTitle(title,infinite);
                    break;
                case BOSS_BAR:
                    bukkitTask = task.runnable = handler.sendBossBar(title,infinite);
                    break;
                case ACTIONBAR:
                    bukkitTask = task.runnable = handler.sendActionBar(title,infinite);
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
        public BukkitTask sendTitle(ExCustomTitle title,boolean isInfinite){
            Component text = getComponent(title);
            if (!isInfinite) SendPacket.sendTitle(player,text,title.getTime());
            else {
                return new BukkitRunnable() {
                    @Override
                    public void run() {
                        SendPacket.sendTitle(player,text,30);
                    }
                }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,30);
            }
            return null;
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
                        cancel();
                    }
                }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),title.getTime(),1);
            }
            return null;
        }
        public BukkitTask sendActionBar(ExCustomTitle title,boolean isInfinite){
            Component text = getComponent(title);
            int period = isInfinite ? 40 : 1;
            return new BukkitRunnable() {
                private int time = 0;
                @Override
                public void run() {
                    if (isInfinite) SendPacket.sendActionBar(player,text);
                    else {
                        if (time > title.getTime()){cancel();return;}
                        if (time % 40 == 0) SendPacket.sendActionBar(player,text);
                        time++;
                    }
                }
            }.runTaskTimer(EXOS_TitleManagerAPI.getInstance(),0,period);
        }
    }
    public void addTitle(ExCustomTitle title){
        if (title.isForced()){
            list.add(0,title);
        }else {
            list.add(title);
        }
    }
    public void cancelTitle(boolean fullStop){
        if (fullStop){
            if (task.runnable != null && task.runnable.isCancelled()) task.cancel();
            tasks.remove(type);
            handler.sendVoidMessage();
            return;
        }
        list.remove(task.title);
        task.skip();
    }
    public void runTask(){
        task.createTask();
    }


    public TitleEditable.Type getType() {
        return type;
    }
    public List<ExCustomTitle> getList() {
        return list;
    }
}
