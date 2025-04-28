/*
 * DeleteItemInDbException.kt
 * 17.10.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package utils.exception


class DeleteItemInDbException : Exception {

    companion object {
        private val serialVersionUID = 468410577832071234L
    }

    constructor() {
    }

    constructor(message: String) : super(message) {}

    constructor(cause: Throwable) : super(cause) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

}