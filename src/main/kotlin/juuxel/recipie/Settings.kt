package juuxel.recipie

import com.beust.klaxon.*
import java.io.*
import java.nio.file.*

val settings: JsonObject = initSettings()
private val settingsPath = Paths.get("${juuxel.recipie.userDir}/settings.json")

fun initSettings(): JsonObject
{
    var json = json { obj() }
    json.put("skin", "Web")

    try
    {
        if (Files.exists(settingsPath)) // Workdir as settings dir, TODO?
            json = Parser().parse(Files.newInputStream(settingsPath)) as JsonObject
        else
            exportSettings()
    }
    catch (e: IOException)
    {
        e.printStackTrace()
    }

    return json
}

fun exportSettings()
{
    Files.write(settingsPath, listOf(settings.toJsonString(true)), StandardOpenOption.CREATE_NEW)
}
