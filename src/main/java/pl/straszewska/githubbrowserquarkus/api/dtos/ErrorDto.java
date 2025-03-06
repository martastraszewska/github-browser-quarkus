package pl.straszewska.githubbrowserquarkus.api.dtos;

public class ErrorDto {
    private String status;
    private String message;

    public ErrorDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
