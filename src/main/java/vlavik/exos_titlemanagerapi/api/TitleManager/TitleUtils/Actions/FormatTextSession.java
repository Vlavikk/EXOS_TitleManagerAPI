package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

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
    }
    private static final HashMap<Integer,String> ones = new HashMap<>(Map.of(
            8,"Щ",
            7,"ЖЫЮФШю",
            6,"@ЪЦДЪдщъы",
            4,")(><=_fkк{}г",
            3,"*[]tI ",
            2,"l",
            1,"!;:i.,'|."
    ));
    private Component result;

    public FormatTextSession(String input, long gameTime,int ping, int maxLine,int startIndex) {
        logic(input,maxLine,ping,gameTime,startIndex);
    }
    private void logic(String input,int maxLine,int ping,long gameTime,int startIndex){
        ComponentBuilder builder = Component.text();

        String[] messages = input.split("\n",maxLine);
        int maxWight = Arrays.stream(messages).mapToInt(s ->getStringOffsets(getClearedString(s))).max().orElse(0);
        int allOffset = messages.length >= 2 ?
                maxWight - (maxWight - getStringOffsets(getClearedString(messages[messages.length-1])))
                :
                maxWight;

        int thisOffset = 0;
        int line = 0;
        Key font = Key.key("minecraft","utils/colors/utiltext_white");
        long time = gameTime % 24000 + ((long) Math.floor((double) ping / 50));
        builder.append(Component.text("\u1A31".repeat(allOffset)));
        builder.font(font);
        for (String s : messages){
            TextColor color = TextColor.color((int) time % 255,((int) Math.floor((double) time /255)) + 100,startIndex+ line);
            builder.append(Component.text("\u1A32".repeat(thisOffset)));
            for (int j = 0; j < s.length(); j++){
                char c = s.charAt(j);

                if ("§&".contains(String.valueOf(c)) && j + 1 < s.length()){
                    String code = String.valueOf(s.charAt(++j));
                    font = Key.key("minecraft","utils/colors/utiltext_"+colors.get(code).toString());
                    continue;
                }
                builder.append(Component.text(String.valueOf(c))
                        .font(font)
                        .color(color));
            }
            thisOffset = getStringOffsets(getClearedString(s));
            line++;
        }
        result = builder.build();
    }
    private int getStringOffsets(String input){
        int i = 0;
        for (char c : input.toCharArray()){
            boolean isConf = false;
            for (Map.Entry<Integer, String> entry : ones.entrySet()){
                if (entry.getValue().contains(String.valueOf(c))){
                    i = i+entry.getKey();
                    isConf = true;
                    break;
                }
            }
            if (!isConf) i = i+5;
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
