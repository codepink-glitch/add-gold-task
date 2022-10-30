package entity;

import logging.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LogEntity {

    private long logId;
    private String logMessage;
    private String logError;
    private LogLevel level;
    private Date createdAt;

    public LogEntity(String logMessage, String logError, LogLevel level) {
        this.logMessage = logMessage;
        this.logError = logError;
        this.level = level;
    }
}
