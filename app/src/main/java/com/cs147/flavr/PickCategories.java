package com.cs147.flavr;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import net.java.jddac.common.type.ArgMap;
import java.util.ArrayList;
import java.util.List;


public class PickCategories extends Activity {
    //List to hold categories associated with an event.
    public static List<String> categories = new ArrayList<String>();

    /* Set action bar font to system standard.
     */
    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Flavr");
    }
    /* Save the user categories for their event.
     */
    public void saveEvent(View view) {
        finish();
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_asian:
                if (checked) categories.add("Asian Cuisine");
                break;
            case R.id.checkbox_baked:
                if (checked) categories.add("Baked Foods");
                break;
            case R.id.checkbox_bbq:
                if (checked) categories.add("BBQ Food");
                break;
            case R.id.checkbox_beverages:
                if (checked) categories.add("Beverages");
                break;
            case R.id.checkbox_coffee:
                if (checked) categories.add("Coffee or Tea");
                break;
            case R.id.checkbox_dessert:
                if (checked) categories.add("Dessert");
                break;
            case R.id.checkbox_doughnut:
                if (checked) categories.add("Doughnuts");
                break;
            case R.id.checkbox_ethnic:
                if (checked) categories.add("Ethnic Food");
                break;
            case R.id.checkbox_fastfood:
                if (checked) categories.add("Fast Food");
                break;
            case R.id.checkbox_fish:
                if (checked) categories.add("Fish or Seafood");
                break;
            case R.id.checkbox_froyo:
                if (checked) categories.add("Frozen Yogurt");
                break;
            case R.id.checkbox_icecream:
                if (checked) categories.add("Ice Cream");
                break;
            case R.id.checkbox_meat:
                if (checked) categories.add("Meat");
                break;
            case R.id.checkbox_mexican:
                if (checked) categories.add("Mexican Food");
                break;
            case R.id.checkbox_pancakes:
                if (checked) categories.add("Pancakes or Waffles");
                break;
            case R.id.checkbox_pizza:
                if (checked) categories.add("Pizza");
                break;
            case R.id.checkbox_protein:
                if (checked) categories.add("Various Protein");
                break;
            case R.id.checkbox_sandwiches:
                if (checked) categories.add("Sandwiches");
                break;
            case R.id.checkbox_snacks:
                if (checked) categories.add("Snacks");
                break;
            case R.id.checkbox_soupsalad:
                if (checked) categories.add("Soup or Salad");
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_categories);
        createCustomActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_categories, menu);
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
