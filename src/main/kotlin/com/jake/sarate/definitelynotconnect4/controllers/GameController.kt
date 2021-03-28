package com.jake.sarate.definitelynotconnect4.controllers

import com.jake.sarate.definitelynotconnect4.models.GameInstance
import com.jake.sarate.definitelynotconnect4.models.GameRequest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/drop_token")
class GameController {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun createGame(@RequestBody body: GameRequest): GameInstance {
        return GameInstance()
    }
}