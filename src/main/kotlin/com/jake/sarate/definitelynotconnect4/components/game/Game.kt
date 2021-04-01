package com.jake.sarate.definitelynotconnect4.components.game

import com.jake.sarate.definitelynotconnect4.models.*
import com.jake.sarate.definitelynotconnect4.models.constants.GameState

interface Game {
    val gameId: String
    val players: List<String>
    val columns: Int
    val rows: Int
    val winner: String?
    val currentTurn: String
    val state: GameState
    fun quit(playerId: String): Pair<Int, PlayerMoveResult>
    fun attemptPlayerMove(playerId: String, column: Int): Pair<Int, PlayerMoveResult>
    fun listMoves(start: Int?, until: Int?): List<PlayerMoveResult>
    fun getMove(moveNumber: Int): PlayerMoveResult?
}