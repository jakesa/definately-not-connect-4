package com.jake.sarate.definitelynotconnect4.components.winConditionScanner

import com.jake.sarate.definitelynotconnect4.components.GameScanResult
import com.jake.sarate.definitelynotconnect4.components.gameboard.GameBoard

interface WinConditionScanner {
    fun scanForWinner(gameBoard: GameBoard): GameScanResult
}