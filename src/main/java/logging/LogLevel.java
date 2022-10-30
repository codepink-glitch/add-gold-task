package logging;

import java.util.Arrays;

public enum LogLevel {
    INFO((byte) 1),
    ERROR((byte) 2),
    FATAL((byte) 3);

    private final byte level;

    LogLevel(byte level) {
        this.level = level;
    }

    public byte getLevel() {
        return level;
    }

    public static LogLevel getByCode(byte code) {
        // TODO
        return Arrays.stream(LogLevel.values())
                .filter(x -> x.getLevel() == code)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
