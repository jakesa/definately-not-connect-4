package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.*
import java.util.*

class BasicGame(gameSettings: GameSettings, gameBoardFactory: GameBoardFactory): Game {

    override var currentTurn: String = gameSettings.players[0]
        private set
    override val gameId: String = UUID.randomUUID().toString()
    override val players: List<String> = gameSettings.players
    override val columns: Int = gameSettings.columns
    override val rows: Int = gameSettings.rows

    override var winner: String? = null
        private set
    override var state: GameState = GameState.IN_PROGRESS
        private set
    private val moves: MutableList<PlayerMoveResult> = mutableListOf()
    private val gameBoard = gameBoardFactory.create(gameSettings)

    override fun quit(playerId: String): Pair<Int, PlayerMoveResult> {
        val result: Pair<Int, PlayerMoveResult>
        if (state == GameState.DONE) {
            val moveResult =
                PlayerMoveResult(
                    playerId,
                    moveType = PlayerMoveType.QUIT,
                    column = null,
                    row = null,
                    exception = GameException.GAME_IS_ALREADY_DONE,
                    status = MoveResultStatus.UNSUCCESSFUL
                )
            moves.add(moveResult)
            return Pair(moves.lastIndex, moveResult)
        }
        if (players.contains(playerId) && state != GameState.DONE) {
            val moveResult = PlayerMoveResult(
                playerId,
                moveType = PlayerMoveType.QUIT,
                column = null,
                row = null,
                exception = null,
                status = MoveResultStatus.SUCCESSFUL
            )
            moves.add(moveResult)
            state = GameState.DONE
            result = Pair(moves.lastIndex, moveResult)
        } else {
            val moveResult = PlayerMoveResult(
                    playerId,
                    moveType = PlayerMoveType.QUIT,
                    column = null,
                    row = null,
                    exception = GameException.GAME_OR_PLAYER_NOT_FOUND,
                    status = MoveResultStatus.UNSUCCESSFUL
                )
            moves.add(moveResult)
            result = Pair(moves.lastIndex, moveResult)
        }
        return result
    }

    override fun attemptPlayerMove(playerId: String, column: Int): Pair<Int, PlayerMoveResult> {
        if (playerId != currentTurn) {
            val moveResult = PlayerMoveResult(
                playerId = playerId,
                moveType = PlayerMoveType.MOVE,
                status = MoveResultStatus.UNSUCCESSFUL,
                column = column,
                row = null,
                exception = GameException.PLAYED_MOVE_OUT_OF_TURN
            )
            moves.add(moveResult)
            return Pair(moves.lastIndex, moveResult)
        }
        try {
            val result = gameBoard.dropToken(column, playerId)
            val moveResult = PlayerMoveResult(
                playerId = result.playerId,
                moveType = PlayerMoveType.MOVE,
                status = MoveResultStatus.SUCCESSFUL,
                column = result.column,
                row = result.row,
                exception = null,
            )
            moves.add(moveResult)
            currentTurn = players.filterNot { it == playerId }[0]
            return Pair(moves.lastIndex, moveResult)
        } catch (e: InvalidColumnSpecificationException) {
            val moveResult = PlayerMoveResult(
                playerId = playerId,
                moveType = PlayerMoveType.MOVE,
                status = MoveResultStatus.UNSUCCESSFUL,
                column = column,
                row = null,
                exception = GameException.INVALID_COLUMN_SPECIFICATION
            )
            moves.add(moveResult)
            return Pair(moves.lastIndex, moveResult)
        } catch (e: NoAvailableSpacesException) {
            val moveResult = PlayerMoveResult(
                playerId = playerId,
                moveType = PlayerMoveType.MOVE,
                status = MoveResultStatus.UNSUCCESSFUL,
                column = column,
                row = null,
                exception = GameException.NO_AVAILABLE_SPACES
            )
            moves.add(moveResult)
            return Pair(moves.lastIndex, moveResult)
        } catch (e: Exception) {
            println("${e.message}\n${e.stackTrace}")

            val moveResult = PlayerMoveResult(
                playerId = playerId,
                moveType = PlayerMoveType.MOVE,
                status = MoveResultStatus.UNSUCCESSFUL,
                column = column,
                row = null,
                exception = GameException.UNKNOWN_PLAYER_MOVE_EXCEPTION
            )
            moves.add(moveResult)
            return Pair(moves.lastIndex, moveResult)
        }
    }

    override fun listMoves(start: Int?, until: Int?): List<PlayerMoveResult> {
        val successfulMoves = moves.filter { it.status == MoveResultStatus.SUCCESSFUL }
        return if (start !== null && until !== null) {
            val determinedStart = if (start < 0) { 0 } else { start }
            val determinedUntil = if (until > moves.lastIndex || until < determinedStart) { moves.lastIndex } else { until }
            moves.subList(determinedStart, determinedUntil)
        } else if (start !== null && start > 0) {
            moves.subList(start, moves.lastIndex)
        } else {
            successfulMoves
        }
    }

    override fun getMove(moveNumber: Int): PlayerMoveResult? {
        return if (moves.indices.contains(moveNumber)) {
            moves[moveNumber]
        } else {
            null
        }
    }
}