@file:JvmName("UIUtils")
package juuxel.recipie.ui

import com.alee.laf.*
import com.beust.klaxon.*
import juuxel.recipie.*
import juuxel.recipie.l10n.*
import juuxel.recipie.ui.skin.*
import java.awt.*
import java.awt.image.*
import java.io.*
import java.net.*
import javax.imageio.*
import javax.swing.*

fun updateLookAndFeel()
{
    invokeOnEDT {
        UIManager.setLookAndFeel(skins.find { it.name == settings.string("skin") }?.lookAndFeel?.value)
        WebLookAndFeel.updateAllComponentUIs()
    }
}

fun invokeOnEDT(action: () -> Unit)
    = if (!SwingUtilities.isEventDispatchThread()) SwingUtilities.invokeLater(action)
      else action.invoke()

/** This file filter accepts only .recipe.json files or directories */
val recipeFilter = object : javax.swing.filechooser.FileFilter() {
    override fun accept(f: File?): Boolean
    {
        return f?.let {
            it.name.endsWith(".recipe.json") || it.isDirectory
        } ?: false
    }

    override fun getDescription() = localize("recipe.fileType")
}

// Tab utils

/**
 * Creates a nice tab title component for [title] and [buttonComp].
 *
 * ASCII representation: `INSERT TITLE HERE  buttonComp`
 */
fun createTabTitleComponent(title: String, buttonComp: Component): JPanel
{
    val panel = JPanel()

    panel.layout = BoxLayout(panel, BoxLayout.X_AXIS)
    panel.isOpaque = false
    panel.add(JLabel(title))
    panel.add(Box.createHorizontalStrut(4))
    panel.add(buttonComp)

    return panel
}

/**
 * Creates a tab close button for [tabPane] and [component].
 *
 * [component] is used for detecting the tab, because index can change.
 */
fun createTabCloseButton(tabPane: JTabbedPane, component: Component): JButton
{
    val button = JButton(Images.newIcon(Images.CLOSE_TAB))
    button.addActionListener {
        tabPane.removeTabAt(tabPane.indexOfComponent(component))
    }
    return button
}

/**
 * This class performs operations on images.
 */
object Images // TODO Move methods to root level
{
    // Icons
    val SETTINGS_ICON = getImage(Images::class.java.getResource("/juuxel/recipie/icons/settings.png"))
    val CREATE_RECIPE = getImage(Images::class.java.getResource("/juuxel/recipie/icons/new-recipe.png"))
    val IMPORT_RECIPE = getImage(Images::class.java.getResource("/juuxel/recipie/icons/import-recipe.png"))
    val SEARCH        = getImage(Images::class.java.getResource("/juuxel/recipie/icons/search.png"))
    val CLOSE_TAB     = getImage(Images::class.java.getResource("/juuxel/recipie/icons/close-tab.png"))

    /**
     * Creates a new `ImageIcon` from a `BufferedImage`.
     * Takes a width and a height.
     *
     * @param image the icon image
     * @param width the width
     * @param height the height
     * @return the new icon
     */
    fun newIcon(image: BufferedImage, width: Int = 16, height: Int = 16): ImageIcon
    {
        return ImageIcon(resizeImage(image, width, height))
    }

    /**
     * Reads an image from an URL.
     *
     * @param url the URL
     * @return the image
     */
    fun getImage(url: URL): BufferedImage
    {
        try
        {
            return ImageIO.read(url)
        }
        catch (e: IOException)
        {
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }

    /**
     * Resizes a `BufferedImage`.

     * @param image the original image
     * *
     * @param width the new width
     * *
     * @param height the new height
     * *
     * @return the resized image
     */
    fun resizeImage(image: BufferedImage, width: Int, height: Int): BufferedImage
    {
        val img = BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR)
        img.graphics.drawImage(image, 0, 0, width, height, null)
        return img
    }
}