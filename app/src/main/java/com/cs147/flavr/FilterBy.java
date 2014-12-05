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
import android.widget.TextView;

import net.java.jddac.common.type.ArgMap;

import java.util.ArrayList;
import java.util.List;


public class FilterBy extends Activity {
    public boolean filterCategory;
    public boolean filterDiet;
    public boolean filterLocation;
    public boolean filterExpireTime;
    public static String CATEGORY_SORT = "category";

    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Filters");
    }
    public void sortNormal(View view) {
        Intent sort = new Intent(this, GetFoodList.class);
        startActivity(sort);
    }
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
    public void sortCategory(View view) {
        Intent sort = new Intent(this, FilterCategory.class);
        startActivity(sort);
    }

    public void sortDiet(View view) {
        Intent sort = new Intent(this, FilterDiet.class);
        startActivity(sort);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by);
        createCustomActionBar();
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
