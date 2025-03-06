package pl.straszewska.githubbrowserquarkus;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.google.common.io.Resources;
import io.quarkus.test.junit.QuarkusTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@QuarkusTest
public class GithubResourceTest {

    private static WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(8088);
        wireMockServer.start();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }


    @Test
    public void shouldReadRepositoryBranchesExcludingForks() {
        // given
        String userName = "martastraszewska";
        stubGithubRepositoriesResponse(userName);
        stubBranchesResponse(userName, "CustomerManagement");
        stubBranchesResponse(userName, "GitHubBrowser");

        //when then
        String expectedBody = getFileAsText("/json/expected-response.json").replace(" ", "").replace("\n", "");
        given()
                .when().get(String.format("/users/%s/repos", userName))
                .then()
                .statusCode(200)
                .body(is(expectedBody));

        //and
        Assertions.assertEquals(0, wireMockServer.findAllUnmatchedRequests().size());
    }

    private void stubGithubRepositoriesResponse(String userName) {
        wireMockServer.stubFor(get(urlEqualTo(String.format("/users/%s/repos", userName)))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBody(getFileAsText("/json/repositories-response.json"))));
    }


    private void stubBranchesResponse(String userName, String repoName) {
        String url = String.format("/repos/%s/%s/branches", userName, repoName);
        String path = String.format("/json/%s-branch-response.json", repoName.toLowerCase());
        wireMockServer.stubFor(get(urlEqualTo(url))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBody(getFileAsText(path))));
    }

    private static String getFileAsText(String path) {
        try {
            return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
