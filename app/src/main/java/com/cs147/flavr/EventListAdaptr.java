package com.cs147.flavr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.java.jddac.common.type.ArgMap;

import java.util.List;

/*
 * Created by Alex on 11/24/14.
 */
public class EventListAdaptr extends ArrayAdapter<ArgMap> {
    List<ArgMap> events;
    Context context;
    int resourceId;

    public EventListAdaptr(Context context, int resourceId,
                           List<ArgMap> list) {
        super(context, resourceId, list);
        events = list;
        this.context = context;
        this.resourceId = resourceId;
    }

    @Override
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
        TextView eventFood = (TextView) rowView.findViewById(R.id.event_food);
        eventFood.setText(event.getString(GetFoodList.FOOD, ""));
        TextView eventTitle = (TextView) rowView.findViewById(R.id.event_event);
        eventTitle.setText(event.getString(GetFoodList.EVENT,""));
        TextView eventLocation = (TextView) rowView.findViewById(R.id.event_location);
        eventLocation.setText(event.getString(GetFoodList.LOCATION,""));
         return rowView;
    }
}
