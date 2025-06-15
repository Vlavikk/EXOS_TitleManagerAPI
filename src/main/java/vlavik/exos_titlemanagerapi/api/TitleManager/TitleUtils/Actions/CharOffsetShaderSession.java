package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharOffsetShaderSession {
    private final String input;

    public CharOffsetShaderSession(String input) {
        this.input = input;
    }

    public List<CharOffset> convertToCharOffsets(){
        List<CharOffset> out = new ArrayList<>();
        int colorIndex = 0;
        for (int j = 0; j < input.length(); j++) {
            char c = input.charAt(j);
            if (c == ' ') {
                colorIndex += 2;
                out.add(new CharOffset(c,-1)); //Пробел = -1
                continue;
            }
            out.add(new CharOffset(c,Math.max(colorIndex,0)));
            if (c == '&' || c == '§') continue;
            if (j > 0 && (input.charAt(j-1) == '&' || input.charAt(j-1) == '§')) continue;
            if (colorIndex >= 255) continue;
            for (Map.Entry<Integer, String> entry : chartersOffset.entrySet()){
                if (entry.getValue().contains(String.valueOf(c))){
                    colorIndex += (entry.getKey());
                }
            }
        }
        return out;
    }
    public record CharOffset(char getChar,Integer getIndex){}
    private static final HashMap<Integer,String> chartersOffset = new HashMap<>(Map.of(
            -1,"ющъы",
            1,")(-><=_f{}",
            2,"*[]t",
            3,"l",
            4,"!;:i.,"
    ));
}
