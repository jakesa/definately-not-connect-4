package com.jake.sarate.definitelynotconnect4.models

data class GetGameResponse(val players: List<String>, val state: GameState, val winner: String? = null)
