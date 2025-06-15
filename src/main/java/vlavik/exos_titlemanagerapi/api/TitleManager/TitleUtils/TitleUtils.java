package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils;

import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.CharOffsetShaderSession;
import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.ChatCenteredSession;

import java.util.List;

public class TitleUtils {
    public static List<CharOffsetShaderSession.CharOffset> getCharOffsetsForShader(String input){
        return new CharOffsetShaderSession(input).convertToCharOffsets();
    }
    public static ChatCenteredSession.ChatCenterResult centeredChatText(String input,int leftOffsetPixel){
        return new ChatCenteredSession(input,leftOffsetPixel).centerText();
    }
}
