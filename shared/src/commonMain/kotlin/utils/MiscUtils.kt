/*
 * MiscUtils.kt
 * 12.09.2018
 *
 * Created by Josef Novák (novak.josef@gmail.com)
 * Copyright (c) 2018. All rights reserved.
 *
 */
package utils


import androidx.datastore.core.Closeable
import kotlinx.io.IOException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun consume(f: () -> Unit): Boolean {
    f(); return true
}

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}

fun <T : Closeable> T?.closeQuietly() {
    try {
        this?.close()
    } catch (ignored: IOException) {
        // ignore
    }
}

fun String.notEmptyOrNull(): String? {
    return if (isNotEmpty()) this else null
}

fun String.notBlankOrNull(): String? {
    return if (isNotBlank()) this else null
}

fun Any?.notNull(f: () -> Unit) {
    if (this != null) {
        f()
    }
}

fun String?.notNullAndBlank(f: () -> Unit) {
    if (this != null && this.isNotBlank()) {
        f()
    }
}

fun String.unaccent(): String {
    val original = arrayOf("ę", "ě", "š", "č", "ř", "ž", "ý", "á", "í", "é")
    val normalized = arrayOf("e", "e", "s", "c", "r", "z", "y", "a", "i", "e")

    return this.map { it ->
        val index = original.indexOf(it.toString())
        if (index >= 0) normalized[index] else it
    }.joinToString("")
}



@OptIn(ExperimentalContracts::class)
inline fun <T> T?.ifNotNull(block: (T) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        block(this)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T1, T2> ifNotNull(t1: T1?, t2: T2?, block: (T1, T2) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (t1 != null && t2 != null) {
        block(t1, t2)
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <T1, T2, T3> ifNotNull(t1: T1?, t2: T2?, t3: T3?, block: (T1, T2, T3) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (t1 != null && t2 != null && t3 != null) {
        block(t1, t2, t3)
    }
}

fun grep(lines: List<String>, pattern: String, action: (String) -> Unit) {
    val regex = pattern.toRegex()
    lines.filter(regex::containsMatchIn)
        .forEach(action)
}