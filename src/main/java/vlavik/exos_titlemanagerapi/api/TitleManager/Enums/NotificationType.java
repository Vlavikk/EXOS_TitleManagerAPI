package vlavik.exos_titlemanagerapi.api.TitleManager.Enums;

public enum NotificationType {
    ERROR("\u1B26"), // При повторе ошибки вместо нового выезд ошибки будет трястись, хз не играл
    WARNING(""), // каждый вызов будет выезжать
    SUCCESSFUL(""),// тоже
    NONE("");//хз не придумал
    private final String icon;

    NotificationType(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
