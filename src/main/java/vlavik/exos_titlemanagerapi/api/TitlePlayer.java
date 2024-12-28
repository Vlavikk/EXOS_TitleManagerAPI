package vlavik.exos_titlemanagerapi.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.Title.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.Title.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.Title.Object.ExCustomTitle;
import vlavik.exos_titlemanagerapi.api.Title.Object.TitleTask;
import vlavik.exos_titlemanagerapi.api.Title.TitleEditable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class TitlePlayer implements TitleEditable {
    public static HashMap<String,TitlePlayer> titlePlayers = new HashMap<>();
    protected final Player player;
    protected CopyOnWriteArrayList<TitleTask> tasks;
    protected List<ExCustomTitle> actionBarList;
    protected List<ExCustomTitle> bossBarList;
    protected List<ExCustomTitle> titleList;
    private boolean egoistTask;
    public TitlePlayer(Player player){
        this.player = player;
        tasks = new CopyOnWriteArrayList<>();
        actionBarList = new ArrayList<>();
        bossBarList = new ArrayList<>();
        titleList = new ArrayList<>();
        titlePlayers.put(player.getName(),this);
    }

    @Override
    public <T> void send(TitleType type, T text, int time, IgnoredType... ignoredOtherType) {
        handler(Objects.requireNonNull(
                createCustomTitle(type, text, time, false, ignoredOtherType)));
    }

    @Override
    public <T> void forcedSend(TitleType type, T text, int time, IgnoredType... ignoredOtherType) {
        handler( Objects.requireNonNull(
                createCustomTitle(type, text, time, true, ignoredOtherType)));
    }

    @Override
    public <T> void addQueue(TitleType type, T text, int time, int numberInQueue, IgnoredType... ignoredOtherType) {
        ExCustomTitle title = Objects.requireNonNull(
                createCustomTitle(type, text, time, true,ignoredOtherType));
        List<ExCustomTitle> list = getList(type);
        if (numberInQueue == 0 || numberInQueue > list.size()-1){
            list.add(title);
        }
        else{
            list.add(numberInQueue,title);
        }

    }

    @Override
    public void removeQueue(TitleType type, int numberInQueue) {
        List<ExCustomTitle> list = getList(type);
        if (numberInQueue == 0 || numberInQueue > list.size()-1){}
        else list.remove(numberInQueue);
    }

    @Override
    public void cancel(boolean cancelAll,@NotNull TitleType... types) {
        for (TitleType type : types) {
            for (TitleTask task : tasks) {
                if (task.getType() == type){
                    if (cancelAll) task.getList().clear();
                    task.cancelTitle(cancelAll);
                }}}
    }
    @Override
    public void sendAnimation(TitleType type, List<Object> animationFrame, int time, int delay, IgnoredType... ignoredOtherType) {
        handler(Objects.requireNonNull(
                createCustomTitleAnimation(type, animationFrame, time, false, delay,ignoredOtherType)));
    }

    @Override
    public void forcedSendAnimation(TitleType type, List<Object> animationFrame, int time, int delay, IgnoredType... ignoredOtherType) {
        handler(Objects.requireNonNull(
                createCustomTitleAnimation(type, animationFrame, time, true, delay,ignoredOtherType)));
    }
    public static TitlePlayer getTitlePlayer(Player player){
        return titlePlayers.getOrDefault(player.getName(),new TitlePlayer(player));
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
    public CopyOnWriteArrayList<TitleTask> getTasks() {
        return tasks;
    }





    private synchronized void handler(ExCustomTitle title) {
        TitleType type = title.getType();
        List<ExCustomTitle> list = getList(type);
        if (!listIsContainsType(type)){
            TitleTask titleTask = new TitleTask(type,player,list);
            titleTask.addTitle(title);
            tasks.add(titleTask);
            if (!isEgoistTask()) titleTask.start();
        }else {
           getTaskByType(type).addTitle(title);
        }
    }
    private ExCustomTitle createCustomTitle(TitleType type, Object text, int time, boolean forced, IgnoredType... ignoredOtherType) {
        try {
            IgnoredType ignoredType = ignoredOtherType.length == 0 ? IgnoredType.NONE : ignoredOtherType[0];
            return new ExCustomTitle(type, text, time, forced, ignoredType);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    private ExCustomTitle createCustomTitleAnimation(TitleType type, List<Object> list, int time, boolean forced, int delay, IgnoredType... ignoredOtherType){
        try {
            IgnoredType ignoredType = ignoredOtherType.length == 0 ? IgnoredType.NONE : ignoredOtherType[0];
            return new ExCustomTitle(type, list, time, forced,delay, ignoredType);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ExCustomTitle> getList(TitleType type){
        return switch (type) {
            case TITLE -> titleList;
            case BOSS_BAR -> bossBarList;
            case ACTIONBAR -> actionBarList;
        };
    }
    protected boolean listIsContainsType(TitleType type){
        if (tasks == null) return false;
        boolean contains = false;
        for (TitleTask task : tasks){
            if (task.getType() == type){
                contains = true;
                break;
            }
        }
        return contains;
    }
    protected TitleTask getTaskByType(TitleType type){
        if (tasks == null) return null;
        for (TitleTask task : tasks){
            if (task.getType() == type) return task;
        }
        return null;
    }
}
