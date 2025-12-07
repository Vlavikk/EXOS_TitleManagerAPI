package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.*;
import vlavik.exos_titlemanagerapi.api.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TitleUtils {
    public static List<CharOffsetShaderSession.CharOffset> getCharOffsetsForShader(String input){
        return new CharOffsetShaderSession(input).convertToCharOffsets();
    }
    public static ChatCenteredSession.ChatCenterResult centeredChatText(String input,int leftOffsetPixel){
        return new ChatCenteredSession(input,leftOffsetPixel).centerText();
    }
    public static Component formatText(String inputText, int maxLine,Player player,int startIndex){
        return new FormatTextSession(inputText,player.getWorld().getGameTime(), Utils.getRealPlayerPing(player),maxLine,startIndex,false).getResult();
    }
    public static Component formatText(String inputText, int maxLine,Player player,int startIndex,boolean shadow){
        return new FormatTextSession(inputText,player.getWorld().getGameTime(),Utils.getRealPlayerPing(player),maxLine,startIndex,shadow).getResult();
    }
    public static Component formatText(LinkedHashMap<String,Integer> inputText, Player player, int startIndex, boolean shadow){
        return new FormatTextSession(inputText,player.getWorld().getGameTime(),Utils.getRealPlayerPing(player),startIndex,shadow).getResult();
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
        return new FormatImageSession(inputText,font,player.getWorld().getGameTime(),Utils.getRealPlayerPing(player),startIndex).getResult();
    }
    public static ShiftTextSession.ShiftTextSessionResult shiftText(ShiftTextSession.ShiftTextType type, String input, int lineShift, boolean middleShift){
        return new ShiftTextSession(type,input,lineShift,middleShift).getResult();
    }
    private static final HashMap<Integer,String> charsMap = new HashMap<>(Map.of(
            8,"Щ",
            7,"ЖЫЮФШю",
            6,"@ЪЦДЪдщъы",
            4,"><=_fkк{}г",
            3,")(*[]tI ",
            2,"l",
            1,"!;:i.,'|."
    ));
    public static int getCharLength(char c){
        int i = -1;
        for (Map.Entry<Integer, String> entry : charsMap.entrySet()){
            if (entry.getValue().contains(String.valueOf(c))){
                i = entry.getKey();
                break;
            }
        }
        return i == -1 ? 5 : i;
    }
}
