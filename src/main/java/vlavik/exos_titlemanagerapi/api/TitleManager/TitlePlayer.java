package vlavik.exos_titlemanagerapi.api.TitleManager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.TitleType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation.ExActionBarAnimation;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation.ExBossBarAnimation;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Animation.ExTitleAnimation;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExBossBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePattern.TitlePattern;

import java.util.*;

public class TitlePlayer implements TitleEditable {
    public static HashMap<String,TitlePlayer> titlePlayers = new HashMap<>();

    private final Player player;
    private final TitlePattern patterns = new TitlePattern(this);
    private final List<AbstractTitle> actionBarList = new ArrayList<>();;
    private final List<AbstractTitle> bossBarList = new ArrayList<>();;
    private final List<AbstractTitle> titleList = new ArrayList<>();
    public TitlePlayer(Player player){
        this.player = player;
        titlePlayers.put(player.getName(),this);
    }


    @Override
    public void send(AbstractTitle... titles) {
        for (AbstractTitle title : titles){
            TitleType type = title.getType();
            List<AbstractTitle> list = getList(type);
            if (list.isEmpty()){
                list.addFirst(title);
                sendInPlayerScreen(title);
            }
            else if (title.isForced()){
                getCurrentTitle(type).ifPresent(t -> {
                    if (title.getForceType() == ForceType.DELETE){
                        list.removeFirst();
                        t.stop(this);
                    }else t.pause(this,IgnoredType.NONE);
                    list.addFirst(title);
                    sendInPlayerScreen(title);
                });
            }
            else list.add(title);
        }
    }

    @Override
    public void send(AbstractNotification... notifications) {
        Arrays.stream(notifications).forEach(
                notification -> notification.send(this));
    }

    @Override
    public <T> void send(TitleType type, T text, int time, IgnoredType... ignoredOtherType) {
        send(createTitle(type,text,time,false,ignoredOtherType));
    }

    @Override
    public <T> void forceSend(TitleType type, T text, int time, IgnoredType... ignoredOtherType) {
        send(createTitle(type,text,time,true,ignoredOtherType));
    }

    @Override
    public void sendAnimation(TitleType type, List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(type,animationFrame,time,delayBetweenFrame,false,ignoredOtherType));
    }

    @Override
    public void forcedSendAnimation(TitleType type, List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(type,animationFrame,time,delayBetweenFrame,true,ignoredOtherType));
    }

    @Override
    public <T> void sendActionBar(T text, int time, IgnoredType... ignoredOtherType) {
        send(createTitle(TitleType.ACTIONBAR,text,time,false,ignoredOtherType));
    }

    @Override
    public <T> void sendBossBar(T text, int time, IgnoredType... ignoredOtherType) {
        send(createTitle(TitleType.BOSS_BAR,text,time,false,ignoredOtherType));
    }

    @Override
    public <T> void sendTitle(T text, int time, IgnoredType... ignoredOtherType) {
        send(createTitle(TitleType.TITLE,text,time,false,ignoredOtherType));
    }

    @Override
    public <T> void sendTitle(T text, int timeFadeIn, int time, int timeFadeOut, IgnoredType... ignoredOtherType) {
        send(new ExTitle(text,timeFadeIn,time,timeFadeOut,ignoredOtherType));
    }

    @Override
    public <T> void sendActionBar(T text, int time, boolean forced, IgnoredType... ignoredOtherType) {
        send(createTitle(TitleType.ACTIONBAR,text,time,forced,ignoredOtherType));
    }

    @Override
    public <T> void sendBossBar(T text, int time, boolean forced, IgnoredType... ignoredOtherType) {
        AbstractTitle title = createTitle(TitleType.BOSS_BAR,text,time,forced,ignoredOtherType);
        if (title != null) send(title);
    }

    @Override
    public <T> void sendTitle(T text, int time, boolean forced, IgnoredType... ignoredOtherType) {
        send(createTitle(TitleType.TITLE,text,time,forced,ignoredOtherType));
    }

    @Override
    public <T> void sendTitle(T text, int timeFadeIn, int time, int timeFadeOut, boolean forced, IgnoredType... ignoredOtherType) {
        send(new ExTitle(text,timeFadeIn,time,timeFadeOut,forced,ignoredOtherType));
    }

    @Override
    public void sendAnimationActionBar(List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.ACTIONBAR,animationFrame,time,delayBetweenFrame,false,ignoredOtherType));
    }

    @Override
    public void sendAnimationBossBar(List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.BOSS_BAR,animationFrame,time,delayBetweenFrame,false,ignoredOtherType));
    }

    @Override
    public void sendAnimationTitle(List<?> animationFrame, int time, int delayBetweenFrame, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.TITLE,animationFrame,time,delayBetweenFrame,false,ignoredOtherType));
    }

    @Override
    public void sendAnimationActionBar(List<?> animationFrame, int time, int delayBetweenFrame, boolean forced, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.ACTIONBAR,animationFrame,time,delayBetweenFrame,forced,ignoredOtherType));
    }

    @Override
    public void sendAnimationBossBar(List<?> animationFrame, int time, int delayBetweenFrame, boolean forced, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.BOSS_BAR,animationFrame,time,delayBetweenFrame,forced,ignoredOtherType));
    }

    @Override
    public void sendAnimationTitle(List<?> animationFrame, int time, int delayBetweenFrame, boolean forced, IgnoredType... ignoredOtherType) {
        send(createTitleAnimation(TitleType.TITLE,animationFrame,time,delayBetweenFrame,forced,ignoredOtherType));
    }

    @Override
    public void addQueue(AbstractTitle title,int numberInQueue) {
        TitleType titleType = title.getType();
        List<AbstractTitle> list = getList(titleType);
        list.add(numberInQueue,title);
    }

    @Override
    public void removeQueue(TitleType type, int numberInQueue) {
        List<AbstractTitle> list = getList(type);
        list.remove(numberInQueue);
    }

    @Override
    public void cancel(@NotNull TitleType... types) {
        Arrays.stream(types).forEach(type ->{
            getCurrentTitle(type).ifPresent( title ->{
                title.stop(this);
                title.sendVoidMassage(this);
            });
            getList(type).clear();
        });
    }

    @Override
    public void next(TitleType type) {
        List<AbstractTitle> list = getList(type);
        getCurrentTitle(type).ifPresent(title -> {
            title.stop(this);
            list.removeFirst();

            boolean nextTitleNotIgnoredType = list.isEmpty() || list.getFirst().getIgnoredType() == IgnoredType.NONE;

            if (title.getIgnoredType() != IgnoredType.NONE && nextTitleNotIgnoredType){
                Arrays.stream(TitleType.values())
                        .filter(t -> t != type)
                        .forEach(t ->
                        getCurrentTitle(t).ifPresent(p -> p.send(this)
                ));
            }
            if (!list.isEmpty()) sendInPlayerScreen(list.getFirst());
        });
    }

    public static TitlePlayer getTitlePlayer(Player player){
        if (titlePlayers.containsKey(player.getName())) return titlePlayers.get(player.getName());
        else return new TitlePlayer(player);
    }
    public void terminate(){
        titlePlayers.remove(player.getName());
    }
    private void sendInPlayerScreen(AbstractTitle title){
        if (title.getIgnoredType() != IgnoredType.NONE){
            Arrays.stream(TitleType.values())
                    .filter(type -> type != title.getType())
                    .forEach(type -> getCurrentTitle(type)
                            .ifPresent(p -> {
                                p.pause(this,title.getIgnoredType());
                                p.sendVoidMassage(this);
                            }));
        }
        title.send(this);
    }

    private <T> AbstractTitle createTitle(TitleType type, T text, int time,boolean force,IgnoredType... ignoredOtherType){
        AbstractTitle title = null;
        switch (type) {
            case TITLE -> title = new ExTitle(text,time,force,ignoredOtherType);
            case BOSS_BAR -> title = new ExBossBar(text,time,force,ignoredOtherType);
            case ACTIONBAR -> title = new ExActionBar(text,time,force,ignoredOtherType);
        }
        return title;
    }
    private AbstractTitle createTitleAnimation(TitleType type, List<?> animationFrame, int time, int delayBetweenFrame,boolean force, IgnoredType... ignoredOtherType){
        AbstractTitle title = null;
        switch (type) {
            case TITLE -> title = new ExTitleAnimation(animationFrame, time, delayBetweenFrame, force, ignoredOtherType);
            case BOSS_BAR -> title = new ExBossBarAnimation(animationFrame, time, delayBetweenFrame, force, ignoredOtherType);
            case ACTIONBAR -> title = new ExActionBarAnimation(animationFrame, time, delayBetweenFrame, force, ignoredOtherType);
        }
        return title;
    }

    public List<AbstractTitle> getList(TitleType type){
        return switch (type) {
            case TITLE -> titleList;
            case BOSS_BAR -> bossBarList;
            case ACTIONBAR -> actionBarList;
        };
    }
    public Optional<AbstractTitle> getCurrentTitle(TitleType type){
        List<AbstractTitle> list = getList(type);
        if (list.isEmpty()) return Optional.empty();
        return Optional.of(list.getFirst());
    }
    public TitlePattern getTitlePatterns(){
        return patterns;
    }
    public Player getPlayer() {
        return player;
    }
}
