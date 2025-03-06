package pl.straszewska.githubbrowserquarkus.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BranchDto {
    @JsonProperty("branchName")
    private String branchName;
    @JsonProperty("commitSha")
    private String commitSha;

    public BranchDto(String branchName, String commitSha) {
        this.branchName = branchName;
        this.commitSha = commitSha;
    }

    public BranchDto() {
    }

    public String getBranchName() {
        return branchName;
    }

    public String getCommitSha() {
        return commitSha;
    }
}
