package com.android.bakingfavorites;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecipePojo implements Parcelable {
    private int recipe_id;
    private String recipe_name;
    private List<String> ingredients_list;
    private List<String> recipe_steps;
    private int serving_size;




    public RecipePojo(int recipe_id, String recipe_name, List<String> ingredients_list, List<String> recipe_steps, int serving_size) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.ingredients_list = ingredients_list;
        this.recipe_steps = recipe_steps;
        this.serving_size = serving_size;
    }

    public int getServing_size() {
        return serving_size;
    }

    public void setServing_size(int serving_size) {
        this.serving_size = serving_size;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public List<String> getIngredients_list() {
        return ingredients_list;
    }

    public void setIngredients_list(List<String> ingredients_list) {
        this.ingredients_list = ingredients_list;
    }

    public List<String> getRecipe_steps() {
        return recipe_steps;
    }

    public void setRecipe_steps(List<String> recipe_steps) {
        this.recipe_steps = recipe_steps;
    }


    protected RecipePojo(Parcel in) {
        recipe_id = in.readInt();
        recipe_name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients_list = new ArrayList<String>();
            in.readList(ingredients_list, String.class.getClassLoader());
        } else {
            ingredients_list = null;
        }
        if (in.readByte() == 0x01) {
            recipe_steps = new ArrayList<String>();
            in.readList(recipe_steps, String.class.getClassLoader());
        } else {
            recipe_steps = null;
        }
        serving_size = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(recipe_name);
        if (ingredients_list == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients_list);
        }
        if (recipe_steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(recipe_steps);
        }
        dest.writeInt(serving_size);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RecipePojo> CREATOR = new Parcelable.Creator<RecipePojo>() {
        @Override
        public RecipePojo createFromParcel(Parcel in) {
            return new RecipePojo(in);
        }

        @Override
        public RecipePojo[] newArray(int size) {
            return new RecipePojo[size];
        }
    };
}
