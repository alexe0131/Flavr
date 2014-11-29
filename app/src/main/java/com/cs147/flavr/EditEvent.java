package com.cs147.flavr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public void uploadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

    }
    private void enterOldData(ArgMap info) {
        EditText foodType = (EditText) findViewById(R.id.edit_food_type);
        foodType.setText(info.getString(GetFoodList.FOOD));
        EditText eventTitle = (EditText) findViewById(R.id.edit_event_title);
        eventTitle.setText(info.getString(GetFoodList.EVENT));
        EditText description = (EditText) findViewById(R.id.edit_description);
        description.setText(info.getString(GetFoodList.DESCRIPTION));
        EditText location = (EditText) findViewById(R.id.edit_location);
        location.setText(info.getString(GetFoodList.LOCATION));
        EditText tags = (EditText) findViewById(R.id.edit_tags);
        tags.setText(info.getString(GetFoodList.TAGS));
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

    private int[] parseTimes() {
        int[] times = new int[4];
        TimePicker startTimer = (TimePicker) findViewById(R.id.edit_start_time);
        int startHour = startTimer.getCurrentHour();
        int startMinute = startTimer.getCurrentMinute();
        times[0] = startHour;
        times[1] = startMinute;
        TimePicker endTimer = (TimePicker) findViewById(R.id.edit_end_time);
        int endHour = endTimer.getCurrentHour();
        int endMinute = endTimer.getCurrentMinute();
        times[2] = endHour;
        times[3] = endMinute;
        return times;
    }

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
        EditText tags = (EditText) findViewById(R.id.edit_tags);
        event.put(GetFoodList.TAGS, tags.getText());
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
