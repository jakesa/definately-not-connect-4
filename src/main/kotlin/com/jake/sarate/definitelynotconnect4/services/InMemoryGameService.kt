package com.jake.sarate.definitelynotconnect4.services

import com.jake.sarate.definitelynotconnect4.models.GameInstance
import com.jake.sarate.definitelynotconnect4.models.GameRequest
import org.springframework.stereotype.Service

@Service
class InMemoryGameService: GameService {
    private val gameMap: MutableMap<String, GameInstance> = mutableMapOf()

    override fun createGame(gameRequest: GameRequest): GameInstance {
        val game = GameInstance(
            columns = gameRequest.columns,
            rows = gameRequest.rows,
            players = gameRequest.players
        )
        gameMap[game.gameId] = game
        return game
    }

    override fun getGame(gameId: String): GameInstance? {
        return gameMap[gameId]
    }

    override fun deleteGame(gameId: String): Boolean {
        return when(gameMap.contains(gameId)) {
            true -> {
                gameMap.remove(gameId)
                return true
            }
            else -> false
        }
    }

    override fun listGames(): List<GameInstance> {
        return gameMap.values.toList()
    }
}