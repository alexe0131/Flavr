package com.cs147.flavr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class FilterDiet extends Activity {
    public static String DIET = "chosen category";

    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Filter By Diet");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_category);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        createCustomActionBar();
        ListView category = (ListView) findViewById(R.id.categorylist);
        CategoryListAdapter catAdapter = new CategoryListAdapter(getApplicationContext(), R.layout.categorybutton, MainActivity.dietPrefs);
        category.setAdapter(catAdapter);
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category = (String) view.getTag();
                Intent categoryFood = new Intent(getApplicationContext(), GetFoodList.class);
                categoryFood.putExtra(DIET, category);
                startActivity(categoryFood);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_category, menu);
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
