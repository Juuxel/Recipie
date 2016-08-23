package juuxel.recipie

import com.alee.laf.*
import juuxel.recipie.settings.*
import juuxel.recipie.ui.*
import java.awt.*
import java.nio.file.*
import java.util.*
import javax.swing.*

val version = "0.1.0-SNAPSHOT"

// This is marked lazy,
// because otherwise it's initialized here,
// but visualized after setting the LaF.
// That causes it to use a weird combination
// of the default LaF and the set LaF.
val frame = lazy { createWindow() }

// These are the app's settings
val settings = Settings(Paths.get("."))

fun main(args: Array<String>)
{
    // WebLaF seems to be setting the default lang
    // to en-GB, so we reset it
    val locale = Locale.getDefault()
    WebLookAndFeel.install()
    Locale.setDefault(locale)

    invokeOnEDT { updateLookAndFeel() }

    frame.value.isVisible = true
}

fun createWindow(): JFrame
{
    val frame = JFrame("Recipie")

    frame.size = Dimension(640, 440)
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.contentPane = MainPane()

    return frame
}
