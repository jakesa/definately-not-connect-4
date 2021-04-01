package com.jake.sarate.definitelynotconnect4.controllers

import com.jake.sarate.definitelynotconnect4.models.*
import com.jake.sarate.definitelynotconnect4.models.constants.GameException
import com.jake.sarate.definitelynotconnect4.models.requests.GameRequest
import com.jake.sarate.definitelynotconnect4.models.requests.PostMoveRequest
import com.jake.sarate.definitelynotconnect4.models.responses.*
import com.jake.sarate.definitelynotconnect4.services.GameService

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/drop_token")
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
            throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_MOVE_NOT_FOUND)
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
            throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_MOVE_NOT_FOUND)
        }
    }

    @GetMapping("/{gameId}/moves/{moveNumber}", produces = ["application/json"])
    fun getMove(@PathVariable gameId: String, @PathVariable moveNumber: Int): PlayerMove {
        gameService.getGame(gameId)?.let {
            it.getMove(moveNumber)?.let { playerMove ->
                return PlayerMove(playerMove.moveType, playerMove.playerId, playerMove.column)
            } ?: run {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_MOVE_NOT_FOUND)
            }
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_MOVE_NOT_FOUND)
        }
    }

    @DeleteMapping("/{gameId}/{playerId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun quitGame(@PathVariable gameId: String, @PathVariable playerId: String) {
        gameService.getGame(gameId)?.let {
            val quitResult = it.quit(playerId)
            checkForError(quitResult.second)
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_PLAYER_NOT_FOUND)
        }
    }

    @PostMapping("/{gameId}/{playerId}")
    fun postMove(@RequestBody body: PostMoveRequest, @PathVariable gameId: String, @PathVariable playerId: String): PostMoveResponse {
        gameService.getGame(gameId)?.let {
            var playerMoveResult: Pair<Int, PlayerMoveResult>
            if (it.players.contains(playerId)) {
                playerMoveResult = it.attemptPlayerMove(playerId, body.column)
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_PLAYER_NOT_FOUND)
            }
            checkForError(playerMoveResult.second)
            return PostMoveResponse("$gameId/moves/${playerMoveResult.first}")
        } ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_PLAYER_NOT_FOUND)
        }
    }

    private fun checkForError(result: PlayerMoveResult) {
        when(result.exception) {
            GameException.INVALID_COLUMN_SPECIFICATION,
            GameException.NO_AVAILABLE_SPACES -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, GameException.MALFORMED_INPUT_OR_ILLIGAL_MOVE)
            GameException.PLAYED_MOVE_OUT_OF_TURN -> throw ResponseStatusException(HttpStatus.CONFLICT, GameException.PLAYED_MOVE_OUT_OF_TURN)
            GameException.UNKNOWN_PLAYER_MOVE_EXCEPTION -> throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, GameException.UNKNOWN_PLAYER_MOVE_EXCEPTION)
            GameException.GAME_OR_PLAYER_NOT_FOUND -> throw ResponseStatusException(HttpStatus.NOT_FOUND, GameException.GAME_OR_PLAYER_NOT_FOUND)
            GameException.GAME_IS_ALREADY_DONE -> throw ResponseStatusException(HttpStatus.GONE, GameException.GAME_IS_ALREADY_DONE)
        }
    }
}