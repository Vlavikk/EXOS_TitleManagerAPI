package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;


import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.TextColor;

public class FormatImageSession {
    private Component result;
    public FormatImageSession(String input,Key font ,long gameTime,int ping,int startIndex) {
        logic(input,font,ping,gameTime,startIndex);
    }
    private void logic(String input, Key font, int ping, long gameTime, int startIndex){
        ComponentBuilder builder = Component.text();
        builder.font(font);
        builder.append(Component.text("\u1700")); // determinant_pixel
        long time = gameTime % 24000 + ((long) Math.floor((double) ping / 50));
        TextColor color = TextColor.color((int) time % 255,((int) Math.floor((double) time /255)) + 100, startIndex);
        builder.append(Component.text(input).color(color));
        result = builder.build();
    }

    public Component getResult() {
        return result;
    }
}
