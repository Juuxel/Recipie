package juuxel.recipie.l10n

// TODO Remove this object and replace L10n[...] -> localize(...)
object L10n
{
    operator fun get(key: String): String = localize(key)
}
