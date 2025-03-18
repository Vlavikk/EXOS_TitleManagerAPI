package vlavik.exos_titlemanagerapi.api.NotificationManager.Object.ChatBottom;

import vlavik.exos_titlemanagerapi.api.TitleManager.Enums.DecorationPattern;

public class ChatBottomDecoration {
    private boolean haveIcon = false;
    public ChatBottomDecoration() {}
    public ChatBottomDecoration(boolean haveIcon) {
        this.haveIcon = haveIcon;
    }
    public ChatBottomDecoration(DecorationPattern.ChatBottom colorLine1) {
    }
    public boolean isHaveIcon() {
        return haveIcon;
    }
    public void setHaveIcon(boolean haveIcon) {
        this.haveIcon = haveIcon;
    }
}
