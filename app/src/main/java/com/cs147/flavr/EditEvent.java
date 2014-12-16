package com.cs147.flavr;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import net.java.jddac.common.type.ArgMap;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class EditEvent extends Activity {
    public final static int SELECT_PHOTO = 100;
    public static Bitmap yourSelectedImage;
    public static Uri selectedImage;
    public static String TIMES = "edittimes";

    /* Allow user to edit image of their event.
     */
    public void uploadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }

    /* Set action bar font to match system standard.
     */
    private void createCustomActionBar() {
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title","id","android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface alegreya = Typeface.createFromAsset(getAssets(),"fonts/alegreyasanssc_bold.ttf");
        actionBarTitleView.setTypeface(alegreya);
        getActionBar().setTitle("Edit Event");
    }

    /* Bring the user to the page showing their created events.
     */
    private void userEvents() {
        Intent events = new Intent(this, UserEvents.class);
        startActivity(events);
    }

    /* Enter the old data from the event into the text fields/
     */
    private void enterOldData(ArgMap info) {
        EditText foodType = (EditText) findViewById(R.id.edit_food_type);
        foodType.setText(info.getString(GetFoodList.FOOD));
        EditText eventTitle = (EditText) findViewById(R.id.edit_event_title);
        eventTitle.setText(info.getString(GetFoodList.EVENT));
        EditText description = (EditText) findViewById(R.id.edit_description);
        description.setText(info.getString(GetFoodList.DESCRIPTION));
        EditText location = (EditText) findViewById(R.id.edit_location);
        location.setText(info.getString(GetFoodList.LOCATION));
        EditText capacity = (EditText) findViewById(R.id.edit_capacity);
        capacity.setText(info.getString(GetFoodList.CAPACITY));
    }

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

    /* Parse the new end time from the time picker in the form.
     */
    private int[] parseTimes() {
        TimePicker endTimer = (TimePicker) findViewById(R.id.edit_end_time);
        int endHour = endTimer.getCurrentHour();
        int endMinute = endTimer.getCurrentMinute();
        int[]times = new int[2];
        times[0] = endHour;
        times[1] = endMinute;

        return times;
    }

    /* Associate the new information with the argmap corresponding with the event.
     */
    public void changeEvent(View view) {
        Intent change = new Intent(this, EventConfirmation.class);
        ArgMap event = new ArgMap();
        EditText food = (EditText) findViewById(R.id.edit_food_type);
        event.put(GetFoodList.FOOD, food.getText());
        EditText eventTitle = (EditText) findViewById(R.id.edit_event_title);
        event.put(GetFoodList.EVENT, eventTitle.getText());
        EditText description = (EditText) findViewById(R.id.edit_description);
        event.put(GetFoodList.DESCRIPTION, description.getText());
        EditText location = (EditText) findViewById(R.id.edit_location);
        event.put(GetFoodList.LOCATION, location.getText());
        EditText capacity = (EditText) findViewById(R.id.edit_capacity);
        event.put(GetFoodList.CAPACITY, capacity.getText());

        MainActivity.events.set(0, event);
        int[] times = parseTimes();
        change.putExtra(TIMES, times);
        startActivity(change);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        createCustomActionBar();
        ArgMap editEvent = MainActivity.events.get(0);
        enterOldData(editEvent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.myevents3:
                userEvents();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
