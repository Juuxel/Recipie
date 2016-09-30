package juuxel.recipie

import java.awt.*
import java.util.*

// Extensions for the standard libraries of Java and Kotlin

fun String.smartFormat(vararg args: Pair<String, Any?>)
        = smartFormat(args.toList())

fun String.smartFormat(list: List<Pair<String, Any?>>): String
{
    var str = this

    list.forEach { pair ->
        str = str.replace("\\{${pair.first}}".toRegex(), pair.second.asString())
    }

    return str
}

fun String.smartSplit(regex: Regex)
    = if (matches(".*${regex.toString()}.*".toRegex()))
          split(regex).toList()
      else
          listOf(this)

fun Dimension.component1(): Int = width
fun Dimension.component2(): Int = height

fun Any?.asString(): String
        = when (this)
        {
            is String -> this
            null -> "null"
            else -> toString()
        }
/*
/**
 * If `this` is not `null`, makes `this` the target of [action].
 * Alternative to `let`.
 */
fun <T> T?.center(action: (T) -> Unit)
{
    if (this != null)
        action.invoke(this)
}
*/

fun <R> (() -> R).invokeSafely(): R?
        = try { invoke() } catch (e: Throwable) { null }

fun <E> List<E>.unmodifiableCopy(): List<E> = Collections.unmodifiableList(this)
