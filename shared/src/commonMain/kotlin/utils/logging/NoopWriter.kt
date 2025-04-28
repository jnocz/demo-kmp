package utils.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity

class NoopWriter(private val isProduction:Boolean) : LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean = isProduction
}