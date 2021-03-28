package com.jake.sarate.definitelynotconnect4.services

import com.jake.sarate.definitelynotconnect4.models.GameInstance
import com.jake.sarate.definitelynotconnect4.models.GameRequest

interface GameService {
    fun createGame(gameRequest: GameRequest): GameInstance
    fun getGame(gameId: String): GameInstance?
    fun deleteGame(gameId: String): Boolean
}