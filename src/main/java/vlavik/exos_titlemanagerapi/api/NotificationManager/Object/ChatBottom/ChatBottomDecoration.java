package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.DecorationPattern;

public class ChatBottomDecoration {
    private DecorationPattern.ChatBottom colorLine1 = DecorationPattern.ChatBottom.WHITE;
    private DecorationPattern.ChatBottom colorLine2 = DecorationPattern.ChatBottom.WHITE;
    private boolean haveIcon = false;
    public ChatBottomDecoration() {}
    public ChatBottomDecoration(boolean haveIcon) {
        this.haveIcon = haveIcon;
    }
    public ChatBottomDecoration(DecorationPattern.ChatBottom colorLine1) {
        this.colorLine1 = colorLine1;
    }
    public ChatBottomDecoration(DecorationPattern.ChatBottom colorLine1, DecorationPattern.ChatBottom colorLine2) {
        this.colorLine1 = colorLine1;
        this.colorLine2 = colorLine2;
    }
    public ChatBottomDecoration(DecorationPattern.ChatBottom colorLine1, DecorationPattern.ChatBottom colorLine2, boolean haveIcon) {
        this.colorLine1 = colorLine1;
        this.colorLine2 = colorLine2;
        this.haveIcon = haveIcon;
    }
    public DecorationPattern.ChatBottom getColorLine1() {
        return colorLine1;
    }
    public void setColorLine1(DecorationPattern.ChatBottom colorLine1) {
        this.colorLine1 = colorLine1;
    }
    public DecorationPattern.ChatBottom getColorLine2() {
        return colorLine2;
    }
    public void setColorLine2(DecorationPattern.ChatBottom colorLine2) {
        this.colorLine2 = colorLine2;
    }
    public boolean isHaveIcon() {
        return haveIcon;
    }
    public void setHaveIcon(boolean haveIcon) {
        this.haveIcon = haveIcon;
    }
}
