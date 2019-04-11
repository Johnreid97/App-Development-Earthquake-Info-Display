//
// Name                 John Reid
// Student ID           S1511280
// Programme of Study   Computer Games Development (Software Development)
//
package com.example.mobileplatformcoursework;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    List<Earthquake> earthquakes;
    Context context;
    //information about the earthquake passed to the popup class to display when a card is clicked
    public static final String EXTRA_HOUR ="com.example.mobileplatformcoursework.EXTRA_HOUR";
    public static final String EXTRA_MINUTE ="com.example.mobileplatformcoursework.EXTRA_MINUTE";
    public static final String EXTRA_SECOND ="com.example.mobileplatformcoursework.EXTRA_SECOND";
    public static final String EXTRA_LAT ="com.example.mobileplatformcoursework.EXTRA_LAT";
    public static final String EXTRA_LONG ="com.example.mobileplatformcoursework.EXTRA_LONG";
    public static final String EXTRA_DEPTH ="com.example.mobileplatformcoursework.EXTRA_DEPTH";
    public static final String EXTRA_MAG ="com.example.mobileplatformcoursework.EXTRA_MAG";




    public MyAdapter(List<Earthquake> earthquakes, Context context)
    {
        //adapterConstructor
        this.earthquakes = earthquakes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        //Gets the current earthquake and displays the location as the title and the day, date, month and year as the description of the card view
        final Earthquake earthquake = earthquakes.get(i);


        viewHolder.textViewHead.setText(earthquake.location);

        viewHolder.textViewDescription.setText(earthquake.day + " "+ String.format("%.0f", earthquake.date) + " " + earthquake.month + " " + String.format("%.0f",earthquake.year));




        viewHolder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //listens for a button click on more info and if so opens a new activity and displays the rest of the information about the earthquake
                MainActivity mainActivity = new MainActivity();

                Intent i = new Intent(MyAdapter.this.context, PopupClass.class);

                // mainActivity.PopUpMenu(earthquake, i);
                i.putExtra(EXTRA_HOUR, earthquake.hour);
                i.putExtra(EXTRA_MINUTE, earthquake.minute);
                i.putExtra(EXTRA_SECOND, earthquake.second);
                i.putExtra(EXTRA_LAT, earthquake.lat);
                i.putExtra(EXTRA_LONG, earthquake.longitued);
                i.putExtra(EXTRA_DEPTH, earthquake.depth);
                i.putExtra(EXTRA_MAG, earthquake.magnitude);
                MyAdapter.this.context.startActivity(i);



            }
        });


        //checks to see if the card has been clicked and does the same as the button
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MyAdapter.this.context, PopupClass.class);


                i.putExtra(EXTRA_HOUR, earthquake.hour);
                i.putExtra(EXTRA_MINUTE, earthquake.minute);
                i.putExtra(EXTRA_SECOND, earthquake.second);
                i.putExtra(EXTRA_LAT, earthquake.lat);
                i.putExtra(EXTRA_LONG, earthquake.longitued);
                i.putExtra(EXTRA_DEPTH, earthquake.depth);
                i.putExtra(EXTRA_MAG, earthquake.magnitude);
                MyAdapter.this.context.startActivity(i);
                Log.d("John" ,"Click");

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return earthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Instantiates the relevant UI information
        public TextView textViewHead;
        public TextView textViewDescription;
        public Button infoButton;
        public RelativeLayout relativeLayout;


        MainActivity mainActivity;

        public ViewHolder(@NonNull final View itemView)
        {
            //Sets the relevant UI information
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            infoButton = (Button) itemView.findViewById(R.id.textViewButton);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);

            mainActivity = new MainActivity();



            }


    }
}
