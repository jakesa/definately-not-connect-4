package com.jake.sarate.definitelynotconnect4.models.responses

import com.jake.sarate.definitelynotconnect4.models.constants.GameState

data class GetGameResponse(val players: List<String>, val state: GameState, val winner: String? = null)
