package com.jake.sarate.definitelynotconnect4.models

import com.jake.sarate.definitelynotconnect4.models.constants.MoveResultStatus
import com.jake.sarate.definitelynotconnect4.models.constants.PlayerMoveType

data class PlayerMoveResult(
    val playerId: String,
    val moveType: PlayerMoveType,
    val status: MoveResultStatus,
    val column: Int?,
    val row: Int?,
    val exception: String?
    )