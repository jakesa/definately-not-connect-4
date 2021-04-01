package com.jake.sarate.definitelynotconnect4.models.constants

class GameException {
    companion object {
        val NO_AVAILABLE_SPACES = "No available spaces for the specified column"
        val INVALID_COLUMN_SPECIFICATION = "Invalid column specified. Column does not exist."
        val UNKNOWN_PLAYER_MOVE_EXCEPTION = "An unknown error occurred during player move."
        val PLAYED_MOVE_OUT_OF_TURN = "Player tried to post when it's not their turn."
        val GAME_OR_PLAYER_NOT_FOUND = "Game not found or player is not a part of it."
        val GAME_OR_MOVE_NOT_FOUND = "Game/moves not found"
        val GAME_IS_ALREADY_DONE = "Game is already in DONE state."
        val MALFORMED_INPUT_OR_ILLIGAL_MOVE = "Malformed input. Illegal move"
    }
}