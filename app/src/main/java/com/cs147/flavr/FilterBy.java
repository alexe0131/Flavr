package com.cs147.flavr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.java.jddac.common.type.ArgMap;

import java.util.ArrayList;
import java.util.List;


public class FilterBy extends Activity {

    /* Set action bar font to match system standard.
     */
    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Filters");
    }

    /* Sort the list according to the time the event was posted.
     */
    public void sortNormal(View view) {
        Intent sort = new Intent(this, GetFoodList.class);
        startActivity(sort);
    }

    /* Sort event based on time that the events will expire/
     */
    public void sortExpiration(View view) {
        List<ArgMap> arr = MainActivity.events;
        for (int i = 0; i < arr.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.size(); j++)
                if (Integer.parseInt(arr.get(j).getString(GetFoodList.END_HOUR)) < Integer.parseInt(arr.get(index).getString(GetFoodList.END_HOUR))) index = j;

            ArgMap smallerNumber = arr.get(index);
            arr.set(index,arr.get(i));
            arr.set(i, smallerNumber);
        }
        MainActivity.events = arr;
        Intent sort = new Intent(this, GetFoodList.class);
        startActivity(sort);
    }

    /* Sort the maps by distance from user.
     */
    public void sortDistance(View view) {
        List<ArgMap> arr = MainActivity.events;
        for (int i = 0; i < arr.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.size(); j++)
                if (Double.parseDouble(arr.get(j).getString(GetFoodList.DISTANCE)) < Double.parseDouble(arr.get(index).getString(GetFoodList.DISTANCE))) index = j;

            ArgMap smallerNumber = arr.get(index);
            arr.set(index,arr.get(i));
            arr.set(i, smallerNumber);
        }
        MainActivity.events = arr;
        Intent sort = new Intent(this, GetFoodList.class);
        startActivity(sort);
    }

    /* Show only events from a certain category.
     */
    public void sortCategory(View view) {
        Intent sort = new Intent(this, FilterCategory.class);
        startActivity(sort);
    }

    /* Show only events matching a certain dietary preference.
     */
    public void sortDiet(View view) {
        Intent sort = new Intent(this, FilterDiet.class);
        startActivity(sort);
    }
    /* Show the buttons that show the different sorting methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);
        createCustomActionBar();
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        Button category = (Button) findViewById(R.id.filter_category);
        category.setTypeface(alegreya);
        Button time = (Button) findViewById(R.id.filter_time);
        time.setTypeface(alegreya);
        Button diet = (Button) findViewById(R.id.filter_diet);
        diet.setTypeface(alegreya);
        Button loc = (Button) findViewById(R.id.filter_location);
        loc.setTypeface(alegreya);
        Button expire = (Button) findViewById(R.id.filter_expiretime);
        expire.setTypeface(alegreya);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_by, menu);
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
