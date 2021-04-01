package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.components.game.BasicGame
import com.jake.sarate.definitelynotconnect4.components.game.Game
import com.jake.sarate.definitelynotconnect4.components.winConditionScanner.WinConditionScanner
import com.jake.sarate.definitelynotconnect4.models.GameSettings
import org.springframework.stereotype.Component

enum class GameType {
    BASIC
}

@Component
class GameFactory(private val gameBoardFactory: GameBoardFactory, private val winConditionScanner: WinConditionScanner) {

    fun create(gameSettings: GameSettings): Game {
        return when(gameSettings.gameType) {
            GameType.BASIC -> BasicGame(gameSettings, gameBoardFactory, winConditionScanner)
        }
    }
}