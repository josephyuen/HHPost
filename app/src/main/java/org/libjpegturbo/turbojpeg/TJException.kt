package org.libjpegturbo.turbojpeg

import java.io.IOException

/**
 * Creator: 李佳胜
 * FuncDesc:
 * copyright  ©2018-2020 艾戴特 Corporation. All rights reserved.
 */
class TJException : IOException {

    var errorCode = ERR_FATAL
        private set

    constructor() : super() {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(message: String) : super(message) {}

    constructor(message: String, code: Int) : super(message) {
        if (errorCode >= 0 && errorCode < NUMERR)
            errorCode = code
    }

    constructor(cause: Throwable) : super(cause) {}

    companion object {

        val NUMERR = 2


        val ERR_WARNING = 0
        /**
         * The error was fatal and non-recoverable.
         */
        val ERR_FATAL = 1

        private val serialVersionUID = 1L
    }
}