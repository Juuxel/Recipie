package juuxel.recipie.l10n

import com.alee.managers.language.*
import juuxel.recipie.*
import java.util.*

// TODO Why is this here??
val language: String
    get() = Locale.getDefault().language

private val bundle: ResourceBundle = ResourceBundle.getBundle("juuxel.recipie.Resources")

fun localize(key: String): String = bundle.getString(key)

/*------*
 * TODO *
 *------*/
/**
 * This function bridges content from [bundle] to WebLaF's `LanguageManager`.
 */
// I don't like having two l10n files for each language so I just made this bridge function.
// (I need some l10n features from WebLaF, but I'm too lazy to switch to its LanguageManager...)
fun bridgeToLanguageManager()
{
    //LanguageManager.addSupportedLanguage(bundle.locale.language)
}
