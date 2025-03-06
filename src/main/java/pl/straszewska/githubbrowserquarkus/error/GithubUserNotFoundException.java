package pl.straszewska.githubbrowserquarkus.error;

public class GithubUserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_MSG = "User not found user=%s";

    public GithubUserNotFoundException(String userName) {
        super(String.format(USER_NOT_FOUND_MSG, userName));
    }
}
