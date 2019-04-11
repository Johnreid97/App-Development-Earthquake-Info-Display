//
// Name                 John Reid
// Student ID           S1511280
// Programme of Study   Computer Games Development (Software Development)
//
package com.example.mobileplatformcoursework;

import android.os.AsyncTask;


// This class is used to hold all the split up earthquake information gained from parsing
public class Earthquake  {
    String day;
    float date;
    String month;
    float year;
    float hour;
    float  minute;
    float second;
    String location;
    float lat;
    float longitued;
    float depth;
    float magnitude;

    Earthquake (String day_,
                float date_,
                String month_,
                float year_,
                float hour_,
                float  minute_,
                float second_,
                String location_,
                float lat_,
                float longitued_,
                float depth_,
                float magnitude_)
    {
        day = day_;
        date = date_;
        month = month_;
        year = year_;
        hour = hour_;
        minute = minute_;
        second = second_;
        location = location_;
        lat = lat_;
        longitued = longitued_;
        depth = depth_;
        magnitude = magnitude_;
    }


}
