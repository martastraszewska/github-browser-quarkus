package pl.straszewska.githubbrowserquarkus.api;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import pl.straszewska.githubbrowserquarkus.api.dtos.UserRepositoryDto;
import pl.straszewska.githubbrowserquarkus.service.GithubService;

import java.util.List;

@Path("")
public class GithubResource {
    @Inject
    GithubService gitHubService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{username}/repos")
    public Uni<List<UserRepositoryDto>> getRepos(@RestPath String username) {
        Log.info("Received user repository request user=" + username);
        return gitHubService.getUserRepositoriesWithBranches(username);
    }
}
