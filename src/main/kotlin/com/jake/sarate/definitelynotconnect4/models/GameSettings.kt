package com.jake.sarate.definitelynotconnect4.models

import com.jake.sarate.definitelynotconnect4.components.GameBoardType
import com.jake.sarate.definitelynotconnect4.components.GameType

data class GameSettings(val gameType: GameType, val gameBoardType: GameBoardType, val players: List<String>, val columns: Int, val rows: Int)
