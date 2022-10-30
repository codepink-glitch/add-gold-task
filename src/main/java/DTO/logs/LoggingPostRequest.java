package DTO.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoggingPostRequest {

    private byte logLevel;
    private String logMessage;
    private String logError;

}
