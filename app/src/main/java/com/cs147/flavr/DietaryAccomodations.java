package com.cs147.flavr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DietaryAccomodations extends Activity {
    public static List<String> diet = new ArrayList<String>();

    public void saveEvent(View view) {
        finish();
    }

    /* Set action bar font to match system standard.
     */
    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Dietary Accommodations");
    }

    /* Add the dietary options selected by the user to the list to be used for sorting.
     */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_kosher:
                if (checked) diet.add("Kosher");
                break;
            case R.id.checkbox_vegetarian:
                if (checked) diet.add("Vegetarian");
                break;
            case R.id.checkbox_vegan:
                if (checked) diet.add("Vegan");
                break;
            case R.id.checkbox_gluten:
                if (checked) diet.add("Gluten-free");
                break;
            case R.id.checkbox_peanut:
                if (checked) diet.add("Peanut Allergy");
                break;
            case R.id.checkbox_lactose:
                if (checked) diet.add("Lactose Intolerance");
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietary_accomodations);
        createCustomActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dietary_accomodations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
