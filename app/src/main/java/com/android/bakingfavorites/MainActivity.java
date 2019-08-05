package com.android.bakingfavorites;

import android.os.AsyncTask;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.bakingfavorites.JsonUtils.JsonParsingUtil;
import com.android.bakingfavorites.Network.NetworkUtilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    FoodItemRecyclerViewAdapter mainAdapter;
    private ArrayList<RecipePojo> mRecipePojos = new ArrayList<>();
    @Nullable
    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecipeRecyclerView;

    //boolean to check if the layout is tablet or not.
    private boolean TabletLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (findViewById(R.id.main_tablet_layout) != null) {
            TabletLayout = true;
        } else {
            TabletLayout = false;
        }

        //AsyncTask call to fetch json and parse.
        try {
            new fetchAndParseJson().execute(new URL(NetworkUtilities.recipeJsonUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        //setting up recepies
        initializeRecyclerView();


    }

    private void initializeRecyclerView() {
        FoodItemRecyclerViewAdapter recyclerViewAdapter = new FoodItemRecyclerViewAdapter(mRecipePojos, this);
        this.mainAdapter = recyclerViewAdapter;
        mainRecipeRecyclerView.setAdapter(mainAdapter);
        if (TabletLayout == false) {
            mainRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        } else {
            mainRecipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2 ));
        }

        }

        public class fetchAndParseJson extends AsyncTask<URL, Void, String> {

            @Override
            protected String doInBackground(URL... urls) {
                URL recipeUrl = urls[0];
                String recipeJsonString = null;
                try {
                    recipeJsonString = NetworkUtilities.getResponseFromUrl(recipeUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return recipeJsonString;
            }


            @Override
            protected void onPostExecute(String recipeJson) {
                if (recipeJson != null && !recipeJson.equals("")) {
                    try {
                        JSONArray recipeArray = new JSONArray(recipeJson);
                        for (int x = 0; x < recipeArray.length(); x++) {
                            RecipePojo aRecipe = JsonParsingUtil.parseRecipeJson(recipeArray, x);
                            mRecipePojos.add(aRecipe);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "onPostExecute: " + mRecipePojos.get(1).getRecipe_name());

                    //after recipies are parsed, the recyclerView adapter is reset.Changes will be made to the UI.
                    mainAdapter.notifyDataSetChanged();


                }
            }


        }


    }
