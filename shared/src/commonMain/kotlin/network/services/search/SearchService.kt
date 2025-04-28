/*
 * SearchService.kt
 * 29.07.2024
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2024. All rights reserved.
 *
 */

package network.services.search

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.Query

interface SearchService {

    @Headers(
        "User-Agent: Mozilla",
        "Content-Type: text/html"
    )
    @GET("https://www.bazos.cz/search.php")
    suspend fun getBazosCZSearchResults(
        @Query("hledat") query: String = ""
    ): Response<String>

    @Headers(
        "User-Agent: Mozilla",
        "Content-Type: text/html"
    )
    @GET("https://www.seznam.cz")
    suspend fun getSeznamTestResponse(
    ): String

}