package nl.rabobank.kotlinmovement.recipes.service;

import nl.rabobank.kotlinmovement.recipes.data.entity.IngredientsEntity;
import nl.rabobank.kotlinmovement.recipes.data.entity.RecipesEntity;
import nl.rabobank.kotlinmovement.recipes.domain.IngredientResponse;
import nl.rabobank.kotlinmovement.recipes.domain.IngredientType;
import nl.rabobank.kotlinmovement.recipes.domain.RecipeRequest;
import nl.rabobank.kotlinmovement.recipes.domain.RecipeResponse;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

final class RecipesMapper {
    static RecipesEntity toRecipeEntity(RecipeRequest recipeRequest) {
        return new RecipesEntity(null, recipeRequest.getRecipeName(), emptySet());
    }

    static Set<IngredientsEntity> toIngredientsEntity(RecipeRequest recipeRequest, RecipesEntity recipe) {
        return recipeRequest.getIngredients()
                .stream()
                .map(it -> new IngredientsEntity(recipe, null, it.getName(), it.getType().name(), it.getWeight()))
                .collect(Collectors.toSet());
    }

    static RecipeResponse toRecipeResponse(RecipesEntity recipes, Set<IngredientsEntity> ingredientsEntities) {
        return new RecipeResponse(recipes.getId(), recipes.getRecipeName(),
                ingredientsEntities.stream()
                        .map(it -> new IngredientResponse(it.getId(), it.getName(), IngredientType.valueOf(it.getType()), it.getWeight()))
                        .collect(Collectors.toSet()));
    }

}