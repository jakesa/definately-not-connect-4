package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.GameException
import java.lang.Exception

class NoAvailableSpacesException(message: String = GameException.NO_AVAILABLE_SPACES): Exception(message)