package utils.exception

import androidx.compose.runtime.Stable
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class ExceptionHandler {

//    suspend fun getError(responseContent: ByteReadChannel): Error {
//        responseContent.readUTF8Line()?.let {
//            return Json(JsonConfiguration.Stable).parse(Error.serializer(), it)
//        }
//        throw IllegalArgumentException("not a parsable error")
//    }

}