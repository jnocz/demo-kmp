/*
 * SearchedPages.kt
 * 10.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package engine.domain


enum class SearchedPage(
    val pageName: String,
    val dbName: String,
    val domainUrl: String,
    val prettyDomainUrl: String
) {
    BAZOS_CZ("BAZOS_CZ", "BAZOS_CZ", "https://www.bazos.cz", "bazos.cz")
}