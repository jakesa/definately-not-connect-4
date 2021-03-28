package com.jake.sarate.definitelynotconnect4.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import com.jake.sarate.definitelynotconnect4.models.CreateGameResponse
import com.jake.sarate.definitelynotconnect4.models.GameInstance
import com.jake.sarate.definitelynotconnect4.models.GameRequest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/drop_token")
class GameController() {

    private val objectMapper = ObjectMapper()

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun createGame(@RequestBody body: GameRequest): CreateGameResponse {
        val game = GameInstance(
            columns = body.columns,
            rows = body.rows,
            players = body.players
        )
        return CreateGameResponse(game.gameId)
    }
}