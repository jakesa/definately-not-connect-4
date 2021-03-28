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
}