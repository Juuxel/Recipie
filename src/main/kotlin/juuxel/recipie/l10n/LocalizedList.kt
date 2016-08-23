package juuxel.recipie.l10n

import juuxel.recipie.*

class LocalizedList<E> : LocalizedObject<List<E>>()
{
    @Suppress("IMPLICIT_CAST_TO_ANY") // It's confirmed: IDEA is crazy.
    fun addTo(lang: String, vararg elements: E)
    {
        map[lang]?.let {
            if (it is MutableList<E>)
                it.addAll(elements)
            else // Everything SHOULD be mutable; if something's not, it will be
            {
                val list = mutableListOf<E>()
                list.addAll(it)
                list.addAll(elements)
                map.put(lang, list)
            }
        }
    }

    override fun get(): List<E>?
    {
        val mutableList = mutableListOf<E>()

        if (map.contains("common"))
        {
            map["common"]?.let {
                mutableList.addAll(it)
            }
        }

        if (map.contains(language))
        {
            map[language]?.let {
                mutableList.addAll(it)
            }
        }

        return mutableList.unmodifiableCopy()
    }
}
