package com.android.bakingfavorites.DetailFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.bakingfavorites.R;
import java.util.ArrayList;

public class IngredientsFragment extends Fragment {
    final String TAG = IngredientsFragment.class.getSimpleName();

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        Bundle bundle = getArguments();
        ArrayList<IngredientsPojo> ingredientsList =  bundle.getParcelableArrayList("ingredients");
        if (ingredientsList != null) {
            Toast.makeText(container.getContext(),ingredientsList.get(0).getmMeasurement(), Toast.LENGTH_LONG ).show();
            Log.e(TAG, "onCreateView: in" + ingredientsList.get(0).getMingredient() );
        }
        else {
            Toast.makeText(container.getContext(), "buggg", Toast.LENGTH_LONG).show();
        }


        RecyclerView ingredientsRecyclerView =  rootView.findViewById(R.id.ingredients_recycler_view);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        IngredientsRecyclerViewAdapter recyclerViewAdapter = new IngredientsRecyclerViewAdapter(ingredientsList, getContext());
        ingredientsRecyclerView.setAdapter(recyclerViewAdapter);
        return rootView;
    }


}
