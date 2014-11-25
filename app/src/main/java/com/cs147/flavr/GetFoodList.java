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
    /* These variables are the string names of the ArgMaps holding our hard-coded information.
     */
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
    /* Creates an eventlistadapter to only load the linear layouts of the events being shown
     * in the listview on screen at the current time.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_food_list);
        ListView eventPage = (ListView) findViewById(R.id.eventpage);
        EventListAdaptr eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.events);
        eventPage.setAdapter(eventAdapter);
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
