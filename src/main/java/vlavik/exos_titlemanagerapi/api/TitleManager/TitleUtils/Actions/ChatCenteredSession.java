package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;

import org.bukkit.ChatColor;

public class ChatCenteredSession {
    private static final int CHAT_WIDTH_PX = 320;// Ширина чата в пикселях (стандарт)
    private static final int CHAR_WIDTH = 6; // Ширина символов
    private static final int SPACE_WIDTH = 4;

    private final String input;
    private final int leftOffsetPixel;

    public ChatCenteredSession(String input, int leftOffsetPixel) {
        this.input = input;
        this.leftOffsetPixel = leftOffsetPixel;
    }

    public ChatCenterResult centerText() {
        if (input == null || input.isEmpty()) {
            return new ChatCenterResult(input,0);
        }
        String cleanText = ChatColor.stripColor(input);
        int textWidth = 0;
        for (char c : cleanText.toCharArray()) {
            textWidth += getCharWidth(c);
        }
        int totalSpace = CHAT_WIDTH_PX - textWidth - leftOffsetPixel;
        if (totalSpace <= 0) return new ChatCenterResult(input,0);

        int leftSpaces = (totalSpace / 2) / SPACE_WIDTH;

        StringBuilder padding = new StringBuilder();
        padding.append(" ".repeat(leftSpaces));

        return new ChatCenterResult(padding + input + padding,leftSpaces);
    }
    public record ChatCenterResult(String getResultText,int getOffsetSpace){}
    private static int getCharWidth(char c) {
        if (c == ' ') return SPACE_WIDTH;
        if ("iIl.,;!|'".indexOf(c) >= 0) return 2;    // Узкие символы
        if ("@#mwMW".indexOf(c) >= 0) return 8;       // Широкие символы
        return CHAR_WIDTH;                             // Стандартные
    }
}
