package com.cs147.flavr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.lang.String;
import java.util.Calendar;


public class createEvent extends Activity {
    public final static String EVENT_STRINGS = "temp";
    public final static String EVENT_TIMES = "tempTimes";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
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
    //Extracts string from an edit text field where the user inputs data.
    public String extractStringFromID(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }
    //Reads all the strings from the event listing and puts them into an array to be passed into
    //the next activity of confirming the event.
    public void submitEvent(View view) {
        Intent submit = new Intent(this, EventConfirmation.class);
        Bundle event = new Bundle();
        String [] eventInformation = new String[6];
        String foodType = extractStringFromID(R.id.food_type);
        eventInformation[0]= foodType;
        String eventTitle = extractStringFromID(R.id.event_title);
        eventInformation[1]= eventTitle;
        String description = extractStringFromID(R.id.description);
        eventInformation[2]= description;
        String location = extractStringFromID(R.id.location);
        eventInformation[3] = location;
        String tags = extractStringFromID(R.id.tags);
        eventInformation[4] = tags;
        String capacity = extractStringFromID(R.id.capacity);
        eventInformation[5] = capacity;
        int[] times = new int [4];
        TimePicker startTimer = (TimePicker) findViewById(R.id.start_time);

        int startHour = startTimer.getCurrentHour();
        int startMinute = startTimer.getCurrentMinute();
        times[0] = startHour;
        times[1] = startMinute;
        TimePicker endTimer = (TimePicker) findViewById(R.id.end_time);
        endTimer.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        int endHour = endTimer.getCurrentHour();
        int endMinute = endTimer.getCurrentMinute();
        times[2] = endHour;
        times[3] = endMinute;
        event.putIntArray(EVENT_TIMES, times);
        event.putStringArray(EVENT_STRINGS, eventInformation);
        submit.putExtras(event);
        startActivity(submit);
    }

}
