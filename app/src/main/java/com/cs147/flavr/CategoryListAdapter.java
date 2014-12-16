package com.cs147.flavr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.java.jddac.common.type.ArgMap;

import java.util.List;

/**
 * Created by Alex on 12/1/14.
 */
public class CategoryListAdapter extends ArrayAdapter<String> {
    List<String> foodCategories;
    Context context;
    int resourceId;

    /* List adapter is used to switch which views are appearing in the events listview. For example,
    * we will only see 4ish events at once. If there is 10,000 events, we definitely do not want to
    * load them all. You can see our events list is set to be the list and the default food picture is
    * converted to a bitmap to be put into our "get food" list view.
    */
    public CategoryListAdapter(Context context, int resourceId,
                               List<String> list) {
        super(context, resourceId, list);
        foodCategories = list;
        this.context = context;
        this.resourceId = resourceId;
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

        String string = foodCategories.get(position);
        rowView.setTag(string);
        TextView eventFood = (TextView) rowView.findViewById(R.id.category);
        eventFood.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/opensans_regular.ttf"));
        eventFood.setText(string);
        return rowView;
    }
}

