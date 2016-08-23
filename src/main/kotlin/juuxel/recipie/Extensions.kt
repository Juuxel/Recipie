package juuxel.recipie

import com.beust.klaxon.*
import juuxel.recipie.l10n.*
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

fun Dimension.component1(): Int = width
fun Dimension.component2(): Int = height

fun Any?.asString(): String
        = when (this)
        {
            is String -> this
            is LocalizedObject<*> -> this.get().asString()
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

// Extensions for external libraries

/*
fun JsonObject.localizedString(fieldName: String): LocalizedObject<String>
{
    val obj = obj(fieldName)

    var returnValue: LocalizedObject<String>? = null

    obj.center {
        val localizedObj = LocalizedObject<String>()

        for ((s, any) in it)
            localizedObj.add(s, any.asString())

        returnValue = localizedObj
    }

    if (returnValue != null)
        return returnValue!!

    throw NullPointerException("JSON object is null!")
}

fun <E> JsonObject.localizedList(fieldName: String): LocalizedObject<List<E>>
{
    val obj = obj(fieldName)

    var returnValue: LocalizedObject<List<E>>? = null

    obj.center {
        val localizedObj = LocalizedObject<List<E>>()
        val it2 = it

        it.keys.forEach {
            val list = it2.array<E>(it)
            if (list != null)
                localizedObj.add(it, list)
        }

        returnValue = localizedObj
    }

    if (returnValue != null)
        return returnValue!!

    throw NullPointerException("JSON object is null!")
}
*/
