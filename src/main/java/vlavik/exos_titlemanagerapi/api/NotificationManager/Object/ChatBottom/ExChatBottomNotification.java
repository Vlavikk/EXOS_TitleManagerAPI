package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationPlace;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime.GameTimes;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExChatBottomNotification extends AbstractNotification {
    {
        setPlace(NotificationPlace.BOTTOM_CHAT);
    }
    private ChatBottomDecoration decoration = new ChatBottomDecoration();
    private final HashMap<TitlePlayer,ExActionBar> syncActionBars = new HashMap<>();//TODO: Сделать удаление ненужных игроков

    public <T> ExChatBottomNotification(NotificationType type, T text){
        setType(type);
        setText(text);
    }
    public <T> ExChatBottomNotification(NotificationType type, T text, boolean force){
        setType(type);
        setForce(force);
        setText(text);
    }

    @Override
    public void send(TitlePlayer titlePlayer) {
        boolean isSending = syncActionBars.containsKey(titlePlayer) && syncActionBars.get(titlePlayer).isSending();

        GameTimes.ChatBottomNotification gameTime;
        if (getType() == NotificationType.ERROR){
            gameTime = isSending ? GameTimes.ChatBottomNotification.SHAKE : GameTimes.ChatBottomNotification.FADE_IN;
        }else gameTime = GameTimes.ChatBottomNotification.FADE_IN;

        ExActionBar actionBar = new ExActionBar(getText(),220,isForce());
        actionBar.setGameTime(gameTime.getStartTime(), gameTime.getActiveTime());
        actionBar.setDefaultTimeFadeOut(true);
        actionBar.setForceType(ForceType.DELETE);

        syncActionBars.put(titlePlayer,actionBar);
        titlePlayer.send(actionBar);
    }
    @Override
    public Component formatter(String input) {
        String[] lines = input.split("\n", 2);
        TextComponent.Builder result = Component.text();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i] != null ? lines[i] : "";
            LineColors colors = calculateColors(lines.length, i);

            TextComponent.Builder lineComponent = Component.text();

            // Префикс (цвет 78,92,9)
            String prefix = i == 0 ? "\u1901" : "\u1902";
            lineComponent.append(Component.text(prefix).color(TextColor.color(78, 92, 9)));

            // Иконка
            if (decoration.isHaveIcon()) {
                if (i == 0) {
                    lineComponent.append(Component.text(getType().getIcon() + "\u1902")
                            .color(TextColor.color(78, 92, 35)));
                }
                lineComponent.append(Component.text("..").color(TextColor.color(78, 92, 7)));
            }

            // Обработка символов
            TextColor currentOverrideColor = null;
            TextColor currentOverrideSpecColor = null;
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                // Обработка цветовых кодов §
                if (c == '§' && j + 1 < line.length()) {
                    char code = line.charAt(++j);
                    currentOverrideColor = getColorFromCode(code, lines.length, i,false);
                    currentOverrideSpecColor = getColorFromCode(code, lines.length, i,true);
                    continue;
                }

                // Определение цвета символа
                TextColor finalColor;
                if (isSpecialChar(c)) {
                    // Используем специальный цвет из LineColors
                    finalColor = currentOverrideSpecColor;
                } else if (currentOverrideColor != null) {
                    // Переопределенный цвет для обычных символов
                    finalColor = currentOverrideColor;
                } else {
                    // Базовый цвет
                    finalColor = colors.baseColor();
                }

                // Пробелы всегда 78,92,7
                if (c == ' ') {
                    lineComponent.append(Component.text(".").color(TextColor.color(78, 92, 7)));
                } else {
                    lineComponent.append(Component.text(c).color(finalColor));
                }
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
            default -> -0;
        };
        return TextColor.color(78, 92, Math.min(baseValue + offset, 255));
    }

    // Метод расчета цветов
    private LineColors calculateColors(int totalLines, int lineIndex) {
        int baseB = getBaseValue(totalLines, lineIndex) ;
        int specialB = getSpecialValue(totalLines, lineIndex);

        // Ограничиваем значения в пределах 22-33 для спецсимволов
        int clampedSpecialB = Math.min(specialB, totalLines == 1 ? 25 : (lineIndex == 0 ? 29 : 33));

        return new LineColors(
                TextColor.color(78, 92, Math.min(baseB, 255)),
                TextColor.color(78, 92, Math.min(specialB, 255))
        );
    }

    // Базовые значения для обычных символов
    private int getBaseValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 10 : (lineIndex == 0 ? 14 : 18);
    }

    // Базовые значения для спецсимволов
    private int getSpecialValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 22 : (lineIndex == 0 ? 26 : 30);
    }
    private boolean isSpecialChar(char c) {
        return List.of('ё','й','Ё','Й').contains(c);
    }
    record LineColors(TextColor baseColor, TextColor specialColor) {}
//    @Override
//    public Component formatter(String input) {
//        int colorIndex1 = decoration.getColorLine1().getId();
//        int colorIndex2 = decoration.getColorLine2().getId();
//        String[] lines = input.split("\n", 2);
//        TextComponent.Builder result = Component.text();
//
//        for (int i = 0; i < lines.length; i++) {
//            String line = lines[i] != null ? lines[i] : "";
//            int currentColorIndex = (i == 0) ? colorIndex1 : colorIndex2;
//            LineColors colors = calculateColors(lines.length, i, currentColorIndex);
//
//            TextComponent.Builder lineComponent = Component.text();
//
//            // Добавляем префикс
//            String prefix = i == 0 ? "\u1901" : "\u1902";
//            lineComponent.append(Component.text(prefix).color(TextColor.color(78, 92, 9)));
//            if (decoration.isHaveIcon()){
//                if (i == 0) lineComponent.append(Component.text(getType().getIcon()+"\u1902")).color(TextColor.color(78, 92, 35));
//                lineComponent.append(Component.text("..").color(TextColor.color(78, 92, 7)));
//            }
//
//            // Обрабатываем символы
//            for (char c : line.toCharArray()) {
//                if (c == ' ') {
//                    lineComponent.append(Component.text(".").color(TextColor.color(78, 92, 7)));
//                } else if (isSpecialChar(c)) {
//                    lineComponent.append(Component.text(c).color(colors.specialColor()));
//                } else {
//                    lineComponent.append(Component.text(c).color(colors.baseColor()));
//                }
//            }
//
//            result.append(lineComponent);
//        }
//
//        return result.build();
//    }
//    private LineColors calculateColors(int totalLines, int lineIndex, int colorIndex) {
//        int baseB = getBaseValue(totalLines, lineIndex) + colorIndex;
//        int specialB = getSpecialValue(totalLines, lineIndex) + colorIndex;
//
//        return new LineColors(
//                TextColor.color(78, 92, Math.min(baseB, 255)),
//                TextColor.color(78, 92, Math.min(specialB, 255))
//        );
//    }
//    private int getBaseValue(int totalLines, int lineIndex) {
//        return totalLines == 1 ? 10 : (lineIndex == 0 ? 14 : 18);
//    }
//    private int getSpecialValue(int totalLines, int lineIndex) {
//        return totalLines == 1 ? 22 : (lineIndex == 0 ? 26 : 30);
//    }
//    private boolean isSpecialChar(char c) {
//        return List.of('ё','й','Ё','Й').contains(c);
//    }
//    private record LineColors(TextColor baseColor, TextColor specialColor) {}
    public void setDecoration(ChatBottomDecoration decoration){
        this.decoration = decoration;
    }
}
