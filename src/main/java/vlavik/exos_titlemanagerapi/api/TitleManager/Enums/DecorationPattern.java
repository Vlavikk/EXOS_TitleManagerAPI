package vlavik.exos_titlemanagerapi.api.TitleManager.Enums;

public class DecorationPattern {
    public enum ChatBottom{
        WHITE(0),
        RED(1),
        GREEN(2),
        YELLOW(3);
        private final int id;

        ChatBottom(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    public enum Icon{
        ERROR,

    }
}
