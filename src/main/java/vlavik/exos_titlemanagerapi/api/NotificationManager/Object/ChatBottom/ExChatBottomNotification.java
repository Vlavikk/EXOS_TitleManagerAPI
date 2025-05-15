package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationPlace;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime.GameTimes;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class ExChatBottomNotification extends AbstractNotification {
    {
        setPlace(NotificationPlace.BOTTOM_CHAT);
    }
    private Optional<SoundSettings> sound = Optional.empty();
    private final HashMap<TitlePlayer,ExActionBar> syncActionBars = new HashMap<>();

    public <T> ExChatBottomNotification(NotificationType type, T text){
        setType(type);
        setText(text);
    }
    public <T> ExChatBottomNotification(NotificationType type, T text, boolean force){
        setType(type);
        setForce(force);
        setText(text);
    }
    private static HashMap<Integer,String> ones = new HashMap<>(Map.of(
            -1,"ющъы",
            1,")(-><=_f{}",
            2,"*[]t",
            3,"l",
            4,"!;:i.,"
    ));

    @Override
    public void send(TitlePlayer titlePlayer) {
        boolean isSending = syncActionBars.containsKey(titlePlayer) && syncActionBars.get(titlePlayer).isSending();
        GameTimes.ChatBottomNotification animation = isSending ? GameTimes.ChatBottomNotification.SHAKE : GameTimes.ChatBottomNotification.FADE_IN;

        //Гей тайм
        GameTimes.ChatBottomNotification gameTime;
        if (getType() == NotificationType.ERROR) gameTime = animation;
        else gameTime = GameTimes.ChatBottomNotification.FADE_IN;

        //Базовая настройка
        ExActionBar actionBar = new ExActionBar(getText(),220,isForce(),this);
        actionBar.setGameTime(gameTime.getStartTime(), gameTime.getActiveTime());
        actionBar.setDefaultTimeFadeOut(true);
        actionBar.setForceType(ForceType.DELETE);

        //Звук
        getSound().ifPresent(s -> actionBar.setSound(
                animation == GameTimes.ChatBottomNotification.FADE_IN
                        ? s.fadeSound()
                        : s.shakeSound(),
                false
        ));

        syncActionBars.put(titlePlayer,actionBar);
        titlePlayer.send(actionBar);
    }
    @Override
    public Component formatter(String input){
        String[] lines = input.split("\n", 2);
        TextComponent.Builder result = Component.text();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i] != null ? lines[i] : "";
            LineColors colors = calculateColors(lines.length, i);
            TextComponent.Builder lineComponent = Component.text();

            String prefix = i == 0 ? "\u1901" : "\u1902";

            lineComponent.append(Component.text(prefix).color(TextColor.color(78, 92, 9)));
            TextColor currentOverrideColor = null;
            TextColor currentOverrideSpecColor = null;
            int colorIndex = 0;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c == '§' && j + 1 < line.length()) {
                    char code = line.charAt(++j);
                    currentOverrideColor = getColorFromCode(code, lines.length, i,false);
                    currentOverrideSpecColor = getColorFromCode(code, lines.length, i,true);
                    continue;
                }
                TextColor finalColor;
                if (isSpecialChar(c)){
                    if (currentOverrideSpecColor != null) finalColor = currentOverrideSpecColor;
                    else finalColor = colors.specialColor();
                }else{
                    if (currentOverrideColor != null) finalColor = currentOverrideColor;
                    else  finalColor = colors.baseColor();
                }
                finalColor = TextColor.color(finalColor.red(),finalColor.green(),Math.min(Math.max(finalColor.blue() + colorIndex,0),255));

                for (Map.Entry<Integer, String> entry : ones.entrySet()){
                    if (entry.getValue().contains(String.valueOf(c))){
                        colorIndex = colorIndex + (entry.getKey());
                    }
                }

                if (c == ' ') {
                    colorIndex = colorIndex + 2; // смещение после пробела
                    lineComponent.append(Component.text(".").color(TextColor.color(78, 92, 7)));
                }
                else lineComponent.append(Component.text(c).color(finalColor));

            }
            result.append(lineComponent);

        }
        return result.build();
    }
    private TextColor getColorFromCode(char code, int totalLines, int lineIndex,boolean isSpec) {
        int baseValue;
        baseValue = isSpec ? getSpecialValue(totalLines, lineIndex):getBaseValue(totalLines, lineIndex);
        int offset = switch (code) {
            case 'f' -> 0; // Белый
            case 'c' -> 1; // Красный
            case 'a' -> 2; // Зеленый
            case 'e' -> 3; // Желтый
            default -> 0;
        };
        return TextColor.color(78, Math.min(baseValue + offset,255),0);
    }
    // Метод расчета цветов
    private LineColors calculateColors(int totalLines, int lineIndex) {
        int baseB = getBaseValue(totalLines, lineIndex) ;
        int specialB = getSpecialValue(totalLines, lineIndex);

        return new LineColors(
                TextColor.color(78, Math.min(baseB, 255), 0),
                TextColor.color(78, Math.min(specialB, 255), 0)
        );
    }

    // Базовые значения для обычных символов
    private int getBaseValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 68 : (lineIndex == 0 ? 72 : 76);
    }

    // Базовые значения для спецсимволов
    private int getSpecialValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 80 : (lineIndex == 0 ? 84 : 88);
    }
    private boolean isSpecialChar(char c) {
        return c == 'ё' || c == 'й' ||c == 'Ё' ||c == 'Й';
    }
    record LineColors(TextColor baseColor, TextColor specialColor) {}

    public HashMap<TitlePlayer, ExActionBar> getHandlers() {
        return syncActionBars;
    }
    public record SoundSettings(Sound fadeSound, Sound shakeSound){}
    public void setSound(Sound fadeSound, Sound shakeSound) {
        this.sound = Optional.of(new SoundSettings(fadeSound,shakeSound));
    }
    public Optional<SoundSettings> getSound() {
        return sound;
    }
}
