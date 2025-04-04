package vlavik.exos_titlemanagerapi.api.TitleManager.TitlePattern;


import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.IgnoredType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExBossBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExTitle;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

public class TitlePattern {
    private final TitlePlayer titlePlayer;
    public TitlePattern(TitlePlayer titlePlayer){
        this.titlePlayer = titlePlayer;
    }

    private final String text = "\u1900";
    public ExBossBar sendDarkInBossBar(int time, boolean force, IgnoredType... ignoredType){
        ExBossBar exBossBar = new ExBossBar(text,time,force,ignoredType);
        titlePlayer.send(exBossBar);
        return exBossBar;
    }
    public ExActionBar sendDarkInActionBar(int time, boolean force, IgnoredType... ignoredType){
        ExActionBar exActionBar = new ExActionBar(text,time,force,ignoredType);
        titlePlayer.send(exActionBar);
        return exActionBar;
    }
    public ExTitle sendDarkInTitle(int timeFadeIn,int time,int timeFadOut, boolean force, IgnoredType... ignoredType){
        ExTitle exTitle = new ExTitle(text,timeFadeIn,time,timeFadOut,force,ignoredType);
        titlePlayer.send(exTitle);
        return exTitle;
    }
}
