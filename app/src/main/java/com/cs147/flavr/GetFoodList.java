package com.cs147.flavr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import net.java.jddac.common.type.ArgMap;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import net.java.jddac.util.StringUtil;

import android.view.View;
import android.widget.AdapterView;
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
    public static final String CAPACITY = "Capacity";
    public static final String IMAGE = "Image";
    public static final String EXTRA = "extra";
    public static final String ATTENDANCE = "Attendance";
    public static final String DISTANCE = "Distance";


    private void filter() {
        Intent filters = new Intent(this, FilterBy.class);
        startActivity(filters);
    }

    private void notifications() {
        Intent notify = new Intent(this, FlavrNotifications.class);
        startActivity(notify);
    }

    @Override
    /* Creates an eventlistadapter to only load the linear layouts of the events being shown
     * in the listview on screen at the current time.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_food_list);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ListView eventPage = (ListView) findViewById(R.id.eventpage);
        Intent intent = getIntent();
        String category = intent.getStringExtra(FilterCategory.CATEGORY);
        String diet = intent.getStringExtra(FilterDiet.DIET);
        EventListAdaptr eventAdapter;
        if(category != null && MainActivity.categories != null) eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.categories.get(category));
        else if(diet != null && MainActivity.dietChoices != null) eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.dietChoices.get(diet));
        else eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.events);
        eventPage.setAdapter(eventAdapter);
        eventPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArgMap event = (ArgMap) view.getTag();
                Intent eventInfo = new Intent(getApplicationContext(), EventInformation.class);
                eventInfo.putExtra(EXTRA, i);
                startActivity(eventInfo);
            }
        });
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

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.filters:
                filter();
                return true;
            case R.id.notifications:
                notifications();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
