package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.TitleUtils;

import java.util.HashMap;
import java.util.Optional;

public class ExChatBottomNotification extends ExActionBar {
    private static final HashMap<TitlePlayer, ExChatBottomNotification> syncNotifications = new HashMap<>();
    private Optional<SoundSettings> sound = Optional.empty();
    private final NotificationType type;
    private String overrideText;
    private TypeFade typeFade;

    public <T> ExChatBottomNotification(NotificationType type,T text) {
        super(text, 220);
        this.type = type;
        setForce(true);
    }
    public <T> ExChatBottomNotification(NotificationType type,T text,boolean force) {
        super(text, 220);
        this.type = type;
        setForce(force);
    }

    @Override
    public void sendLogic(TitlePlayer titlePlayer) {
        boolean isSending = syncNotifications.containsKey(titlePlayer) && overrideText.equals(syncNotifications.get(titlePlayer).getOverrideText());
        if(type == NotificationType.ERROR) typeFade = isSending ? TypeFade.SHAKE : TypeFade.FADE_IN;
        else typeFade = TypeFade.FADE_IN;

        syncNotifications.put(titlePlayer,this);
        super.text = formatter(overrideText,titlePlayer.getPlayer());
        setDefaultTimeFadeOut(true);

        sound.ifPresent(s -> super.setSound(
                typeFade == TypeFade.FADE_IN
                        ? s.fadeSound()
                        : s.shakeSound(),
                false
        ));
        super.sendLogic(titlePlayer);
    }
    @Override
    public <T> void setText(T text) {
        Optional<String> out = Optional.empty();
        if (text instanceof String){
            out = Optional.of((String) text);
        }else if (text instanceof Component component){
            StringBuilder builder = new StringBuilder();
            builder.append(((TextComponent) component).content());
            for (Component comp : component.children()){
                builder.append(((TextComponent) comp).content());
            }
            out = Optional.of(builder.toString());
        }
        out.ifPresent(value ->{
            this.overrideText = value;
        });
    }
    private Component formatter(String input,Player player){
        Component backGround = TitleUtils.formatImage("\uE000",Key.key("minecraft","utils/custom_utils"),player,typeFade == TypeFade.FADE_IN ? 0 : 1);
        Component text;
        if (input.split("\n",2).length >= 2){
            text = TitleUtils.formatText(input,2, player, typeFade == TypeFade.FADE_IN ? 2 : 4,true);
        }else {
            text = TitleUtils.formatText(input,1, player, typeFade == TypeFade.FADE_IN ? 0 : 1,true);
        }
        return backGround.append(text);
    }
    public void hashClear(TitlePlayer titlePlayer){
        syncNotifications.remove(titlePlayer);
    }
    public String getOverrideText() {
        return overrideText;
    }
    public record SoundSettings(Sound fadeSound, Sound shakeSound){}
    public void setSound(Sound fadeSound, Sound shakeSound) {
        this.sound = Optional.of(new SoundSettings(fadeSound,shakeSound));
    }
    public Optional<SoundSettings> getSound() {
        return sound;
    }
    enum TypeFade{
        FADE_IN,
        SHAKE;
    }
//    @Deprecated
//    private static class Formatter{
//        private final Component result;
//        public Formatter(String input,Player player){
//            this.result = formatter(input);
//        }
////        private Component formatter(String input,Player player){
////            return TitleUtils.formatText(input,2, player);
////        }
//
//        private static final HashMap<Integer,String> ones = new HashMap<>(Map.of(
//                -1,"ющъы",
//                1,")(-><=_f{}",
//                2,"*[]t",
//                3,"l",
//                4,"!;:i.,'|"
//        ));
//        private Component formatter(String input){
//            String[] lines = input.split("\n", 2);
//            TextComponent.Builder result = Component.text();
//            for (int i = 0; i < lines.length; i++) {
//                String line = lines[i] != null ? lines[i] : "";
//                LineColors colors = calculateColors(lines.length, i);
//                TextComponent.Builder lineComponent = Component.text();
//
//                Component prefix = Component.text(i == 0 ? "\uE001" : "\uE002").font(Key.key("minecraft","utils"));
//
//                lineComponent.append(prefix.color(TextColor.color(78, 92, 9)));
//                TextColor currentOverrideColor = null;
//                TextColor currentOverrideSpecColor = null;
//                int colorIndex = 0;
//                for (int j = 0; j < line.length(); j++) {
//                    char c = line.charAt(j);
//
//                    if (c == '§' && j + 1 < line.length()) {
//                        char code = line.charAt(++j);
//                        currentOverrideColor = getColorFromCode(code, lines.length, i,false);
//                        currentOverrideSpecColor = getColorFromCode(code, lines.length, i,true);
//                        continue;
//                    }
//                    TextColor finalColor;
//                    if (isSpecialChar(c)){
//                        if (currentOverrideSpecColor != null) finalColor = currentOverrideSpecColor;
//                        else finalColor = colors.specialColor();
//                    }else{
//                        if (currentOverrideColor != null) finalColor = currentOverrideColor;
//                        else  finalColor = colors.baseColor();
//                    }
//                    finalColor = TextColor.color(finalColor.red(),finalColor.green(),Math.min(Math.max(finalColor.blue() + colorIndex,0),255));
//
//                    for (Map.Entry<Integer, String> entry : ones.entrySet()){
//                        if (entry.getValue().contains(String.valueOf(c))){
//                            colorIndex = colorIndex + (entry.getKey());
//                        }
//                    }
//
//                    if (c == ' ') {
//                        colorIndex = colorIndex + 2; // смещение после пробела
//                        lineComponent.append(Component.text(".").color(TextColor.color(78, 92, 7)));
//                    }
//                    else lineComponent.append(Component.text(c).color(finalColor));
//
//                }
//                result.append(lineComponent);
//
//            }
//            return result.build();
//        }
//        private TextColor getColorFromCode(char code, int totalLines, int lineIndex,boolean isSpec) {
//            int baseValue;
//            baseValue = isSpec ? getSpecialValue(totalLines, lineIndex):getBaseValue(totalLines, lineIndex);
//            int offset = switch (code) {
//                case 'f' -> 0; // Белый
//                case 'c' -> 1; // Красный
//                case 'a' -> 2; // Зеленый
//                case 'e' -> 3; // Желтый
//                default -> 0;
//            };
//            return TextColor.color(78, Math.min(baseValue + offset,255),0);
//        }
//        // Метод расчета цветов
//        private LineColors calculateColors(int totalLines, int lineIndex) {
//            int baseB = getBaseValue(totalLines, lineIndex) ;
//            int specialB = getSpecialValue(totalLines, lineIndex);
//
//            return new LineColors(
//                    TextColor.color(78, Math.min(baseB, 255), 0),
//                    TextColor.color(78, Math.min(specialB, 255), 0)
//            );
//        }
//
//        // Базовые значения для обычных символов
//        private int getBaseValue(int totalLines, int lineIndex) {
//            return totalLines == 1 ? 68 : (lineIndex == 0 ? 72 : 76);
//        }
//
//        // Базовые значения для спецсимволов
//        private int getSpecialValue(int totalLines, int lineIndex) {
//            return totalLines == 1 ? 80 : (lineIndex == 0 ? 84 : 88);
//        }
//        private boolean isSpecialChar(char c) {
//            return c == 'ё' || c == 'й' ||c == 'Ё' ||c == 'Й';
//        }
//        private record LineColors(TextColor baseColor, TextColor specialColor) {}
//        public Component getResult() {
//            return result;
//        }
//    }
}
