/*
 * PageNotLoadException.kt
 * 10.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package utils.exception


class PageNotLoadedException : Exception {

    companion object {
        private val serialVersionUID = 6300641957560001124L
    }

    constructor() {
    }

    constructor(message: String) : super(message) {}

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}


}
