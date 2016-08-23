package juuxel.recipie.settings

import com.beust.klaxon.*
import juuxel.recipie.*
import java.io.IOException
import java.nio.file.*

/**
 * [Settings] is a Klaxon [JsonObject] mapped to different data providers,
 * like UI components. It's used for storing the application's settings.
 *
 * @param path the directory for the settings
 * @param json the [JsonObject] for storing the settings
 */
class Settings(path: Path,
               var json: JsonObject =
               com.beust.klaxon.json { obj() }
) : MutableMap<String, Any?> by json
{
    /**
     * The file path for the JSON file. It's generated with `path + "/settings.json"`.
     */
    val filePath: Path = Paths.get("${path.toAbsolutePath().toFile().path}/settings.json")

    private val map: MutableMap<String, Setting<*>> = hashMapOf()

    init
    {
        if (Files.notExists(path))
            Files.createDirectory(path)

        if (Files.exists(filePath))
        {
            json = Parser().parse(Files.newInputStream(filePath)) as JsonObject
        }
        else
        {
            Files.createFile(filePath)
            putDefaults()
            exportFile()
        }
    }

    private fun putDefaults()
    {
        put("skin", "Web")
    }

    /**
     * Exports the JSON data of [json] to the file located at [filePath].
     */
    @Throws(IOException::class)
    fun exportFile()
    {
        json.keys.forEach { update(it.asString()) }
        Files.write(filePath, listOf(json.toJsonString(true)))
    }

    /**
     * Maps the [provider] to the JSON entry for [key].
     *
     * @param key the fieldName
     * @param provider the provider
     */
    fun <T> map(key: String, provider: (() -> T))
    {
        map.put(key, Setting(key, provider))
    }

    /**
     * Updates the JSON entry for [key] to match the latest data
     * provided by the corresponding provider.
     *
     * @param key the fieldName
     */
    fun update(key: String)
    {
        json.put(key, map[key]!!.provider.invoke().asString())
    }

    private data class Setting<T>(val key: String,
                                  val provider: (() -> T)) : Comparable<Setting<T>>
    {
        override fun compareTo(other: Setting<T>): Int
                = key.compareTo(other.key)

        override fun equals(other: Any?): Boolean
                = other != null && other is Setting<*> && key.equals(other.key)

        override fun hashCode(): Int
                = key.hashCode()
    }
}
