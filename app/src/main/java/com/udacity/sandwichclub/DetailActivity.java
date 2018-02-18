package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public TextView aliasTextView;
    public TextView originTextView;
    public TextView ingredientsTextView;
    public TextView descriptionTextView;

    public TextView labelAlias;
    public TextView labelOrigin;
    public TextView labelIngredients;
    public TextView labelDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        aliasTextView = findViewById(R.id.also_known_tv);
        originTextView = findViewById(R.id.origin_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        labelAlias = findViewById(R.id.alias_label);
        labelOrigin = findViewById(R.id.origin_label);
        labelIngredients = findViewById(R.id.ingredients_label);
        labelDescription = findViewById(R.id.description_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String aliases = listToString(sandwich.getAlsoKnownAs());
        if(!aliases.isEmpty()){
            aliasTextView.setText(aliases);
        }else {
            labelAlias.setVisibility(View.GONE);
            aliasTextView.setVisibility(View.GONE);
        }
        String origin = sandwich.getPlaceOfOrigin();
        if(!origin.isEmpty()){
            originTextView.setText(origin);
        }else {
            labelOrigin.setVisibility(View.GONE);
            originTextView.setVisibility(View.GONE);
        }
        String ingredients = listToString(sandwich.getIngredients());
        if(!ingredients.isEmpty()){
            ingredientsTextView.setText(ingredients);
        }else {
            labelIngredients.setVisibility(View.GONE);
            ingredientsTextView.setVisibility(View.GONE);
        }
        String description = sandwich.getDescription();
        if(!description.isEmpty()) {
            descriptionTextView.setText(description);
        }else {
            labelDescription.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        }
    }

    // Utility function to turn aliases and ingredient ArrayLists into strings
    private String listToString(List<String> list){
        /*
        StringBuilder sb = new StringBuilder();
        for (String s : list)
        {
            sb.append(s);
            sb.append(", ");
        }
        return sb.toString(); **/
        return TextUtils.join(", ", list); // Android saves the day again
    }
}
