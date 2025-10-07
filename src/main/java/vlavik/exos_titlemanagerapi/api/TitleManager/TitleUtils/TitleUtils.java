package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.CharOffsetShaderSession;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.ChatCenteredSession;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.FormatImageSession;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.FormatTextSession;

import java.util.List;

public class TitleUtils {
    public static List<CharOffsetShaderSession.CharOffset> getCharOffsetsForShader(String input){
        return new CharOffsetShaderSession(input).convertToCharOffsets();
    }
    public static ChatCenteredSession.ChatCenterResult centeredChatText(String input,int leftOffsetPixel){
        return new ChatCenteredSession(input,leftOffsetPixel).centerText();
    }
    public static Component formatText(String inputText, int maxLine,Player player,int startIndex){
        return new FormatTextSession(inputText,player.getWorld().getGameTime(),player.getPing(),maxLine,startIndex,false).getResult();
    }
    public static Component formatText(String inputText, int maxLine,Player player,int startIndex,boolean shadow){
        return new FormatTextSession(inputText,player.getWorld().getGameTime(),player.getPing(),maxLine,startIndex,shadow).getResult();
    }
    public static Component formatText(String inputText, int maxLine, long gameTime, int ping,int startIndex){
        return new FormatTextSession(inputText,gameTime,ping,maxLine,startIndex,false).getResult();
    }

    /**
     * @param font шрифт должен содержать determinant_pixel
     */
    public static Component formatImage(String inputText, Key font, long gameTime, int ping, int startIndex){
        return new FormatImageSession(inputText,font,gameTime,ping,startIndex).getResult();
    }
    public static Component formatImage(String inputText, Key font,Player player, int startIndex){
        return new FormatImageSession(inputText,font,player.getWorld().getGameTime(),player.getPing(),startIndex).getResult();
    }
}
