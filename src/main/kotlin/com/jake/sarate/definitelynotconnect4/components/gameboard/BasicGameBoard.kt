package com.jake.sarate.definitelynotconnect4.components.gameboard

import com.jake.sarate.definitelynotconnect4.components.exceptions.InvalidColumnSpecificationException
import com.jake.sarate.definitelynotconnect4.components.exceptions.NoAvailableSpacesException
import com.jake.sarate.definitelynotconnect4.models.PlayerToken
import java.util.*

class BasicGameBoard(columns: Int, rows: Int): GameBoard {

    private val gameBoard = buildBoard(columns, rows)
    override val boardId = UUID.randomUUID().toString()

    override fun dropToken(column: Int, playerId: String): PlayerToken {
        try {
            if (gameBoard[column].contains("open")) {
                val tokenIndex = gameBoard[column].indexOfFirst { it == "open" }
                gameBoard[column][tokenIndex] = playerId
                return PlayerToken(playerId, boardId, column, tokenIndex)
            } else {
                throw NoAvailableSpacesException()
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw InvalidColumnSpecificationException()
        }
    }

    override fun getCurrentState(): Array<Array<String>> {
        return deepCopy2DArray(gameBoard)
    }

    private fun buildBoard(columnLength: Int, rowLength: Int): Array<Array<String>> {
        val columnList: MutableList<Array<String>> = mutableListOf()
        var columnIndex = 0
        while(columnIndex < columnLength) {
            val a: MutableList<String> = mutableListOf()
            for (i in 0 until rowLength) {
                a.add(i, "open")
            }
            columnList.add(columnIndex, a.toTypedArray())
            columnIndex ++
        }

        return columnList.toTypedArray()

    }

    private fun deepCopy2DArray(originalArray: Array<Array<String>>): Array<Array<String>> {
        val arrayCopy: MutableList<Array<String>> = mutableListOf()
        val originalArrayIterator = originalArray.iterator().withIndex()
        while(originalArrayIterator.hasNext()) {
            val el = originalArrayIterator.next()
            arrayCopy.add(el.index, el.value.clone())
        }
        return arrayCopy.toTypedArray()
    }

}