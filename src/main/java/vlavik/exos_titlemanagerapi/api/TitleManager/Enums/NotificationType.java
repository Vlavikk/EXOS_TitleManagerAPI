package vlavik.exos_titlemanagerapi.api.TitleManager.Enums;

public enum NotificationType {
    ERROR("\u1B26"),
    WARNING(""),
    SUCCESSFUL(""),
    NONE("");
    private final String icon;

    NotificationType(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
