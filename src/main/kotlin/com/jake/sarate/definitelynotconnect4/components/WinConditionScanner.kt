package com.jake.sarate.definitelynotconnect4.components

interface WinConditionScanner {
    fun scanForWinner(gameBoard: GameBoard): GameScanResult
}