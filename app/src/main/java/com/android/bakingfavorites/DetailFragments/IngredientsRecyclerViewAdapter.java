package com.android.bakingfavorites.DetailFragments;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bakingfavorites.R;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    final String TAG = IngredientsRecyclerViewAdapter.class.getSimpleName();
    private ArrayList<IngredientsPojo> mIngredients = new ArrayList<>();
    private Context mContext;

    public IngredientsRecyclerViewAdapter(ArrayList<IngredientsPojo> ingredients, Context context){
        this.mIngredients = ingredients;
        this.mContext = context;
    }

    //create array of objects.

    @NonNull
    @Override
    public IngredientsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ingredients,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.ingredient.setText(mIngredients.get(i).getMingredient());
        viewHolder.measurement.setText(mIngredients.get(i).getmMeasurement());
        String quantityString = Integer.toString(mIngredients.get(i).getmQuantity());
        viewHolder.quantity.setText(quantityString);

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity;
        TextView measurement;
        TextView ingredient;
        ConstraintLayout list_item_ingredient_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_item_ingredient_layout = itemView.findViewById(R.id.list_item_ingredients_layout);
            quantity = itemView.findViewById(R.id.ingredients_quantity);
            measurement = itemView.findViewById(R.id.ingredients_measurement);
            ingredient = itemView.findViewById(R.id.ingredients_ingredient);


        }
    }
}
