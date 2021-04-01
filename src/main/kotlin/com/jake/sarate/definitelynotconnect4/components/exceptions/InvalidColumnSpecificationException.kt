package com.jake.sarate.definitelynotconnect4.components.exceptions

import com.jake.sarate.definitelynotconnect4.models.constants.GameException

class InvalidColumnSpecificationException(message: String = GameException.INVALID_COLUMN_SPECIFICATION): Exception(message)