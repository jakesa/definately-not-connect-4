package com.jake.sarate.definitelynotconnect4.models

import com.jake.sarate.definitelynotconnect4.models.constants.PlayerMoveType

data class PlayerMove(val type: PlayerMoveType, val player: String, val column: Int?)
