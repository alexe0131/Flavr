package com.cs147.flavr;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.java.jddac.common.type.ArgMap;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import net.java.jddac.util.StringUtil;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public class GetFoodList extends Activity {
    public static final String FOOD = "Food";
    public static final String EVENT = "Event";
    public static final String DESCRIPTION = "Description";
    public static final String LOCATION = "Location";
    public static final String START_TIME = "Start Time";
    public static final String END_TIME = "End Time";
    public static final String TAGS = "Tags";
    public static final String CAPACITY = "Capacity";
    public static final String IMAGE = "Image";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_food_list);
        int showNEvents = 15;
        ListView eventPage = (ListView) findViewById(R.id.eventpage);
        EventListAdaptr eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.events);
        eventPage.setAdapter(eventAdapter);
//            LinearLayout newLinLayout = new LinearLayout(this);
//            int height = 150;
//            newLinLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 150));
//            newLinLayout.setOrientation(LinearLayout.HORIZONTAL);
//            TextView food = new TextView(this);
//            food.setText(currEvent.getString(FOOD));
//            TextView event = new TextView(this);
//            food.setText(currEvent.getString(EVENT));
//            TextView location = new TextView(this);
//            food.setText(currEvent.getString(LOCATION));
//            newLinLayout.addView(food);
//            newLinLayout.addView(event);
//            newLinLayout.addView(location);
//            eventPage.addView(newLinLayout);
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_food_list, menu);
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
