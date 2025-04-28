/*
 * NetworkException.kt
 * 19.03.2019
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2019. All rights reserved.
 *
 */
package utils.exception


class NetworkException : Exception {

    companion object {
        private val serialVersionUID = 6300641957560001121L
    }

    constructor() {
    }

    constructor(message: String) : super(message) {}

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}


}