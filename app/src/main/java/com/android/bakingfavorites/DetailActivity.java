package com.android.bakingfavorites;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.bakingfavorites.DetailFragments.IngredientsFragment;
import com.android.bakingfavorites.DetailFragments.IngredientsPojo;
import com.android.bakingfavorites.DetailFragments.StepsFragment;
import com.android.bakingfavorites.DetailFragments.StepsPojo;
import com.android.bakingfavorites.JsonUtils.JsonParsingUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    ArrayList<IngredientsPojo> recipieIngredients;
    ArrayList<StepsPojo> recipeSteps;
    ArrayList<String> recipe_ingredients_list;
    final String TAG = DetailActivity.class.getSimpleName();
    private boolean TabletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //check for tablet layout
        if (findViewById(R.id.main_tablet_layout) != null) {
            TabletLayout = true;
        } else {
            TabletLayout = false;
        }

        //getting the recipe from RecyclerViews onBind method.
        Intent receivedRecipe = getIntent();
        RecipePojo displayedRecipe = receivedRecipe.getParcelableExtra("single_recipe");
        //temporary to test before putting in ingredients fragment
        ArrayList<String> ingredients_list =  (ArrayList<String>) displayedRecipe.getIngredients_list();
        recipe_ingredients_list = ingredients_list;


        ArrayList<String> steps_list = (ArrayList<String>) displayedRecipe.getRecipe_steps();
        try {
            recipeSteps = JsonParsingUtil.convertStepsArray(steps_list);
            Log.e(TAG, "onCreate: " + recipeSteps.get(0).getDescription() );
            recipieIngredients = JsonParsingUtil.convertIngredientsArray(ingredients_list);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //widget prefs save
        savedIngredientList();

        //widget
        Intent intent = new Intent(this, IngredientsWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        StepsFragment stepsFragment = new StepsFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelableArrayList("stepsKey", recipeSteps);
        stepsFragment.setArguments(stepsBundle);

        //sending parsed ArrayList recipeIngredients to ingredientsFragment.
        Bundle ingredientsBundle = new Bundle();
        ingredientsBundle.putParcelableArrayList("ingredients", recipieIngredients);
        ingredientsFragment.setArguments(ingredientsBundle);


        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.steps_container, stepsFragment)
                .commit();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container, ingredientsFragment)
                .commit();
        Log.e(TAG, "onCreate: " + "fragmentManager finished" );

    }

    public void savedIngredientList(){
        Set<String> ingredientsSet = new HashSet<String>();
        ingredientsSet.addAll(recipe_ingredients_list);
        SharedPreferences Data = getSharedPreferences("ingredients_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Data.edit();
        editor.putStringSet("ingredients_string", ingredientsSet);
        editor.apply();
    }

    public void getIngredients(View view){
        SharedPreferences getData = getSharedPreferences("ingredients_list", Context.MODE_PRIVATE);
        String name = getData.getString("ingredients_string", "");
    }
}
