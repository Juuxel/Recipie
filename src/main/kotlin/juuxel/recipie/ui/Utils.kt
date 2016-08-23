@file:JvmName("UIUtils")
package juuxel.recipie.ui

import com.alee.laf.*
import com.beust.klaxon.*
import juuxel.recipie.*
import juuxel.recipie.ui.skin.*
import java.awt.image.*
import java.io.*
import java.net.*
import javax.imageio.*
import javax.swing.*

fun updateLookAndFeel()
{
    invokeOnEDT {
        UIManager.setLookAndFeel(skins.find { it.name == settings.json.string("skin") }?.lookAndFeel?.value)
        WebLookAndFeel.updateAllComponentUIs()
    }
}

fun invokeOnEDT(action: () -> Unit)
    = SwingUtilities.invokeLater(action)

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