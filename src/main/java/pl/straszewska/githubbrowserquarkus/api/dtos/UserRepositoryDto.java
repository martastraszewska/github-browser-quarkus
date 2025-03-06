package pl.straszewska.githubbrowserquarkus.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserRepositoryDto {

    @JsonProperty("repositoryName")
    private String repositoryName;

    @JsonProperty("ownerLogin")
    private String ownerLogin;

    @JsonProperty("branches")
    private List<BranchDto> branches;

    public UserRepositoryDto(String repositoryName, String ownerLogin, List<BranchDto> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public UserRepositoryDto() {
    }
}
