package pl.straszewska.githubbrowserquarkus.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GithubOwnerResponse {

    @JsonProperty("login")
    private String ownerLogin;

    public String getOwnerLogin() {
        return ownerLogin;
    }
}
