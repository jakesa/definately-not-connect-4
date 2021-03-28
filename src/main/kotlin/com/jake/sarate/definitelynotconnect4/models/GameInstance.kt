package com.jake.sarate.definitelynotconnect4.models

import java.util.*

typealias PlayerId = String

data class GameInstance(
    val gameId: String = UUID.randomUUID().toString(),
    val players: Array<PlayerId>,
    val columns: Int,
    val rows: Int,
    val state: GameState = GameState.IN_PROGRESS,
    val winner: String? = null
)
