package com.jake.sarate.definitelynotconnect4.controllers

import com.jake.sarate.definitelynotconnect4.models.*
import com.jake.sarate.definitelynotconnect4.services.GameService

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/drop_token")
class GameController(val gameService: GameService) {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun createGame(@RequestBody body: GameRequest): CreateGameResponse {
        val game = gameService.createGame(body)
        return CreateGameResponse(game.gameId)
    }

    @GetMapping("/{gameId}",produces = ["application/json"])
    fun getGame(@PathVariable gameId: String): GetGameResponse {
        gameService.getGame(gameId)?.let {
            return GetGameResponse(it.players, it.state, it.winner)
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game/moves not found")
        }
    }

    @GetMapping(produces = ["application/json"])
    fun listGames(): GetGamesResponse {
        val gameIds = gameService.listGames().map { it.gameId }
        return GetGamesResponse(gameIds)
    }

    @GetMapping("/{gameId}/moves", produces = ["application/json"])
    fun getMoves(@PathVariable gameId: String, @RequestParam start: Int?, @RequestParam until: Int?): ListMovesResponse {
        gameService.getGame(gameId)?.let {
            val gameMoves = it.listMoves(start, until).map { moveResult ->
                PlayerMove(moveResult.moveType, moveResult.playerId, moveResult.column)
            }
            return ListMovesResponse(gameMoves)
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game/moves not found.")
        }
    }

    @PostMapping("/{gameId}/{playerId}")
    fun postMove(@RequestBody body: PostMoveRequest, @PathVariable gameId: String, @PathVariable playerId: String): PostMoveResponse {
        gameService.getGame(gameId)?.let {
            var playerMoveResult: Pair<Int, PlayerMoveResult>
            if (it.players.contains(playerId)) {
                playerMoveResult = it.attemptPlayerMove(playerId, body.column)
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found or player is not a part of it.")
            }
            checkForError(playerMoveResult.second)
            return PostMoveResponse("$gameId/moves/${playerMoveResult.first}")
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found or player is not a part of it.")
        }
    }

    private fun checkForError(result: PlayerMoveResult) {
        when(result.exception) {
            PlayerMoveException.INVALID_COLUMN_SPECIFICATION,
            PlayerMoveException.NO_AVAILABLE_SPACES -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed input. Illegal move")
            PlayerMoveException.PLAYED_MOVE_OUT_OF_TURN -> throw ResponseStatusException(HttpStatus.CONFLICT, PlayerMoveException.PLAYED_MOVE_OUT_OF_TURN)
            PlayerMoveException.UNKNOWN_PLAYER_MOVE_EXCEPTION -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, PlayerMoveException.UNKNOWN_PLAYER_MOVE_EXCEPTION)
        }
    }
}