package com.jake.sarate.definitelynotconnect4.models

data class GetGameResponse(val players: Array<String>, val state: GameState, val winner: String? = null)
