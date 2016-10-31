package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kaspars.fisers on 10/26/2016.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{
    private String primaryLocation;
    private String locationOffset;
    private static final String LOCATION_SEPARATOR = " of ";
    public EarthquakeAdapter(Context context, List<Earthquake> earthquake) {
        super(context,0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            //The view is not a recycled one: we have to inflate
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);

            holder = new ViewHolder();
            holder.magnitudeView = (TextView) convertView.findViewById(R.id.magnitude);
            holder.primaryLocationView = (TextView) convertView.findViewById(R.id.primary_location);
            holder.locationOffsetView = (TextView) convertView.findViewById(R.id.location_offset);
            holder.dateView = (TextView) convertView.findViewById(R.id.date);
            holder.timeView = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            // View recycled,
            // no need to inflate,
            // no need to findViews by id
            holder = (ViewHolder) convertView.getTag();
        }


        Earthquake currentEarthquake = getItem(position);

        String originalLocation = currentEarthquake.getLocation();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) holder.magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        String formattedDate = formatDate(dateObject);
        String formattedTime = formatTime(dateObject);
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

        holder.magnitudeView.setText(formattedMagnitude);
        holder.dateView.setText(formattedDate);
        holder.timeView.setText(formattedTime);
        holder.primaryLocationView.setText(primaryLocation);
        holder.locationOffsetView.setText(locationOffset);

    return convertView;
    }

    private  class ViewHolder {
        TextView magnitudeView;
        TextView locationView;
        TextView dateView;
        TextView timeView;
        TextView primaryLocationView;
        TextView locationOffsetView;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
