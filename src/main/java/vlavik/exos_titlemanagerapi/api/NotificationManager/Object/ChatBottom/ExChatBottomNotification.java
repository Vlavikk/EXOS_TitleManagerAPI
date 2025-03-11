package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import vlavik.exos_titlemanagerapi.api.NotificationManager.Object.AbstractNotification;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.ForceType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationPlace;
import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.NotificationType;
import vlavik.exos_titlemanagerapi.api.TitleManager.Object.Default.ExActionBar;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitlePlayer;

import java.util.List;


public class ExChatBottomNotification extends AbstractNotification {
    {
        setPlace(NotificationPlace.BOTTOM_CHAT);
    }
    private ChatBottomDecoration decoration = new ChatBottomDecoration();
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
        ExActionBar actionBar = new ExActionBar(getText(),220,isForce());
        actionBar.setDefaultTimeFadeOut(true);
        actionBar.setForceType(ForceType.DELETE);
        actionBar.setGameTime(10);
        titlePlayer.send(actionBar);
    }
    @Override
    public Component formatter(String input) {
        int colorIndex1 = decoration.getColorLine1().getId();
        int colorIndex2 = decoration.getColorLine2().getId();
        String[] lines = input.split("\n", 2);
        TextComponent.Builder result = Component.text();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i] != null ? lines[i] : "";
            int currentColorIndex = (i == 0) ? colorIndex1 : colorIndex2;
            LineColors colors = calculateColors(lines.length, i, currentColorIndex);

            TextComponent.Builder lineComponent = Component.text();

            // Добавляем префикс
            String prefix = i == 0 ? "\u1901" : "\u1902";
            lineComponent.append(Component.text(prefix).color(TextColor.color(78, 92, 9)));
            if (decoration.isHaveIcon()){
                if (i == 0) lineComponent.append(Component.text(getType().getIcon()+"\u1902")).color(TextColor.color(78, 92, 35));
                lineComponent.append(Component.text("..").color(TextColor.color(78, 92, 7)));
            }

            // Обрабатываем символы
            for (char c : line.toCharArray()) {
                if (c == ' ') {
                    lineComponent.append(Component.text(".").color(TextColor.color(78, 92, 7)));
                } else if (isSpecialChar(c)) {
                    lineComponent.append(Component.text(c).color(colors.specialColor()));
                } else {
                    lineComponent.append(Component.text(c).color(colors.baseColor()));
                }
            }

            result.append(lineComponent);
        }

        return result.build();
    }
    private LineColors calculateColors(int totalLines, int lineIndex, int colorIndex) {
        int baseB = getBaseValue(totalLines, lineIndex) + colorIndex;
        int specialB = getSpecialValue(totalLines, lineIndex) + colorIndex;

        return new LineColors(
                TextColor.color(78, 92, Math.min(baseB, 255)),
                TextColor.color(78, 92, Math.min(specialB, 255))
        );
    }
    private int getBaseValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 10 : (lineIndex == 0 ? 14 : 18);
    }
    private int getSpecialValue(int totalLines, int lineIndex) {
        return totalLines == 1 ? 22 : (lineIndex == 0 ? 26 : 30);
    }
    private boolean isSpecialChar(char c) {
        return List.of('ё','й','Ё','Й').contains(c);
    }
    private record LineColors(TextColor baseColor, TextColor specialColor) {}
    public void setDecoration(ChatBottomDecoration decoration){
        this.decoration = decoration;
    }
}
