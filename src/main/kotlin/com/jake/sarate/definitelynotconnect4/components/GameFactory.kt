package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.GameSettings
import org.springframework.stereotype.Component

enum class GameType {
    BASIC
}

@Component
class GameFactory(private val gameBoardFactory: GameBoardFactory) {

    fun create(gameSettings: GameSettings): Game {
        return when(gameSettings.gameType) {
            GameType.BASIC -> BasicGame(gameSettings, gameBoardFactory)
        }
    }
}