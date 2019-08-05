package com.android.bakingfavorites.JsonUtils;

import android.util.Log;

import com.android.bakingfavorites.DetailFragments.IngredientsPojo;
import com.android.bakingfavorites.DetailFragments.StepsPojo;
import com.android.bakingfavorites.RecipePojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParsingUtil {
    static final String TAG = JsonParsingUtil.class.getSimpleName();

    public static RecipePojo parseRecipeJson (JSONArray RecipeJsonArray, int recipe_index) throws JSONException {

        //this class is used to convert the JSON into a recipe object.
        JSONObject recipe_item_json = RecipeJsonArray.getJSONObject(recipe_index);

        int recipe_id = recipe_item_json.getInt("id");
        String recipe_name = recipe_item_json.getString("name");
        JSONArray ingredients_list_array = recipe_item_json.getJSONArray("ingredients");
        List<String> ingredients_list = new ArrayList<>();
        for (int x = 0;  x < ingredients_list_array.length(); x++ ) {
            ingredients_list.add(ingredients_list_array.getString(x));
        }

        JSONArray recipe_steps_json_array = recipe_item_json.getJSONArray("steps");

        List<String> recipe_steps = new ArrayList<>();

        for ( int x = 0; x < recipe_steps_json_array.length(); x ++) {
            recipe_steps.add(recipe_steps_json_array.getString(x));
        }
        int serving_size = recipe_item_json.getInt("servings");

        RecipePojo aSingleRecipe = new RecipePojo(recipe_id, recipe_name, ingredients_list, recipe_steps, serving_size);

        return aSingleRecipe;
    }

    public static ArrayList<IngredientsPojo> convertIngredientsArray(ArrayList<String> ingredientsArray) throws JSONException {
        //this class will be used to create ingredients object in the details activity (ingredients fragment)
        JSONArray ingredientsJsonArray = new JSONArray(ingredientsArray);
        Log.e(TAG, "convertIngredientsArray1: " + ingredientsJsonArray.getString(0));
        ArrayList<IngredientsPojo> parsedIngredients = new ArrayList<>();
        for (int x = 0; x < ingredientsArray.size(); x++  ) {
            String singleStringIngredientIndex;
            singleStringIngredientIndex = ingredientsJsonArray.getString(x);
            JSONObject singleIngredientIndex = new JSONObject(singleStringIngredientIndex);
            int quantity = singleIngredientIndex.getInt("quantity");
            String measurement = singleIngredientIndex.getString("measure");
            String ingredient = singleIngredientIndex.getString("ingredient");
            IngredientsPojo parsedRecipieIngredient = new IngredientsPojo(quantity, measurement, ingredient);
            parsedRecipieIngredient.setmQuantity(quantity);
            parsedRecipieIngredient.setmMeasurement(measurement);
            parsedRecipieIngredient.setMingredient(ingredient);
            parsedIngredients.add(x, parsedRecipieIngredient);
        }
        Log.e(TAG, "convertIngredientsArray: " +  parsedIngredients.get(1).getMingredient());

        return parsedIngredients;

    }

    public static ArrayList<StepsPojo> convertStepsArray(ArrayList<String> stepsArray) throws JSONException {
        //another array parsing utility for the steps part of the json.
        JSONArray stepsJsonArray = new JSONArray(stepsArray);
        Log.e(TAG, "convertStepsArray: " + stepsJsonArray.getString(0) );
        ArrayList<StepsPojo> parsedSteps = new ArrayList<>();
        for (int x = 0; x < stepsArray.size(); x++) {
            String singleStepIndex = stepsJsonArray.getString(x);
            JSONObject singleStep = new JSONObject(singleStepIndex);
            int id = singleStep.getInt("id");
            String stepDescription = singleStep.getString("shortDescription");
            String description = singleStep.getString("description");
            String videoURL = singleStep.getString("videoURL");
            String thumbnail = singleStep.getString("thumbnailURL");

            StepsPojo parsedRecipeSteps = new StepsPojo(id, stepDescription, description, videoURL, thumbnail);
            parsedRecipeSteps.setId(id);
            parsedRecipeSteps.setStepDescription(stepDescription);
            parsedRecipeSteps.setDescription(description);
            parsedRecipeSteps.setVideoURL(videoURL);
            parsedRecipeSteps.setThumbnailURL(thumbnail);
            parsedSteps.add(x, parsedRecipeSteps);
        }
        return parsedSteps;
    }
}
