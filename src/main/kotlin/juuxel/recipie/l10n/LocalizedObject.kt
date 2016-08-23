package juuxel.recipie.l10n

open class LocalizedObject<T>
{
    protected val map = hashMapOf<String, T>()

    fun add(lang: String, t: T)
    {
        map.put(lang, t)
    }

    open fun get(): T?
    {
        return if (map.containsKey(language))
            map[language]
        else
            map["common"]
    }
}
