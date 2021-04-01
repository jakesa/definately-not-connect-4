package com.jake.sarate.definitelynotconnect4.components.exceptions

import com.jake.sarate.definitelynotconnect4.models.constants.GameException
import java.lang.Exception

class NoAvailableSpacesException(message: String = GameException.NO_AVAILABLE_SPACES): Exception(message)