package vlavik.exos_titlemanagerapi.api.TitleManager.Object.GameTime;

public class GameTimes {
    public enum ChatBottomNotification{
        SHAKE(100,20),
        FADE_IN(10,10);
        private final int startTime;
        private final int activeTime;

        ChatBottomNotification(int startTime, int activeTime) {
            this.startTime = startTime;
            this.activeTime = activeTime;
        }
        public int getStartTime() {
            return startTime;
        }

        public int getActiveTime() {
            return activeTime;
        }
    }
}
