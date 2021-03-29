package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.*

interface Game {
    val gameId: String
    val players: List<String>
    val columns: Int
    val rows: Int
    val winner: String?
    val currentTurn: String
    val state: GameState
    fun quit(playerId: String)
    fun attemptPlayerMove(playerId: String, column: Int): Pair<Int, PlayerMoveResult>
    fun listMoves(start: Int?, until: Int?): List<PlayerMoveResult>
}