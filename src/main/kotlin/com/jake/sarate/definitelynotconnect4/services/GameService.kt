package com.jake.sarate.definitelynotconnect4.services

import com.jake.sarate.definitelynotconnect4.components.Game
import com.jake.sarate.definitelynotconnect4.models.GameRequest

interface GameService {
    fun createGame(gameRequest: GameRequest): Game
    fun getGame(gameId: String): Game?
    fun deleteGame(gameId: String): Boolean
    fun listGames(): List<Game>
}