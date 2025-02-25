package vlavik.exos_titlemanagerapi.api.TitleManager.TitlePattern;


import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitlePlayer;

public class TitlePattern {
    private TitlePlayer titlePlayer;
    public TitlePattern(TitlePlayer titlePlayer){
        this.titlePlayer = titlePlayer;
    }

    private final String text = "\u1900";
    public void sendDarkInBossBar(int time, boolean force, IgnoredType... ignoredType){
        titlePlayer.sendBossBar(text,time,force,ignoredType);
    }
    public void sendDarkInActionBar(int time, boolean force, IgnoredType... ignoredType){
        titlePlayer.sendActionBar(text,time,force,ignoredType);
    }
    public void sendDarkInTitle(int timeFadeIn,int time,int timeFadOut, boolean force, IgnoredType... ignoredType){
        titlePlayer.sendTitle(text,timeFadeIn,time,timeFadOut,force,ignoredType);
    }
}
