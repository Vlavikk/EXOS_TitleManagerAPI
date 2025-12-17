package vlavik.exos_titlemanagerapi.api.TitleManager.TitleUtils.Actions.FormatText;

import java.util.Optional;

public class FormatTextOverrideBuilder{
    private Optional<Integer> index = Optional.empty();
    private Optional<Long> gameTime = Optional.empty();
    private Optional<Integer> ping = Optional.empty();
    private Optional<Boolean> shadow = Optional.empty();

    public FormatTextOverrideBuilder setIndex(int index) {
        this.index = Optional.of(index);
        return this;
    }
    public FormatTextOverrideBuilder setGameTime(long gameTime) {
        this.gameTime = Optional.of(gameTime);
        return this;
    }
    public FormatTextOverrideBuilder setPing(int ping) {
        this.ping = Optional.of(ping);
        return this;
    }
    public FormatTextOverrideBuilder setShadow(boolean shadow) {
        this.shadow = Optional.of(shadow);
        return this;
    }

    public Optional<Integer> getIndex() {
        return index;
    }

    public Optional<Long> getGameTime() {
        return gameTime;
    }

    public Optional<Integer> getPing() {
        return ping;
    }

    public Optional<Boolean> getShadow() {
        return shadow;
    }
}
