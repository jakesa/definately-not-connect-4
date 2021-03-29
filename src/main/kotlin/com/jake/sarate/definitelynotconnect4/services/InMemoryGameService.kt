package com.jake.sarate.definitelynotconnect4.services

import com.jake.sarate.definitelynotconnect4.components.*
import com.jake.sarate.definitelynotconnect4.models.GameRequest
import com.jake.sarate.definitelynotconnect4.models.GameSettings
import org.springframework.stereotype.Service

@Service
class InMemoryGameService(private val gameFactory: GameFactory): GameService {
    private val gameMap: MutableMap<String, Game> = mutableMapOf()

    override fun createGame(gameRequest: GameRequest): Game {
        val gameSettings = GameSettings(
            gameType = GameType.BASIC,
            gameBoardType = GameBoardType.BASIC,
            players = gameRequest.players,
            columns = gameRequest.columns,
            rows = gameRequest.rows
        )
        val game = gameFactory.create(gameSettings)
        gameMap[game.gameId] = game
        return game
    }

    override fun getGame(gameId: String): Game? {
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

    override fun listGames(): List<Game> {
        return gameMap.values.toList()
    }
}