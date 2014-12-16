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

import net.java.jddac.common.type.ArgMap;


public class UserEvents extends Activity {

    /* Set action bar font to system standard.
     */
    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("My Events");
    }

    /* Populate list of all events that user has created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);
        createCustomActionBar();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ListView userEvents = (ListView) findViewById(R.id.user_events);
        if(!MainActivity.userEvents.isEmpty()) {
            EventListAdaptr eventAdapter;
            eventAdapter = new EventListAdaptr(getApplicationContext(), R.layout.event_entry, MainActivity.userEvents);
            userEvents.setAdapter(eventAdapter);
            userEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ArgMap event = (ArgMap) view.getTag();
                    Intent eventInfo = new Intent(getApplicationContext(), EventConfirmation.class);
                    eventInfo.putExtra(event.getString(GetFoodList.EXTRA), i);
                    startActivity(eventInfo);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_events, menu);
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
