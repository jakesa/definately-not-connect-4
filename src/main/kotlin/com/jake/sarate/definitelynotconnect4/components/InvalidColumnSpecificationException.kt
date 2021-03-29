package com.jake.sarate.definitelynotconnect4.components

import com.jake.sarate.definitelynotconnect4.models.PlayerMoveException

class InvalidColumnSpecificationException(message: String = PlayerMoveException.INVALID_COLUMN_SPECIFICATION): Exception(message)