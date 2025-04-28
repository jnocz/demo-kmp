package utils.exception


import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse


class UnauthorizedResponseException(
    response: HttpResponse,
    cachedResponseText: String
) : ResponseException(response, cachedResponseText) {

    override val message: String =
        "Client request(${response.call.request.method.value} ${response.call.request.url}) " +
                "invalid: ${response.status}. Text: \"$cachedResponseText\""
}