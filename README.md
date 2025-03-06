# github-browser-quarkus

## Application description

Application exposes an endpoint to fetch all repositories with branches for given owner.
Repositories which are forks are ignored.

Example of the request:
```shell script
curl --location --request GET 'localhost:8080/users/martastraszewska/repos'
```
Example of the response:
```shell script
    [{
        "repositoryName": "GitHubBrowser",
        "ownerLogin": "martastraszewska",
        "branches": [
            {
                "branchName": "master",
                "commitSha": "646cacf7ff4eb6788e4f87b1de0182b1dd764e67"
            },
            {
                "branchName": "test-branch",
                "commitSha": "482d637cb29da18dd9df0dbd3b6b0c535340258a"
            }
        ]
    }]
```
When given user does not exist, 404 is returned with following response, for example:
```shell script
{
    "status": "404",
    "message": "User not found user=martastraszewska2"
}
```
## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
mvn quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
mvn package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
mvn package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.


