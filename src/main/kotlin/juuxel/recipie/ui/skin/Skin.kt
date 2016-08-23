package juuxel.recipie.ui.skin

import javax.swing.*

abstract class Skin
{
    abstract val name: String
    abstract val lookAndFeel: Lazy<LookAndFeel>

    override fun toString() = name
}