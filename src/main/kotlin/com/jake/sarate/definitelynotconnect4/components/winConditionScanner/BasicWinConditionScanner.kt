package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.components.gameboard.GameBoard
import com.jake.sarate.definitelynotconnect4.components.winConditionScanner.WinConditionScanner
import org.springframework.stereotype.Component

data class GameScanResult(val winner: String?, val isBoardFull: Boolean)

@Component
class BasicWinConditionScanner: WinConditionScanner {

    override fun scanForWinner(gameBoard: GameBoard): GameScanResult {
        printBoard(gameBoard)
        isThereAWinnerByColumn(gameBoard)?.let {
            println("Winner By Column: $it")
            return GameScanResult(it, false)
        }
        findWinnerByRow(gameBoard)?.let {
            println("WinnerByRow:$it")
            return GameScanResult(it, false)
        }
        findWinnerByDiagonalRow(gameBoard)?.let {
            println("WinnerByDiagonalRow:$it")
            return GameScanResult(it, false)
        }
        return GameScanResult(null, isGameBoardFull(gameBoard))
    }

    private fun isGameBoardFull(gameBoard: GameBoard): Boolean {
        var isFull = true
        val columns = gameBoard.getCurrentState()
        columns.forEach {
            if(!itDoesNotHaveOpenSlots(it, "open")) {
                isFull = false
                return@forEach
            }
        }
        return isFull
    }

    private fun isThereAWinnerByColumn(gameBoard: GameBoard): String? {
        val columns = gameBoard.getCurrentState()
        return findWinnerIn(columns)
    }

    private fun findWinnerIn(columnsOrRows: Array<Array<String>>): String? {
        var winner: String? = null
        columnsOrRows.forEach columnLoop@{ columnOrRow ->
            if(itDoesNotHaveOpenSlots(columnOrRow, "open")) {
                val player:String = columnOrRow[0]
                isPlayerTheWinner(player, columnOrRow).let { result ->
                    if(result) {
                        winner = player
                        return@columnLoop
                    }
                }
            }
        }
        return winner
    }

    private fun findWinnerByRow(gameBoard: GameBoard): String? {
        val rowValues = getBoardAsRows(gameBoard)
        return findWinnerIn(rowValues)
    }

    private fun findWinnerByDiagonalRow(gameBoard: GameBoard): String? {
        val diagonalRows = getDiagonalRowsFromBoard(gameBoard)
        val stringBuilder = StringBuilder()
        diagonalRows.forEachIndexed { index, rows ->
            stringBuilder.append("|$index")
            rows.forEach {
                stringBuilder.append("|$it")
            }
            stringBuilder.append("|\n")
        }
        println(stringBuilder.toString())
        return findWinnerIn(diagonalRows)
    }

    private fun itDoesNotHaveOpenSlots(rowOrColumn: Array<String>, freeSlotValue: String): Boolean {
        return !rowOrColumn.contains(freeSlotValue)
    }

    private fun isPlayerTheWinner(player: String, rowOrColumn: Array<String>): Boolean {
        var winner = true
        rowOrColumn.forEachIndexed forLoop@{ index, columnValue ->
            if (columnValue != player) {
                winner = false
                return@forLoop
            }
        }
        return winner
    }

    private fun printBoard(gameBoard: GameBoard) {
        val rows = getBoardAsRows(gameBoard)
        val stringBuilder = StringBuilder()
        rows.forEachIndexed {index, strings ->
            stringBuilder.append("|${index}")
            strings.forEach {
                stringBuilder.append("| $it ")
            }
            stringBuilder.append("|\n")
        }
        println(stringBuilder.toString())
    }

    private fun getBoardAsRows(gameBoard: GameBoard): Array<Array<String>> {
        val rowsMap: MutableMap<Int, MutableList<String>> = mutableMapOf()
        val columns = gameBoard.getCurrentState()
        columns.forEach { columnValues ->
            columnValues.indices.forEach { index ->
                if (rowsMap.containsKey(index)) {
                    rowsMap[index]?.add(columnValues[index])
                } else {
                    rowsMap[index] = mutableListOf(columnValues[index])
                }
            }
        }
        val twoDArray: MutableList<Array<String>> = mutableListOf()
        rowsMap.entries.forEach {
            twoDArray.add(it.value.toTypedArray())
        }
        return twoDArray.toTypedArray()
    }

    private fun getDiagonalRowsFromBoard(gameBoard: GameBoard): Array<Array<String>> {
        val columns = gameBoard.getCurrentState()
        val diagonalRow1: MutableList<String> = mutableListOf()
        val diagonalRow2: MutableList<String> = mutableListOf()
        columns.forEachIndexed {index, column ->
            diagonalRow1.add(column[index])
        }
        columns.reversedArray().forEachIndexed { index, column ->
            diagonalRow2.add(column[index])
        }
        return arrayOf(diagonalRow1.toTypedArray(), diagonalRow2.toTypedArray())
    }
}