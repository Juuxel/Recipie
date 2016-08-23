package juuxel.recipie.ui.skin

import com.alee.laf.*
import org.pushingpixels.substance.api.skin.*

val skins = listOf(WebSkin, RavenSkin, SaharaSkin, SilverSkin)

object WebSkin : Skin()
{
    override val name = "Web"
    override val lookAndFeel = lazy { WebLookAndFeel() }
}

object RavenSkin : Skin()
{
    override val name = "Raven"
    override val lookAndFeel = lazy { SubstanceRavenLookAndFeel() }
}

object SaharaSkin : Skin()
{
    override val name = "Sahara"
    override val lookAndFeel = lazy { SubstanceSaharaLookAndFeel() }
}

object SilverSkin : Skin()
{
    override val name = "Silver"
    override val lookAndFeel = lazy { SubstanceOfficeSilver2007LookAndFeel() }
}
