package network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.ResponseConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import utils.exception.UnauthorizedResponseException
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

internal val networkModule
    get() = module {
        single { provideKtorfit() }
    }

fun provideKtorfit(
): Ktorfit {
    return ktorfit {
        baseUrl("https://www.google.com/")
        httpClient(HttpClient {
            expectSuccess = false

            defaultRequest {
                headers.appendIfNameAbsent(
                    HttpHeaders.ContentType, "application/json; charset=utf-8"
                )
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 15.seconds.inWholeMilliseconds
                requestTimeoutMillis = 30.seconds.inWholeMilliseconds
                socketTimeoutMillis = 2.minutes.inWholeMilliseconds
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true // Useful for debugging
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    },
                    contentType = ContentType.Application.Json,//ContentType.Any
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        co.touchlab.kermit.Logger.i("HttpClient") { message }
                    }
                }
            }

            //CACHE
            install(HttpCache)

            //Response Validator
            HttpResponseValidator {
                validateResponse { response: HttpResponse ->

                    val statusCode = response.status.value

                    co.touchlab.kermit.Logger.d("HTTP status: $statusCode")

                    if (statusCode == 401) {
                        throw UnauthorizedResponseException(response, "")
                    }

                    when (statusCode) {
                        in 300..399 -> throw RedirectResponseException(response, "")
                        in 400..499 -> throw ClientRequestException(response, "")
                        in 500..599 -> throw ServerResponseException(response, "")
                    }

                    if (statusCode >= 600) {
                        throw ResponseException(response, "")
                    }
                }

                handleResponseExceptionWithRequest { throwable: Throwable, httpRequest: HttpRequest ->
                    co.touchlab.kermit.Logger.e("${throwable.message}", throwable)
                    throw throwable
                }
            }
        })
        converterFactories(
            ResponseConverterFactory()
        )
    }
}
