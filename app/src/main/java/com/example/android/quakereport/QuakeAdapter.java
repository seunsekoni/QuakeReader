package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuakeAdapter extends ArrayAdapter<Quaker> {
    private static final String LOCATION_SEPARATOR = "of";

    /**
     * constructor for the quaker adapter class
     *
     * @param context    takes in the activity being used
     * @param earthquake takes in list of objects
     */
    public QuakeAdapter(@NonNull Context context, @NonNull List<Quaker> earthquake) {
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

            // get the current position of the list item
            Quaker currentQuake = getItem(position);

            // gettting the location textviews and splitting into two textviews
            String places = currentQuake.getmLocation();
            String[] location = places.split(LOCATION_SEPARATOR);
            String offsetLocation;
            String primaryLocation;

            // separate the location textview into two wherever there's am "of"
            if (places.contains(LOCATION_SEPARATOR)) {
                offsetLocation = location[0] + LOCATION_SEPARATOR;
                primaryLocation = location[1];
            } else {
                offsetLocation = getContext().getString(R.string.near_the);
                primaryLocation = places;
            }

            // get the textview for the offset location
            TextView offsetLocationView = (TextView) listViewItem.findViewById(R.id.location_offset);
            offsetLocationView.setText(offsetLocation);

            // get the textview of the primary location
            TextView primaryLocationView = (TextView) listViewItem.findViewById(R.id.primary_location);
            primaryLocationView.setText(primaryLocation);

            // get the textview of the magnitude of the earthquake
            TextView magnitudeView = (TextView) listViewItem.findViewById(R.id.magnitude);
            String formattedMag = formatMagnitude(currentQuake.getmMagnitude());
            magnitudeView.setText(formattedMag);

            GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
            int magnitudeColor = getMagnitude(currentQuake.getmMagnitude());
            magnitudeCircle.setColor(magnitudeColor);


            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateTimeObject = new Date(currentQuake.getmTimeInMilliSeconds());

            // Find the TextView with view ID date
            TextView dateView = (TextView) listViewItem.findViewById(R.id.date);
            String formattedDate = formatDate(dateTimeObject);
            dateView.setText(formattedDate);

            // Find the TextView with view ID date
            TextView timeView = (TextView) listViewItem.findViewById(R.id.time);
            String formattedTime = formatTime(dateTimeObject);
            timeView.setText(formattedTime);

        }
        return listViewItem;
    }

    /**
     * @param dateTimeObject takes in the time in milliseconds
     * @return the formatted milliseconds time in a date readable representation as a string
     */
    private String formatDate(Date dateTimeObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateTimeObject);
    }

    /**
     * @param dateTimeObject takes in time in milliseconds
     * @return the formatted milliseconds time in a time readable representation as a string
     */
    private String formatTime(Date dateTimeObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateTimeObject);
    }

    /**
     * @param magObject takes in the magnitude in decimal value
     * @return the formatted magnitude value
     */
    private String formatMagnitude(double magObject) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(magObject);
    }

    /**
     * @param magnitude takes in a double value
     * @return the assigned color for the value of each magnitude
     */
    private int getMagnitude(double magnitude) {
        int magnitudeResourceColorId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeResourceColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeResourceColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeResourceColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeResourceColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeResourceColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeResourceColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeResourceColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeResourceColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeResourceColorId = R.color.magnitude9;
                break;
            default:
                magnitudeResourceColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeResourceColorId);
    }
}
