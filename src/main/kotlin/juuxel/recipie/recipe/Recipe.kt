package juuxel.recipie.recipe

import com.beust.klaxon.*
import juuxel.recipie.*
import juuxel.recipie.l10n.*

// TODO: JSON parsing
fun recipeOf(json: JsonObject): Recipe
        = Recipe(json.string("name")!!,
        json.array<JsonObject>("ingredients")!!
                .map { Recipe.Ingredient(it["name"].asString(), it["amount"].asString()) },
        json.array<String>("instructions")!!.toList(),
        json.string("author")!!,
        json.array<String>("imagePaths")!!.toList())

data class Recipe(
        val name: String,
        val ingredients: List<Ingredient>,
        val instructions: List<String>,
        val author: String,
        val images: List<String>
)
{
    val nameWithAuthor = L10n["recipe.titleFormat", "name".to(name), "author".to(author)]

    override fun toString() = nameWithAuthor

    data class Ingredient(val name: String, val amount: String)
}
