package vlavik.exos_titlemanagerapi.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Component convertToComponent(String input) {
        // Регулярное выражение для захвата цветовых кодов и управляющих кодов
        Pattern pattern = Pattern.compile("&#([a-fA-F0-9]{6})|&(l|o|n|m|k|r)");
        Matcher matcher = pattern.matcher(input);

        Component finalComponent = Component.text(""); // Итоговый компонент
        int lastEnd = 0;

        // Текущие стили
        TextColor currentColor = null;
        Set<TextDecoration> activeDecorations = EnumSet.noneOf(TextDecoration.class);

        while (matcher.find()) {
            // Добавляем текст перед текущим кодом
            if (matcher.start() > lastEnd) {
                String plainText = input.substring(lastEnd, matcher.start());
                finalComponent = finalComponent.append(buildStyledText(plainText, currentColor, activeDecorations));
            }

            if (matcher.group(1) != null) { // Цветовой код &#RRGGBB
                currentColor = TextColor.fromHexString("#" + matcher.group(1));
            } else if (matcher.group(2) != null) { // Управляющий код стиля
                String styleCode = matcher.group(2);
                switch (styleCode) {
                    case "l": // Жирный
                        activeDecorations.add(TextDecoration.BOLD);
                        break;
                    case "o": // Курсив
                        activeDecorations.add(TextDecoration.ITALIC);
                        break;
                    case "n": // Подчёркнутый
                        activeDecorations.add(TextDecoration.UNDERLINED);
                        break;
                    case "m": // Зачёркнутый
                        activeDecorations.add(TextDecoration.STRIKETHROUGH);
                        break;
                    case "k": // Магический (обфускация)
                        activeDecorations.add(TextDecoration.OBFUSCATED);
                        break;
                    case "r": // Сброс стилей
                        currentColor = null;
                        activeDecorations.clear();
                        break;
                }
            }

            lastEnd = matcher.end();
        }

        // Добавляем оставшийся текст
        if (lastEnd < input.length()) {
            finalComponent = finalComponent.append(buildStyledText(input.substring(lastEnd), currentColor, activeDecorations));
        }

        return finalComponent;
    }
    private static Component buildStyledText(String text, TextColor color, Set<TextDecoration> decorations) {
        Component component = Component.text(text);

        if (color != null) {
            component = component.color(color);
        }

        for (TextDecoration decoration : decorations) {
            component = component.decorate(decoration);
        }

        return component;
    }
}
