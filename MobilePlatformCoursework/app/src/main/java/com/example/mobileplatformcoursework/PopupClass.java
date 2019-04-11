//
// Name                 John Reid
// Student ID           S1511280
// Programme of Study   Computer Games Development (Software Development)
//
package com.example.mobileplatformcoursework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class PopupClass extends Activity {

//Variables used to display required earthquake information
    TextView hour;

    TextView lat;
    TextView longitude;
    TextView depth;
    TextView magnitude;

    float hour_;
    float minute_;
    float second_;
    float lat_;
    float long_;
    float depth_;
    float magnitude_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        //Gets the necessary information from the information passed to it from the MyAdapter class
        Intent intent = getIntent();
        hour_ = intent.getFloatExtra(MyAdapter.EXTRA_HOUR, 0);
        minute_ = intent.getFloatExtra(MyAdapter.EXTRA_MINUTE, 0);
        second_ = intent.getFloatExtra(MyAdapter.EXTRA_SECOND, 0);
        lat_ = intent.getFloatExtra(MyAdapter.EXTRA_LAT, 0);
        long_ = intent.getFloatExtra(MyAdapter.EXTRA_LONG, 0);
        depth_ = intent.getFloatExtra(MyAdapter.EXTRA_DEPTH, 0);
        magnitude_ = intent.getFloatExtra(MyAdapter.EXTRA_MAG, 0);
        DisplayMetrics dm = new DisplayMetrics();


        //Gets the dimensions of the screen
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Sets it to be a certain size of the window
        getWindow().setLayout((int) (width *.8), (int) (height *.7));

        //Finds the required UI Elements
        hour = (TextView) findViewById(R.id.hour);

        lat = (TextView) findViewById(R.id.lat);
        longitude = (TextView) findViewById(R.id.longitude);
        depth = (TextView) findViewById(R.id.depth);
        magnitude = (TextView) findViewById(R.id.magnitude);

        //Displays the information using the required UI elements
        hour.setText("Time: " + " " + "" + String.format("%.0f",hour_) + ":" + String.format("%.0f",minute_) + "." + String.format("%.0f",second_));

        lat.setText("Latitude: " + "" + lat_);
        longitude.setText("Longitude: "+"" + long_);
        depth.setText( "Depth: " + "" + String.format("%.0f",depth_) + "km");
        magnitude.setText( "Magnitude: " + "" + magnitude_);

    }
}
