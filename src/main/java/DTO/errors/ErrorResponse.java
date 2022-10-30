package DTO.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private boolean success;
    private String message;

    public ErrorResponse(Exception exception) {
        this.success = false;
        this.message = "Error occurred, reason: " + exception.getMessage();
    }

    public String toString() {
        return String.format("{" +
                "\"success\": %s, " +
                "\"message\": %s " +
                "}", success, message);
    }
}
