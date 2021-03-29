package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.PlayerToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BasicGameBoardTests {
    @Test
    fun itShouldAcceptAValidMove() {
        val board = BasicGameBoard(4, 4)
        val moveResult = board.dropToken(0, "1")
        assertEquals(PlayerToken("1", board.boardId, 0, 0), moveResult)
    }

    @Test
    fun itShouldDropATokenInTheNextAvailableSlot() {
        val board = BasicGameBoard(1, 2)
        board.dropToken(0, "1")
        val moveResult = board.dropToken(0, "1")
        assertEquals(PlayerToken("1", board.boardId, 0, 1), moveResult)
    }

    @Test
    fun itShouldRejectAMoveWhenColumnIsFull() {
        val board = BasicGameBoard(1, 1)
        board.dropToken(0, "1")
        try {
            board.dropToken(0, "1")
        } catch (e: NoAvailableSpacesException) {
            assertEquals("No available spaces for the specified column", e.message)
        } catch (e: Exception) {
            throw Exception("Test Failed with error: ${e.message}")
        }
    }

    @Test
    fun itShouldRejectAMoveToAColumnThatDoesNotExist() {
        val board = BasicGameBoard(1, 1)
        try {
            board.dropToken(10, "1")
        } catch (e: InvalidColumnSpecificationException) {
            assertEquals("Invalid column specified. Column does not exist.", e.message)
        } catch (e: Exception) {
            throw Exception("Test Failed with error: ${e.message}")
        }
    }

    @Test
    fun itShouldReturnTheCurrentStateOfTheBoard() {
        val board = BasicGameBoard(1, 1)
        board.dropToken(0, "1")
        assertEquals("1", board.getCurrentState()[0][0])
    }
}