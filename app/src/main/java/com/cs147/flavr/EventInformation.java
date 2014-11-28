package com.cs147.flavr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.java.jddac.common.type.ArgMap;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class EventInformation extends Activity {

    private void fillData(ArgMap event) {
        TextView eventTitle = (TextView) findViewById(R.id.get_event_food);
        eventTitle.setText(event.getString(GetFoodList.FOOD));
        TextView food = (TextView) findViewById(R.id.get_event_event);
        food.setText(event.getString(GetFoodList.EVENT));
        TextView description = (TextView) findViewById(R.id.get_event_description);
        description.setText(event.getString(GetFoodList.DESCRIPTION));
        TextView location = (TextView) findViewById(R.id.get_event_location);
        location.setText(event.getString(GetFoodList.LOCATION));
        TextView tags = (TextView) findViewById(R.id.get_event_tags);
        tags.setText(event.getString("Tags: "+GetFoodList.TAGS));
        TextView capacity = (TextView) findViewById(R.id.get_event_capacity);
        capacity.setText(event.getString("Capacity: "+GetFoodList.CAPACITY));
        ImageView eventImage = (ImageView) findViewById(R.id.get_event_image);
        eventImage.setImageBitmap((Bitmap) event.get(GetFoodList.IMAGE, EventListAdaptr.defaultPicture));
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng loc = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) return null;
            Address location = address.get(0);
            if (location != null)
                loc = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return loc;
    }
    private void printEventExpiry(int[] times) {
        Calendar c = Calendar.getInstance();
        int sysHour = c.get(Calendar.HOUR_OF_DAY);
        int sysMin = c.get(Calendar.MINUTE);
        int startHour = times[0];
        int startMin = times[1];
        int endHour = times[2];
        int endMin = times[3];
        TextView startText = (TextView) findViewById(R.id.confirmation_start_time);
        if (startHour > sysHour) {
            int hourDiff = startHour - sysHour;
            int minDiff = startMin - sysMin;
            if(sysMin > startMin) {
                hourDiff--;
                minDiff += 60;
            }
            startText.setText("Your event starts in " + Integer.toString(hourDiff) + " hours and "+ Integer.toString(minDiff)+" minutes.");
        }
        if(startHour == sysHour && startMin >= sysMin) {
            int minDiff = startMin - sysMin;
            startText.setText("Your event starts in "+ Integer.toString(minDiff)+" minutes.");
        }
        TextView endText = (TextView) findViewById(R.id.confirmation_end_time);
        if (endHour > sysHour) {
            int hourDiff = endHour - sysHour;
            int minDiff = endMin - sysMin;
            if(sysMin > endMin) {
                hourDiff--;
                minDiff += 60;
            }
            endText.setText("Your event ends in " + Integer.toString(hourDiff) + " hours and "+ Integer.toString(minDiff)+" minutes.");
        }
        if(endHour == sysHour && endMin >= sysMin) {
            int minDiff = endMin - sysMin;
            endText.setText("Your event ends in "+ Integer.toString(minDiff)+" minutes.");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);
        Intent eventInfo = getIntent();
        int listPosition = 0;
        listPosition = eventInfo.getIntExtra(GetFoodList.EXTRA, listPosition);
        ArgMap event = MainActivity.events.get(listPosition);
        fillData(event);
        LatLng location = getLocationFromAddress(event.getString(GetFoodList.LOCATION));
        GoogleMap gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.get_map)).getMap();
        gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        Marker place = gMap.addMarker(new MarkerOptions()
                .position(location));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_information, menu);
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
