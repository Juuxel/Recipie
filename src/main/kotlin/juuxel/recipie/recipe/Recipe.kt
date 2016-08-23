package juuxel.recipie.recipe

//import com.beust.klaxon.*
//import juuxel.recipie.*
import juuxel.recipie.l10n.*

/* TODO: JSON parsing
fun recipeOf(json: JsonObject): Recipe
        = Recipe(json.localizedString("name"),
        null,
        json.localizedList("instructions"),
        json.localizedString("author"),
        null)
        */

data class Recipe(
        val name: LocalizedObject<String>,
        val ingredients: LocalizedObject<List<Ingredient>>,
        val instructions: LocalizedObject<List<String>>,
        val author: LocalizedObject<String>,
        val images: List<String>
)
{
    val nameWithAuthor = L10n["recipe.titleFormat", "name".to(name), "author".to(author)]

    override fun toString() = nameWithAuthor

    data class Ingredient(val name: LocalizedObject<String>,
                          val amount: LocalizedObject<String>)
}
