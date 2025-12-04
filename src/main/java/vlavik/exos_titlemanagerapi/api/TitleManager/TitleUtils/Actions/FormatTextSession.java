package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.TitleUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormatTextSession {
    private static final HashMap<String,NamedTextColor> colors = new HashMap<>();
    static {
        colors.put("4",NamedTextColor.DARK_RED);
        colors.put("c",NamedTextColor.RED);
        colors.put("6",NamedTextColor.GOLD);
        colors.put("e",NamedTextColor.YELLOW);
        colors.put("2",NamedTextColor.DARK_GREEN);
        colors.put("a",NamedTextColor.GREEN);
        colors.put("b",NamedTextColor.AQUA);
        colors.put("3",NamedTextColor.DARK_AQUA);
        colors.put("1",NamedTextColor.DARK_BLUE);
        colors.put("9",NamedTextColor.BLUE);
        colors.put("d",NamedTextColor.LIGHT_PURPLE);
        colors.put("5",NamedTextColor.DARK_PURPLE);
        colors.put("f",NamedTextColor.WHITE);
        colors.put("7",NamedTextColor.GRAY);
        colors.put("8",NamedTextColor.DARK_GRAY);
        colors.put("0",NamedTextColor.BLACK);
    }
    private Component result;

    public FormatTextSession(String input, long gameTime,int ping, int maxLine,int startIndex,boolean shadow) {
        logic(input,maxLine,ping,gameTime,startIndex,shadow);
    }
    private void logic(String input,int maxLine,int ping,long gameTime,int startIndex,boolean shadow){
        ComponentBuilder builder = Component.text();

        String[] messages = input.split("\n",maxLine);
        int maxWight = Arrays.stream(messages).mapToInt(s ->getStringOffsets(getClearedString(s))).max().orElse(0);
        int allOffset = messages.length >= 2 ?
                maxWight - (maxWight - getStringOffsets(getClearedString(messages[messages.length-1])))
                :
                maxWight;

        int thisOffset = 0;
        int line = 0;
        @NotNull Key fontMain = Key.key("minecraft","utils/colors/utiltext_white");
        @Nullable Key fontShadow = Key.key("minecraft","utils/shadows/utiltext_white");
        long time = gameTime % 24000 + ((long) Math.floor((double) ping / 50));
        builder.append(Component.text("\u1A31".repeat(allOffset)));
        builder.font(fontMain);
        for (String s : messages){
            TextColor color = TextColor.color((int) time % 255,((int) Math.floor((double) time /255)) + 100,startIndex+ line);
            builder.append(Component.text("\u1A32".repeat(thisOffset)));
            @NotNull ComponentBuilder lineComponent = Component.text();
            @Nullable ComponentBuilder lineShadowComponent = Component.text();
            for (int j = 0; j < s.length(); j++){
                char c = s.charAt(j);

                if ("§&".contains(String.valueOf(c)) && j + 1 < s.length()){
                    String code = String.valueOf(s.charAt(++j));
                    String finalCode = colors.containsKey(code) ? code : "f"; // просто белый если символ не найден
                    fontMain = Key.key("minecraft","utils/colors/utiltext_"+colors.get(finalCode).toString());
                    if (shadow) fontShadow = Key.key("minecraft","utils/shadows/utiltext_"+colors.get(finalCode).toString());
                    continue;
                }

                lineComponent.append(Component.text(String.valueOf(c))
                        .font(fontMain)
                        .color(color));
                if (shadow){
                    lineShadowComponent.append(Component.text(String.valueOf(c))
                            .font(fontShadow)
                            .color(color));
                }
            }
            thisOffset = getStringOffsets(getClearedString(s));
            if (shadow){
                builder.append(lineShadowComponent.build());
                builder.append(Component.text("\u1A32".repeat(thisOffset)));
            }
            builder.append(lineComponent.build());
            line++;
        }
        result = builder.build();
    }
    private int getStringOffsets(String input){
        int i = 0;
        for (char c : input.toCharArray()){
            i = i + TitleUtils.getCharLength(c);
//            boolean isConf = false;
//            for (Map.Entry<Integer, String> entry : TitleUtils.charsMap.entrySet()){
//                if (entry.getValue().contains(String.valueOf(c))){
//                    i = i+entry.getKey();
//                    isConf = true;
//                    break;
//                }
//            }
//            if (!isConf) i = i+5;
        }
        return i+input.length();
    }
    private String getClearedString(String input){
        input = input.replace("\n","");
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < input.length(); j++){
            char c = input.charAt(j);
            if ("§&".contains(String.valueOf(c)) && j + 1 < input.length()){
                ++j;
            }else builder.append(c);
        }
        return builder.toString();
    }

    public Component getResult() {
        return result;
    }
}
