package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.GameSettings
import org.springframework.stereotype.Component

enum class GameBoardType {
    BASIC
}

@Component
class GameBoardFactory {
    fun create(gameSettings: GameSettings): GameBoard {
        return when(gameSettings.gameBoardType) {
            GameBoardType.BASIC -> BasicGameBoard(gameSettings.columns, gameSettings.rows)
        }
    }
}