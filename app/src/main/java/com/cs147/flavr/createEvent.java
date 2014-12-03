package com.cs147.flavr;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import java.io.FileNotFoundException;
import java.lang.String;
import android.widget.Toast;
import android.net.Uri;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.java.jddac.common.type.ArgMap;


public class createEvent extends Activity {

    public List<ArgMap> myEvents;
    //Temporary array for event string information.
    public final static String EVENT_STRINGS = "temp";
    //Temporary array for event time information.
    public final static String EVENT_TIMES = "tempTimes";
    //Temp integer to hold image later.
    public final static int SELECT_PHOTO = 100;

    public static Bitmap yourSelectedImage;
    public static Uri selectedImage;
    static final String STATE_FOOD = "saveFood";
    static final String STATE_EVENT = "saveEvent";
    static final String STATE_DESCRIPTION = "saveDescription";
    static final String STATE_LOCATION = "saveLocation";
    static final String STATE_CAPACITY = "saveCapacity";

    /*Extracts string from an edit text field where the user inputs data.
    */
    public String extractStringFromID(int id) {
        EditText editText = (EditText) findViewById(id);
        return editText.getText().toString();
    }

    private void giveToastError(String error) {
        if(error == "foodType")  Toast.makeText(getApplicationContext(), "Please enter a valid food type.", Toast.LENGTH_LONG).show();
        else if(error == "eventTitle")  Toast.makeText(getApplicationContext(), "Please enter a valid event title.", Toast.LENGTH_LONG).show();
        else if(error == "location")  Toast.makeText(getApplicationContext(), "Please enter a valid location.", Toast.LENGTH_LONG).show();
    }

    public void uploadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }

    private int[] parseTimes() {
        int[] times = new int[4];
        TimePicker startTimer = (TimePicker) findViewById(R.id.start_time);
        int startHour = startTimer.getCurrentHour();
        int startMinute = startTimer.getCurrentMinute();
        times[0] = startHour;
        times[1] = startMinute;
        TimePicker endTimer = (TimePicker) findViewById(R.id.end_time);
        int endHour = endTimer.getCurrentHour();
        int endMinute = endTimer.getCurrentMinute();
        times[2] = endHour;
        times[3] = endMinute;
        return times;
    }

    public void dietaryAccomodations(View view) {
        Intent diet = new Intent(this, DietaryAccomodations.class);
        startActivity(diet);
    }
    public void pickCategories(View view) {
        Intent categories = new Intent(this, PickCategories.class);
        startActivity(categories);
    }
    public void determineDiet(ArgMap event) {
        for(String string : MainActivity.dietPrefs) {
            if(DietaryAccomodations.diet.indexOf(string) != -1) {
                if(MainActivity.dietChoices != null && MainActivity.dietChoices.get(string) != null) {
                    List<ArgMap> diet = MainActivity.dietChoices.get(string);
                    diet.add(event);
                    MainActivity.categories.put(string, diet);
                }
                else if(MainActivity.dietChoices != null){
                    List<ArgMap> diet = new ArrayList<ArgMap>();
                    diet.add(event);
                    MainActivity.categories.put(string, diet);
                }
            }
        }
    }
    public void determineCategories(ArgMap event) {
        for (String string : MainActivity.allCategories) {
            if (PickCategories.categories.indexOf(string) != -1) {
                if (MainActivity.categories != null && MainActivity.categories.get(string) != null) {
                    List<ArgMap> category = MainActivity.categories.get(string);
                    category.add(event);
                    MainActivity.categories.put(string, category);
                }
                else if(MainActivity.categories != null){
                    List<ArgMap> category = new ArrayList<ArgMap>();
                    category.add(event);
                    MainActivity.categories.put(string, category);
                }
            }
        }
    }

    public void notifications(ArgMap event) {
        if(FlavrNotifications.notify == true) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.flavrlogo)
                    .setContentTitle("Flavr")
                    .setContentText("Flavr has found an event matching your specifications.");
            Intent resultIntent = new Intent(this, EventInformation.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(EventInformation.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(123, mBuilder.build());
        }
    }
    /*Upon the clicking of the submit button, reads all the strings and times from the event listing and puts them into an respective arrays to be passed into
    *the next activity of confirming the event. Bundles these into an extra for the intent
    * and goes to event confirmation.
    */
    public void submitEvent(View view) {
        String foodType = extractStringFromID(R.id.food_type);
        String eventTitle = extractStringFromID(R.id.event_title);
        String description = extractStringFromID(R.id.description);
        String location = extractStringFromID(R.id.location);
        String capacity = extractStringFromID(R.id.capacity);
        if (foodType.length() == 0) giveToastError("foodType");
        else if (eventTitle.length() == 0) giveToastError("eventTitle");
        else if (location.length() == 0) giveToastError("location");
        else {

            int [] timeInfo = parseTimes();
            ArgMap newEvent = new ArgMap();
            newEvent.put(GetFoodList.FOOD, foodType);
            newEvent.put(GetFoodList.EVENT, eventTitle);
            newEvent.put(GetFoodList.LOCATION, location);
            newEvent.put(GetFoodList.DESCRIPTION, description);
            newEvent.put(GetFoodList.CAPACITY, capacity);
            newEvent.put(GetFoodList.ATTENDANCE, 0);
            newEvent.put(GetFoodList.IMAGE, yourSelectedImage);
            MainActivity.events.add(0, newEvent);
            determineCategories(newEvent);
            determineDiet(newEvent);
            Intent submit = new Intent(this, EventConfirmation.class);
            Bundle event = new Bundle();
            event.putIntArray(EVENT_TIMES, timeInfo);
            submit.putExtras(event);
            notifications(newEvent);
            startActivity(submit);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            EditText food = (EditText) findViewById(R.id.food_type);
            EditText event = (EditText) findViewById(R.id.event_title);
            EditText description = (EditText) findViewById(R.id.description);
            EditText location = (EditText) findViewById(R.id.location);
            EditText capacity = (EditText) findViewById(R.id.capacity);
            food.setText(savedInstanceState.getString(STATE_FOOD));
            event.setText(savedInstanceState.getString(STATE_EVENT));
            description.setText(savedInstanceState.getString(STATE_DESCRIPTION));
            location.setText(savedInstanceState.getString(STATE_LOCATION));
            capacity.setText(savedInstanceState.getString(STATE_CAPACITY));

        }
        setContentView(R.layout.activity_create_event);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
    @Override
    /* Gets image from user for event and converts to Bitmap
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    try {
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        EditText food = (EditText) findViewById(R.id.food_type);
        EditText event = (EditText) findViewById(R.id.event_title);
        EditText description = (EditText) findViewById(R.id.description);
        EditText location = (EditText) findViewById(R.id.location);
        EditText capacity = (EditText) findViewById(R.id.capacity);
        savedInstanceState.putString(STATE_FOOD, food.getText().toString());
        savedInstanceState.putString(STATE_EVENT, event.getText().toString());
        savedInstanceState.putString(STATE_DESCRIPTION, description.getText().toString());
        savedInstanceState.putString(STATE_LOCATION, location.getText().toString());
        savedInstanceState.putString(STATE_CAPACITY, capacity.getText().toString());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}


