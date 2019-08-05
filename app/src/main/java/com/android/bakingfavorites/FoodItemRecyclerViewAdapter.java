package com.android.bakingfavorites;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodItemRecyclerViewAdapter extends RecyclerView.Adapter<FoodItemRecyclerViewAdapter.ViewHolder> {

    final String TAG = FoodItemRecyclerViewAdapter.class.getSimpleName();

    //creating array list of RecipePojo's.
    private ArrayList<RecipePojo> mRecipeObjects = new ArrayList<>();
    private Context mContext;

    public FoodItemRecyclerViewAdapter(ArrayList<RecipePojo> mRecipeObjects, Context mContext) {
        this.mRecipeObjects = mRecipeObjects;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FoodItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recipe,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder was called");
        //will need to set the onclicklistener here...
        viewHolder.textView.setText(mRecipeObjects.get(i).getRecipe_name());
        viewHolder.servings_text_view.setText(String.valueOf(mRecipeObjects.get(i).getServing_size()));
        viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorRecipeButton));
        viewHolder.list_item_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selected_recipe = new Intent(v.getContext(), DetailActivity.class);
                RecipePojo single_recipe = mRecipeObjects.get(i);
                selected_recipe.putExtra("single_recipe", single_recipe);
                mContext.startActivity(selected_recipe);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipeObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView servings_text_view;
        RelativeLayout list_item_parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recycler_recipe_text);
            servings_text_view = itemView.findViewById(R.id.serving_size_text_view);
            list_item_parent_layout = itemView.findViewById(R.id.recipe_list_item_layout);

        }
    }
}
