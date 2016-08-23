package juuxel.recipie.l10n

import juuxel.recipie.*

import java.util.ResourceBundle

object L10n
{
    private val bundle: ResourceBundle = ResourceBundle.getBundle("juuxel.recipie.Resources")

    operator fun get(key: String): String = bundle.getString(key)

    operator fun get(key: String, vararg args: Pair<String, Any?>)
        = this[key].smartFormat(args.toList())
}
