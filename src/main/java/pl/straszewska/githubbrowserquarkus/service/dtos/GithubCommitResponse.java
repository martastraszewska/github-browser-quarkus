package pl.straszewska.githubbrowserquarkus.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GithubCommitResponse {
    @JsonProperty("sha")
    private String sha;

    public String getSha() {
        return sha;
    }
}
