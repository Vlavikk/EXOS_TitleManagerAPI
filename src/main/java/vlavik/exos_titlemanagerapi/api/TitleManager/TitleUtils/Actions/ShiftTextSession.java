package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions;

import vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.TitleUtils;

import java.util.ArrayList;
import java.util.List;

public class ShiftTextSession {
    //middleShift - будет ли делится слово для переноса
    //lineShift - крайнее значение после которого не должно быть символов
    private ShiftTextSessionResult result;

    public ShiftTextSession(ShiftTextType type, String input, int lineShift, boolean middleShift) {
        formatText(type,input,lineShift,middleShift);
    }

    public void formatText(ShiftTextType type, String input, int lineWidth, boolean middleShift) {
        List<String> lines = new ArrayList<>();
        String[] words = input.split(" ");
        StringBuilder currentLine = new StringBuilder();
        String leftoverWordPart = null;
        boolean isFirstWord = true;
        int currentLineLength = 0;

        for (String word : words) {
            if (leftoverWordPart != null) {
                currentLine.append(" ").append(leftoverWordPart);
                leftoverWordPart = null;
            }

            String wordToProcess = isFirstWord ? word : " " + word;
            int wordLength = calculateLength(wordToProcess, type);

            if (wordLength > lineWidth) {
                if (!currentLine.isEmpty()) {
                    lines.add(currentLine.toString());
                    currentLine.setLength(0);
                }
                splitLongWord(word, type, lineWidth, lines);
                leftoverWordPart = getRemainingWordPart(word, type, lineWidth);
            } else {
                if (currentLineLength + wordLength > lineWidth) {
                    lines.add(currentLine.toString());
                    currentLine.setLength(0);
                    currentLine.append(word);
                    currentLineLength = calculateLength(word, type);
                } else {
                    currentLine.append(isFirstWord ? "" : " ").append(word);
                    currentLineLength += wordLength;
                }
            }
            isFirstWord = false;
        }

        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        String formattedResult = String.join("\n", lines);
        result = new ShiftTextSessionResult(formattedResult, lines);
    }

    private int calculateLength(String text, ShiftTextType type) {
        if (type == ShiftTextType.PIXELS) {
            return text.chars().map(c -> TitleUtils.getCharLength((char) c)+1).sum();
        }
        return text.length();
    }

    private void splitLongWord(String word, ShiftTextType type, int lineWidth, List<String> lines) {
        for (int i = word.length(); i >= 0; i--) {
            String prefix = word.substring(0, i);
            if (calculateLength(prefix, type) > lineWidth + 3) {
                continue;
            }
            lines.add(prefix + "-");
            break;
        }
    }

    private String getRemainingWordPart(String word, ShiftTextType type, int lineWidth) {
        for (int i = word.length(); i >= 0; i--) {
            String prefix = word.substring(0, i);
            if (calculateLength(prefix, type) > lineWidth + 3) {
                continue;
            }
            return word.substring(i);
        }
        return null;
    }
    public ShiftTextSessionResult getResult(){
        return result;
    }

    public record ShiftTextSessionResult(String result, List<String> lineList){}
    public enum ShiftTextType{
        PIXELS,
        CHARS
    }
}
