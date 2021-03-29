package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.PlayerToken

interface GameBoard {
    val boardId: String
    fun dropToken(column: Int, playerId: String): PlayerToken
    fun getCurrentState(): Array<Array<String>>
}