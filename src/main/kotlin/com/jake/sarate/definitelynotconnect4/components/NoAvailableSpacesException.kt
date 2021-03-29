package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.PlayerMoveException
import java.lang.Exception

class NoAvailableSpacesException(message: String = PlayerMoveException.NO_AVAILABLE_SPACES): Exception(message)