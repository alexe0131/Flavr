package com.cs147.flavr;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import net.java.jddac.common.type.ArgMap;

import java.util.List;

/*
* Created by Alex on 11/24/14.
*/
public class EventListAdaptr extends ArrayAdapter<ArgMap> {
    List<ArgMap> events;
    Context context;
    int resourceId;
    public static Bitmap defaultPicture;
    /* List adapter is used to switch which views are appearing in the events listview. For example,
    * we will only see 4ish events at once. If there is 10,000 events, we definitely do not want to
    * load them all. You can see our events list is set to be the list and the default food picture is
    * converted to a bitmap to be put into our "get food" list view.
    */
    public EventListAdaptr(Context context, int resourceId,
                           List<ArgMap> list) {
        super(context, resourceId, list);
        events = list;
        this.context = context;
        this.resourceId = resourceId;
        if(defaultPicture == null) defaultPicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultfood);
    }

    @Override
    /* Inflates the linear layouts that hold the events in our listview. After inflating,
    * we can set the textviews and imageview inside the inflated linear layout.
    */
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView=null;

        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(resourceId, parent, false);
        } else {
            rowView = convertView;
        }

        // Fill with goodies

        ArgMap event = events.get(position);
        rowView.setTag(event);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/alegreyasanssc_regular.ttf");
        TextView eventFood = (TextView) rowView.findViewById(R.id.event_food);
        eventFood.setTypeface(font);
        eventFood.setText(event.getString(GetFoodList.FOOD, ""));
        TextView eventTitle = (TextView) rowView.findViewById(R.id.event_event);
        eventTitle.setTypeface(font);
        eventTitle.setText(event.getString(GetFoodList.EVENT,""));
        TextView eventLocation = (TextView) rowView.findViewById(R.id.event_location);
        eventLocation.setTypeface(font);
        eventLocation.setText(event.getString(GetFoodList.LOCATION,""));
        TextView eventDistance = (TextView) rowView.findViewById(R.id.event_distance);
        eventDistance.setTypeface(font);
        SpannableStringBuilder sb = new SpannableStringBuilder(event.getString(GetFoodList.DISTANCE)+" miles away");
        StyleSpan b = new StyleSpan(Typeface.BOLD);
        sb.setSpan(b, 0, 0+event.getString(GetFoodList.DISTANCE).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        eventDistance.setText(sb);
        TextView eventAttendance = (TextView) rowView.findViewById(R.id.event_attendance);
        eventAttendance.setTypeface(font);
        SpannableStringBuilder sc = new SpannableStringBuilder(event.getString(GetFoodList.ATTENDANCE)+" attending");
        StyleSpan c = new StyleSpan(Typeface.BOLD);
        sb.setSpan(c, 0, 0+event.getString(GetFoodList.ATTENDANCE).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        eventAttendance.setText(sc);
        ImageView eventImage = (ImageView) rowView.findViewById(R.id.event_picture);
        if(MainActivity.userEvents.indexOf(event)== -1) {
            int imageResource = getContext().getResources().getIdentifier(event.getString(GetFoodList.IMAGE), null, "com.cs147.flavr");
            if (imageResource != 0) {
                Drawable custom = getContext().getResources().getDrawable(imageResource);
                eventImage.setImageDrawable(custom);
            }
        }
        else eventImage.setImageBitmap((Bitmap) event.get(GetFoodList.IMAGE, defaultPicture));
        return rowView;
    }
}
