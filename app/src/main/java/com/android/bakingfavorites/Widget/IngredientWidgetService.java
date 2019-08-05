package com.android.bakingfavorites.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.bakingfavorites.DetailFragments.IngredientsPojo;
import com.android.bakingfavorites.JsonUtils.JsonParsingUtil;
import com.android.bakingfavorites.R;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IngredientWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetItemFactory(getApplicationContext(), intent);
    }

    class IngredientWidgetItemFactory implements RemoteViewsFactory {
        private final String TAG = IngredientWidgetService.class.getSimpleName();
        private Context context;
        private int widgetId;
        private ArrayList<String> ingredients_list = new ArrayList<String>();

        IngredientWidgetItemFactory (Context context, Intent intent) {
            this.context = context;
            this.widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences getData = getSharedPreferences("ingredients_list", Context.MODE_PRIVATE);
            ingredients_list.clear();
            Set<String> defaultSet = new HashSet<String>();
            HashSet<String> names = new HashSet<String>();
            names = (HashSet<String>) getData.getStringSet("ingredients_string", defaultSet);

            ArrayList<String> namesSet = new ArrayList<>(names);

            try {
                ArrayList<IngredientsPojo> recipeIngredients = JsonParsingUtil.convertIngredientsArray(namesSet);
                for (int x = 0; x < recipeIngredients.size(); x ++) {
                    //creating formatted String for stack view.
                    String recipe_ingredient = recipeIngredients.get(x).getMingredient();
                    String recipe_measurement = recipeIngredients.get(x).getmMeasurement();
                    String recipe_quantity = Integer.toString(recipeIngredients.get(x).getmQuantity());
                    String full_ingredient = "Ingredient\n" + recipe_ingredient + "\n" + "measurement: "
                            + recipe_measurement + "quantity: " + recipe_quantity;
                    ingredients_list.add(full_ingredient);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return ingredients_list.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            SharedPreferences getData = getSharedPreferences("ingredients_list", Context.MODE_PRIVATE);
            Set<String> defaultSet = new HashSet<String>();
            defaultSet.add("coot");
            HashSet<String> names = new HashSet<String>();
            names = (HashSet<String>) getData.getStringSet("ingredients_string", defaultSet);
            ArrayList<String> namesSet = new ArrayList<>(names);
            ingredients_list.clear();



            names = (HashSet<String>) getData.getStringSet("ingredients_string", defaultSet);

            //parsing string to create formatted ingredients for stack view.
            try {
                ArrayList<IngredientsPojo> recipeIngredients = JsonParsingUtil.convertIngredientsArray(namesSet);
                for (int x = 0; x < recipeIngredients.size(); x ++) {
                    //creating formatted String for stack view.
                    String recipe_ingredient = recipeIngredients.get(x).getMingredient();
                    String recipe_measurement = recipeIngredients.get(x).getmMeasurement();
                    String recipe_quantity = Integer.toString(recipeIngredients.get(x).getmQuantity());
                    String full_ingredient = "Ingredient\n" + recipe_ingredient + "\n" + "measurement: "
                            + recipe_measurement + "\n" + "quantity: " + recipe_quantity;
                    ingredients_list.add(full_ingredient);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


            //ingredients_list = namesSet;

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list_item);
            views.setTextViewText(R.id.widget_ingredient_list_item_text, ingredients_list.get(position));
            Log.e(TAG, "getViewAt: " + ingredients_list.get(position) );
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
