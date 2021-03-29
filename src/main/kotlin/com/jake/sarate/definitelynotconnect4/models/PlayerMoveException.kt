package com.jake.sarate.definitelynotconnect4.models

class PlayerMoveException {
    companion object {
        val NO_AVAILABLE_SPACES = "No available spaces for the specified column"
        val INVALID_COLUMN_SPECIFICATION = "Invalid column specified. Column does not exist."
        val UNKNOWN_PLAYER_MOVE_EXCEPTION = "An unknown error occurred during player move."
        val PLAYED_MOVE_OUT_OF_TURN = "Player tried to post when it's not their turn."
    }
}