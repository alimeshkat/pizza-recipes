package nl.rabobank.kotlinmovement.recipes

import nl.rabobank.kotlinmovement.recipes.test.util.RecipeAssert.assertRecipeResponse
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTest
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.emptyRequest
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.emptyRequestIngredient
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectIngredientName
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectIngredientType
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectIngredients
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectRecipe
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectRecipeName
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.errorMessageIncorrectWeight
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.ingredientMissingName
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.ingredientMissingType
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.ingredientMissingWeight
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.nullIngredientsRequest
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.nullRecipeNameRequest
import nl.rabobank.kotlinmovement.recipes.test.util.RecipeTestData.peperoniPizzaRecipeRequest
import nl.rabobank.kotlinmovement.recipes.test.util.model.RecipeRequestTest
import nl.rabobank.kotlinmovement.recipes.test.util.model.RecipesErrorResponseTest
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpMethod
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CreateUpdateRecipesControllerTest : RecipeTest() {
    @Test
    @Throws(Exception::class)
    fun `Should be able to update a recipe`() {
        val updateRequest: RecipeRequestTest = peperoniPizzaRecipeRequest
        val response = updateRecipe(1L, updateRequest)
        assertRecipeResponse(updateRequest, response)
    }

    @Test
    @Throws(
        Exception::class
    )
    fun `Should be to able create new recipe when recipe id doesn't not exist`() {
        val updateRequest: RecipeRequestTest = peperoniPizzaRecipeRequest
        val response = updateRecipe(2L, updateRequest)
        assertRecipeResponse(updateRequest, response)
    }

    @ParameterizedTest
    @MethodSource("errorDataParams")
    @Throws(
        Exception::class
    )
    fun `Should not be able to create or update if request object is invalid`(
        recipeRequest: RecipeRequestTest?,
        errorResponse: RecipesErrorResponseTest?
    ) {
        val body = objectMapper.writeValueAsString(recipeRequest)
        val invalidCreateResponse = badRequestCall(HttpMethod.POST, "/recipes", body)
        val invalidUpdateResponse = badRequestCall(HttpMethod.PUT, "/recipes/1", body)
        AssertionsForClassTypes.assertThat(invalidCreateResponse).isEqualTo(errorResponse)
        AssertionsForClassTypes.assertThat(invalidUpdateResponse).isEqualTo(errorResponse)
    }

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        setInitialState()
    }

    private fun errorDataParams(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(emptyRequest, errorMessageIncorrectRecipe),
            Arguments.of(emptyRequestIngredient, errorMessageIncorrectIngredients),
            Arguments.of(nullRecipeNameRequest, errorMessageIncorrectRecipeName),
            Arguments.of(nullIngredientsRequest, errorMessageIncorrectIngredients),
            Arguments.of(ingredientMissingName, errorMessageIncorrectIngredientName),
            Arguments.of(ingredientMissingType, errorMessageIncorrectIngredientType),
            Arguments.of(ingredientMissingWeight, errorMessageIncorrectWeight)
        )
    }

}