package vlavik.exos_titlemanagerapi.api.TitleManager;

import vlavik.exos_titlemanagerapi.api.TitleManager.Object.AbstractClass.AbstractTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExBossBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerBossBarManager {
    private final TitlePlayer titlePlayer;
    private static final int BOSS_BAR_PER_PLAYER = 6;
    private final List<AbstractTitle> allBossBars = new ArrayList<>();
    private final HashMap<UUID,ExBossBar> activeBossBars = new HashMap<>();
    private final HashMap<UUID,ExBossBar> unactiveBossBars = new HashMap<>();
    public PlayerBossBarManager(TitlePlayer titlePlayer){
        this.titlePlayer = titlePlayer;
    }
    public void addBossBar(AbstractTitle b){
        if (b instanceof ExBossBar bossBar){
            allBossBars.add(bossBar);
            if (activeBossBars.size() < BOSS_BAR_PER_PLAYER) {
                activeBossBars.put(bossBar.getUuid(), bossBar);
                bossBar.send(titlePlayer);
            }else unactiveBossBars.put(bossBar.getUuid(),bossBar);
        }
    }
    public void removeBossBar(ExBossBar bossBar){
        allBossBars.remove(bossBar);
        if (activeBossBars.containsKey(bossBar.getUuid())){
            activeBossBars.remove(bossBar.getUuid());
            if(activeBossBars.size() < BOSS_BAR_PER_PLAYER){
                unactiveBossBars.values().stream().findFirst().ifPresent(
                        b -> b.send(titlePlayer)
                );
            }
        }else unactiveBossBars.remove(bossBar.getUuid());
    }

    public List<AbstractTitle> getAllBossBars() {
        return allBossBars;
    }
}
