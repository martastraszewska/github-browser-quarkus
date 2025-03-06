package pl.straszewska.githubbrowserquarkus.service.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GithubRepositoryResponse {
    @JsonProperty("name")
    private String repositoryName;
    @JsonProperty("owner")
    private GithubOwnerResponse owner;
    @JsonProperty("fork")
    private boolean fork;

    @JsonProperty("branches_url")
    private String branchesUrl;

    public String getRepositoryName() {
        return repositoryName;
    }

    public GithubOwnerResponse getOwner() {
        return owner;
    }

    public boolean isFork() {
        return fork;
    }

    public String getBranchesUrl() {
        return branchesUrl;
    }
}
