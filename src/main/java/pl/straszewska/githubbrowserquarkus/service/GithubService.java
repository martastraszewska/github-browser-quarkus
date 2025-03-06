package pl.straszewska.githubbrowserquarkus.service;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.straszewska.githubbrowserquarkus.api.dtos.BranchDto;
import pl.straszewska.githubbrowserquarkus.api.dtos.UserRepositoryDto;
import pl.straszewska.githubbrowserquarkus.error.GithubUserNotFoundException;
import pl.straszewska.githubbrowserquarkus.service.dtos.GithubBranchResponse;
import pl.straszewska.githubbrowserquarkus.service.dtos.GithubRepositoryResponse;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class GithubService {
    @Inject
    private WebClient webClient;
    @ConfigProperty(name = "github.user-repositories-url")
    private String userRepositoriesUrl;

    public Uni<List<UserRepositoryDto>> getUserRepositoriesWithBranches(String user) {
        URI uri = UriBuilder.fromUri(userRepositoriesUrl).build(user);
        return executeGetToGithub(uri.toString())
                .map(res -> handleRepositoriesResponse(res, user)
                        .filter(repo -> !repo.isFork())
                        .toList())
                .flatMap(repositories -> {
                            if (repositories.isEmpty()) {
                                return Uni.createFrom().item(List.of());
                            } else {
                                return Uni.join().all(repositories.stream().map(this::getBranchesForRepository).toList())
                                        .usingConcurrencyOf(1)
                                        .andFailFast();
                            }

                        }
                );
    }

    private static Stream<GithubRepositoryResponse> handleRepositoriesResponse(HttpResponse<Buffer> res, String user) {
        if (res.statusCode() == 200) {
            return Arrays.stream(res.bodyAsJson(GithubRepositoryResponse[].class));
        } else if (res.statusCode() == 404) {
            throw new GithubUserNotFoundException(user);
        } else {
            throw new RuntimeException("Unexpected Github response");
        }
    }

    private Uni<HttpResponse<Buffer>> executeGetToGithub(String uri) {
        Log.info("Requesting Github url=" + uri);
        return webClient.getAbs(uri)
                .putHeader("Accept", "application/vnd.github+json")
                .putHeader("X-GitHub-Api-Version", "2022-11-28")
                .send();
    }

    private Uni<UserRepositoryDto> getBranchesForRepository(GithubRepositoryResponse repository) {
        String branchesUrl = repository.getBranchesUrl().replace("{/branch}", "");
        return executeGetToGithub(branchesUrl)
                .onItem().transform(res -> Arrays.stream(res.bodyAsJson(GithubBranchResponse[].class))
                        .map(branch -> new BranchDto(branch.getName(), branch.getCommit().getSha())).toList())
                .onItem().transform(branches -> new UserRepositoryDto(repository.getRepositoryName(), repository.getOwner().getOwnerLogin(), branches));
    }
}


