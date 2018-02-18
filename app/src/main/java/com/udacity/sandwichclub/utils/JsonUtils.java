package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJson = new JSONObject(json);

            // Extract name and it's aliases (if any)
            String name = sandwichJson.getJSONObject("name").getString("mainName");
            JSONArray aliasesJson = sandwichJson.getJSONObject("name").getJSONArray("alsoKnownAs");
            List<String> aliases = new ArrayList<String>();
            for (int i=0; i < aliasesJson.length(); i++){
                aliases.add(aliasesJson.getString(i));
            }

            // Place of origin
            String placeOfOrigin = sandwichJson.getString("placeOfOrigin");

            // Description
            String description = sandwichJson.getString("description");

            // Image
            String imageUrl = sandwichJson.getString("image");

            // Ingredients
            JSONArray ingredientsJson = sandwichJson.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for(int i=0; i < ingredientsJson.length(); i++){
                ingredients.add(ingredientsJson.getString(i));
            }

            Sandwich sandwich = new Sandwich(name,aliases,placeOfOrigin,description,
                    imageUrl, ingredients);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
