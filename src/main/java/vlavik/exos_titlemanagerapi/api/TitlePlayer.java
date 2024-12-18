package vlavik.exos_titlemanagerapi.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.Title.Object.ExCustomTitle;
import vlavik.exos_titlemanagerapi.api.Title.Object.TitleTask;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TitlePlayer implements TitleEditable {
    public static HashMap<String,TitlePlayer> titlePlayers = new HashMap<>();
    protected final Player player;
    protected ConcurrentHashMap<Type,TitleTask> tasks;
    protected List<ExCustomTitle> actionBarList;
    protected List<ExCustomTitle> bossBarList;
    protected List<ExCustomTitle> titleList;
    private boolean egoistTask;
    public TitlePlayer(Player player){
        this.player = player;
        tasks = new ConcurrentHashMap<>();
        actionBarList = new ArrayList<>();
        bossBarList = new ArrayList<>();
        titleList = new ArrayList<>();
        titlePlayers.put(player.getName(),this);
    }

    @Override
    public <T> void send(Type type, T text, int time, boolean... ignoredOtherType) {
        handler(Objects.requireNonNull(
                createCustomTitle(type, text, time, false, ignoredOtherType)));
    }

    @Override
    public <T> void forcedSend(Type type, T text, int time, boolean... ignoredOtherType) {
        handler( Objects.requireNonNull(
                createCustomTitle(type, text, time, true, ignoredOtherType)));
    }

    @Override
    public <T> void addQueue(Type type, T text, int time, int numberInQueue, boolean... ignoredOtherType) {
        ExCustomTitle title = Objects.requireNonNull(
                createCustomTitle(type, text, time, true, ignoredOtherType));
        List<ExCustomTitle> list = getList(type);
        if (numberInQueue == 0 || numberInQueue > list.size()-1){
            list.add(title);
        }
        else{
            list.add(numberInQueue,title);
        }

    }

    @Override
    public void removeQueue(Type type, int numberInQueue) {
        List<ExCustomTitle> list = getList(type);
        if (numberInQueue == 0 || numberInQueue > list.size()-1){
            return;
        }
        else{
            list.remove(numberInQueue);
        }
    }

    @Override
    public void cancel(boolean cancelAll,@NotNull Type... types) {
        for (Type type : types) {
            for (TitleTask task : tasks.values()) {
                if (task.getType() == type){
                    if (cancelAll){
                        task.getList().clear();
                        task.cancelTitle(cancelAll);
                    }else {
                        task.getList().remove(0);
                    }
                }
            }
        }
    }

    @Override
    public <T> void sendAnimation(Type type, List<T> animationFrame, int time, int delay, boolean... ignoredOtherType) {

    }

    @Override
    public <T> void forcedSendAnimation(Type type, List<T> animationFrame, int time, int delay, boolean... ignoredOtherType) {

    }
    public static TitlePlayer getTitlePlayer(Player player){
        return titlePlayers.getOrDefault(player.getName(),null);
    }
    public Player getPlayer() {
        return player;
    }
    public boolean isEgoistTask() {
        return egoistTask;
    }
    public void setEgoistTask(boolean egoistTask) {
        this.egoistTask = egoistTask;
    }

    public ConcurrentHashMap<Type, TitleTask> getTasks() {
        return tasks;
    }

    private void handler(ExCustomTitle title) {
        Type type = title.getType();
        List<ExCustomTitle> list = getList(type);
        if (!tasks.containsKey(type)){
            TitleTask titleTask = new TitleTask(type,player,list,title);
            tasks.put(type,titleTask);
            titleTask.addTitle(title);
            if (!isEgoistTask()) titleTask.runTask();
            Bukkit.broadcastMessage(tasks.toString());
            Bukkit.broadcastMessage("создал");
        }else {
           tasks.get(type).addTitle(title);
            Bukkit.broadcastMessage("не создал");
        }
    }
    private ExCustomTitle createCustomTitle(Type type, Object text, int time, boolean forced, boolean... ignoredOtherType) {
        try {
            ExCustomTitle.IgnoredType ignoredType;
            if (ignoredOtherType.length == 0) ignoredType = ExCustomTitle.IgnoredType.NONE;
            else ignoredType = ignoredOtherType[0] ? ExCustomTitle.IgnoredType.DELETE : ExCustomTitle.IgnoredType.SAVE;

            return new ExCustomTitle(type, text, time, forced, ignoredType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ExCustomTitle> getList(Type type){
        List<ExCustomTitle> list = null;
        switch (type){
            case TITLE:
                list = titleList;
                break;
            case BOSS_BAR:
                list = bossBarList;
                break;
            case ACTIONBAR:
                list = actionBarList;
                break;
        }
        return list;
    }
}
