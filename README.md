# Definitely Not Connect 4
Definitely Not Connect 4 is a Connect 4 clone in RESTful API form.

##Running The Application
Definitely Not Connect 4 is a Kotlin/SpringBoot application that uses Gradle as its package manager and build tool.
To run the application locally, you will want to make sure that you have JDK 11 available on your machine and run the following command from the root of the project:

```shell
$ ./gradlew run
```

###Testing The Application
To run the unit and container integration test suites, run the following command from the root of the project:

```shell
$ ./gradlew cleanTest test
```

In the above command, you are executing two different tasks. Then `cleanTest` task tells gradle to clean out any results or references to the previous test run. This is effective "busting the cache" so that the tests will always run.
Gradle uses a caching mechanism to make tasks run faster. This results in tests not always executing if the `cleanTest` task is not run.

The `test` task is the task that actually executes the tests.

By default, this test run uses a silent output mode. To see all of the output information during a test run, using the `-i` flag when running the `test` task.

```shell
$ ./gradlew cleanTest test -i
```

##Using The API

Once the application is up and running, you free to start a new game.

*Note: By default, the application will be running on port 8080*

To start a game, POST a game request object as shown below:
```shell
$ curl --header "Content-Type: application/json" \
--request POST \
--data '{"players": ["player1", "player2"], "columns": 4, "rows": 4}' \
http://localhost:8080/drop_token

```
If all is well, you will get the game id:
```shell
{"gameId":"ab21686c-6501-4e89-89a5-9e300a94ee70"}
```

You can use that game id to check the status of your game at any time:

```shell
$ curl http://localhost:8080/drop_token/{gameId}
```
Game status:

```shell
{"players":["player1","player2"],"state":"IN_PROGRESS","winner":null}
```

When a game is finished, it will return a status of `DONE`. If the game was completed and no winner was determined,
`winner` field will remain `null` (this happens when the board had been filled and no players having completed a column, row, or diagonal row).

To submit a move you will want to post as a player to a specified column:

*Note: The game enforces a player order. The first player to go will always be the player at
index 0 in the create game request. If a user attempts to go out of order, 409 error will be returned*

```shell
$ curl --header "Content-Type: application/json" \
--request POST \
--data '{"column": 0}' \
http://localhost:8080/drop_token/{gameId}/{playerId}
```

If successful, you will be given a link to the processed move:

```shell
{"move":"ab21686c-6501-4e89-89a5-9e300a94ee70/moves/0"}
```

This link can be used to see the results of that move:

```shell
$ curl http://localhost:8080/drop_token/{gameId}/moves/{moveNumber}
```

```shell
{"type":"MOVE","player":"player1","column":0}
```

From this point, players can continue alternating move submissions until the game has completed.

At any point, a player can submit a request to quit the game:

```shell
$ curl -X DELETE http://localhost:8080/drop_token/{gameId}/{playerId}
```

This will end the game.