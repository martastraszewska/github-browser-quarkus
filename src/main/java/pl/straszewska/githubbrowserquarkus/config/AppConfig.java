package pl.straszewska.githubbrowserquarkus.config;


import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AppConfig {
    @Produces
    WebClient githubWebClient(Vertx vertx) {
        return WebClient.create(vertx);
    }
}
