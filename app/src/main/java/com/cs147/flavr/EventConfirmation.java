//NOTICE: THIS CODE CONTAINS MATERIAL THAT IS FREELY DISTRIBUTED BY GOOGLE.INC
package com.cs147.flavr;

import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import java.lang.String;
import java.util.Calendar;
import com.google.android.gms.maps.model.LatLng;
import android.location.Geocoder;
import android.widget.ImageView;
import java.util.List;
import java.io.IOException;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.java.jddac.common.type.ArgMap;

public class EventConfirmation extends FragmentActivity{
    public final static String EVENT_STRINGS = "event strings";

    public final static String EVENT_TIMES = "event times";

    private void userEvents() {
        Intent events = new Intent(this, UserEvents.class);
        startActivity(events);
    }

    /* Looks up the address that the user enters and finds the latitude and longitude of that
    * location to be looked up on a map.
    */
    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng loc = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) return null;
            if(!address.isEmpty()) {
                Address location = address.get(0);
                if (location != null)
                    loc = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return loc;
    }

    /* Iterates through the array of event info and prints it out on the confirmation screen.
    */
    public void printEventInfo(ArgMap event) {
            TextView eventTitle = (TextView) findViewById(R.id.confirmation_food);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/alegreyasanssc_regular.ttf");
        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts/opensans_regular.ttf");
        eventTitle.setTypeface(font);
            eventTitle.setText(event.getString(GetFoodList.FOOD));
            TextView food = (TextView) findViewById(R.id.confirmation_event);
            food.setTypeface(font);
            food.setText(event.getString(GetFoodList.EVENT));
            TextView description = (TextView) findViewById(R.id.confirmation_description);
         description.setTypeface(openSans);
            description.setText(event.getString(GetFoodList.DESCRIPTION));
            TextView location = (TextView) findViewById(R.id.confirmation_location);
            location.setTypeface(font);
            location.setText(event.getString(GetFoodList.LOCATION));
            TextView attendance = (TextView) findViewById(R.id.confirmation_attendance);
            attendance.setTypeface(openSans);
        SpannableStringBuilder sb = new SpannableStringBuilder("You currently have "+event.getString(GetFoodList.ATTENDANCE)+" attending.");
        StyleSpan b = new StyleSpan(Typeface.BOLD);
        sb.setSpan(b, 19, 19+event.getString(GetFoodList.ATTENDANCE).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            attendance.setText(sb);
        TextView distance = (TextView) findViewById(R.id.confirmation_distance);
        distance.setTypeface(font);
        distance.setText(event.getString(GetFoodList.DISTANCE) + " miles away");
            TextView capacity = (TextView) findViewById(R.id.confirmation_capacity);
            capacity.setText(event.getString(GetFoodList.CAPACITY));
            ImageView eventImage = (ImageView) findViewById(R.id.event_image);
            eventImage.setImageBitmap(createEvent.yourSelectedImage);
    }
    /* Retrieves the times that the user entered on the previous screen and uses these to
    * calculate how long until the start and end times the user is.
    */
    private void printEventExpiry(ArgMap event) {
        Calendar c = Calendar.getInstance();
        int sysHour = c.get(Calendar.HOUR_OF_DAY);
        int sysMin = c.get(Calendar.MINUTE);
//        int startHour = times[0];
//        int startMin = times[1];
        int endHour = Integer.parseInt(event.getString(GetFoodList.END_HOUR));
        int endMin = Integer.parseInt(event.getString(GetFoodList.END_MIN));
//        TextView startText = (TextView) findViewById(R.id.confirmation_start_time);
//        if (startHour > sysHour) {
//            int hourDiff = startHour - sysHour;
//            int minDiff = startMin - sysMin;
//            if(sysMin > startMin) {
//                hourDiff--;
//                minDiff += 60;
//            }
//            startText.setText("Your event starts in " + Integer.toString(hourDiff) + " hours and "+ Integer.toString(minDiff)+" minutes.");
//        }
//        if(startHour == sysHour && startMin >= sysMin) {
//            int minDiff = startMin - sysMin;
//            startText.setText("Your event starts in "+ Integer.toString(minDiff)+" minutes.");
//        }
        Typeface openSans = Typeface.createFromAsset(getAssets(), "fonts/opensans_regular.ttf");

        TextView endText = (TextView) findViewById(R.id.confirmation_end_time);
        endText.setTypeface(openSans);
        if (endHour > sysHour) {
            int hourDiff = endHour - sysHour;
            int minDiff = endMin - sysMin;
            if(sysMin > endMin) {
                hourDiff--;
                minDiff += 60;
            }
            String plural = " hours.";
            if(hourDiff == 1) plural = " hour.";
            endText.setText("Expires in: " + Integer.toString(hourDiff) + plural);
        }
        if(endHour == sysHour && endMin >= sysMin) {
            int minDiff = endMin - sysMin;
            endText.setText("Expires in: "+ Integer.toString(minDiff)+" minutes.");
        }

    }

    private void editEvent() {
        Intent edit = new Intent(this, EditEvent.class);
        startActivity(edit);
    }

    /* Retrieves bundled information from create event activity, updates the map location
    * accordingly, and calls the methods to print the event information and times
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent eventInfo = getIntent();
        setContentView(R.layout.activity_event_confirmation);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ArgMap event = MainActivity.events.get(0);
        LatLng location = getLocationFromAddress(event.getString(GetFoodList.LOCATION));
        if(location != null) {
            GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            Marker place = gMap.addMarker(new MarkerOptions()
                    .position(location));
        }
        printEventInfo(event);
        printEventExpiry(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_confirmation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_event:
                editEvent();
                return true;
            case R.id.myevents2:
                userEvents();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
