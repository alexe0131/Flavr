package com.cs147.flavr;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import org.w3c.dom.Text;


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

    private void userEvents() {
        Intent events = new Intent(this, UserEvents.class);
        startActivity(events);
    }
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
        int[] times = new int[2];
//        TimePicker startTimer = (TimePicker) findViewById(R.id.start_time);
//        int startHour = startTimer.getCurrentHour();
//        int startMinute = startTimer.getCurrentMinute();
//        times[0] = startHour;
//        times[1] = startMinute;
        TimePicker endTimer = (TimePicker) findViewById(R.id.end_time);
        int endHour = endTimer.getCurrentHour();
        int endMinute = endTimer.getCurrentMinute();
        times[0] = endHour;
        times[1] = endMinute;

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
    public void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Create Event");
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
            newEvent.put(GetFoodList.END_HOUR, Integer.toString(timeInfo[0]));
            newEvent.put(GetFoodList.END_MIN, Integer.toString(timeInfo[1]));
            newEvent.put(GetFoodList.FOOD, foodType);
            newEvent.put(GetFoodList.EVENT, eventTitle);
            newEvent.put(GetFoodList.LOCATION, location);
            newEvent.put(GetFoodList.DESCRIPTION, description);
            newEvent.put(GetFoodList.CAPACITY, capacity);
            newEvent.put(GetFoodList.ATTENDANCE, 0);
            newEvent.put(GetFoodList.IMAGE, yourSelectedImage);
            newEvent.put(GetFoodList.ATTENDING, 0);
            double rangeMin = 0.1;
            double rangeMax=3.0;
            Random r = new Random();
            double randomValue = (int)(rangeMin + (rangeMax - rangeMin) * r.nextDouble()*10)/10.0;
            newEvent.put(GetFoodList.DISTANCE,Double.toString(randomValue));
            MainActivity.events.add(0, newEvent);
            MainActivity.userEvents.add(0, newEvent);
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
        setContentView(R.layout.activity_create_event);
        createCustomActionBar();
        yourSelectedImage = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
        Typeface open = Typeface.createFromAsset(getAssets(), "fonts/opensans_regular.ttf");
        Typeface alegreyaBold = Typeface.createFromAsset(getAssets(), "fonts/alegreyasanssc_bold.ttf");
        TextView food = (TextView) findViewById(R.id.food_type_label);
        EditText foodEntry = (EditText) findViewById(R.id.food_type);
        food.setTypeface(open);
        foodEntry.setTypeface(open);
        TextView event = (TextView) findViewById(R.id.event_title_label);
        EditText eventEntry = (EditText) findViewById(R.id.event_title);
        event.setTypeface(open);
        eventEntry.setTypeface(open);
        TextView location = (TextView) findViewById(R.id.location_label);
        EditText loc = (EditText) findViewById(R.id.location);
        location.setTypeface(open);
        loc.setTypeface(open);
        TextView description = (TextView) findViewById(R.id.description_label);
        EditText descriptionEntry = (EditText) findViewById(R.id.description);
        description.setTypeface(open);
        descriptionEntry.setTypeface(open);
        TextView endTime = (TextView) findViewById(R.id.end_time_label);
        endTime.setTypeface(open);
        TextView capacity = (TextView) findViewById(R.id.capacity_label);
        EditText cap = (EditText) findViewById(R.id.capacity);
        capacity.setTypeface(open);
        cap.setTypeface(open);
        TextView img = (TextView) findViewById(R.id.image_label);
        img.setTypeface(open);
        Button categories = (Button) findViewById(R.id.pick_categories);
        Button diet = (Button) findViewById(R.id.dietary_accomodations);
        Button image = (Button) findViewById(R.id.uploadimage);
        Button submit = (Button) findViewById(R.id.submit);
        categories.setTypeface(alegreyaBold);
        categories.setTextColor(Color.WHITE);
        TextView catLabel = (TextView) findViewById(R.id.category_label);
        catLabel.setTypeface(open);
        TextView dietLabel = (TextView) findViewById(R.id.diet_label);
        dietLabel.setTypeface(open);
        diet.setTypeface(alegreyaBold);
        diet.setTextColor(Color.WHITE);
        image.setTypeface(alegreyaBold);
        submit.setTypeface(alegreyaBold);
        submit.setTextColor(Color.WHITE);
        if (savedInstanceState != null) {
            // Restore value of members from saved state

            foodEntry.setText(savedInstanceState.getString(STATE_FOOD));
            eventEntry.setText(savedInstanceState.getString(STATE_EVENT));
            descriptionEntry.setText(savedInstanceState.getString(STATE_DESCRIPTION));
            loc.setText(savedInstanceState.getString(STATE_LOCATION));
            cap.setText(savedInstanceState.getString(STATE_CAPACITY));

        }
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
        switch (item.getItemId()) {
            case R.id.myevents4:
                userEvents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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


