package pl.straszewska.githubbrowserquarkus.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GithubBranchResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("commit")
    private GithubCommitResponse commit;

    public String getName() {
        return name;
    }

    public GithubCommitResponse getCommit() {
        return commit;
    }
}
