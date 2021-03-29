package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.GameException

class InvalidColumnSpecificationException(message: String = GameException.INVALID_COLUMN_SPECIFICATION): Exception(message)