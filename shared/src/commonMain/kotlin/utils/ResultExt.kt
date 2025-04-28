package utils

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

suspend inline fun <T> invoke(noinline block: suspend () -> T): T = withContext(Dispatchers.IO) {
    block()
}

inline fun <R> resultOf(block: () -> R): kotlin.Result<R> {
    return try {
        kotlin.Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Logger.e(e.message ?: "", e)
        kotlin.Result.failure(e)
    }
}


