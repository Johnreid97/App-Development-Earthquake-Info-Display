//
// Name                 John Reid
// Student ID           S1511280
// Programme of Study   Computer Games Development (Software Development)
//

// Update the package name to include your Student Identifier
package com.example.mobileplatformcoursework;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity
{
    //Variables used to hold and display earthquake information
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ImageButton sortButton;

    ArrayList<String> descriptions;
    ArrayList<Earthquake> earthquakes;
    ArrayList<Earthquake> sortedList;
    EditText searchBar;
    int textLength = 0;
    boolean isPortrait = false;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        // Displaying information and parsing done on a separate thread from teh main thread
        new DisplayInformation().execute();
        new ParcingData().execute();




    }

    @Override
    protected void onResume() {
        super.onResume();
        //reloads the parsing and display info whenever the app is resumed
        new ParcingData().execute();

    }


    public void Setup()
    {
        //creates new lists needed
        descriptions = new ArrayList<String>();
        earthquakes = new ArrayList<Earthquake>();
        sortedList = new ArrayList<Earthquake>();

        // creates and displays information using the adapter
        adapter = new MyAdapter(earthquakes, this);

        //checks to see which orientation the phone is on when the app is started and sets up the layouts accordingly
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            isPortrait = true;
            setContentView(R.layout.activity_main);
            Portrait();
            recyclerView.setAdapter(adapter);
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            isPortrait = false;
            setContentView(R.layout.landscape_view);
            Landscape();
            recyclerView.setAdapter(adapter);
        }

        // Sets up the search bar
        searchBar = (EditText) findViewById(R.id.search_bar);
        //Sets up sort button
        sortButton = (ImageButton) findViewById(R.id.sort_Button);
        //recyclerView.setAdapter(adapter);



    }

    public void Portrait()
    {
        //sets the recycler view and displays it in the portrait format
        //adapter = new MyAdapter(earthquakes, this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);


    }

    public void Landscape()
    {
        //Sets up the recycler view and displays it in the landscape format
       // adapter = new MyAdapter(earthquakes, this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, 1, false));
       // adapter = new MyAdapter(earthquakes, this);
        recyclerView.setAdapter(adapter);


    }

    private  class CustomComparatorNorth implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o2.lat).compareTo(Float.toString(o1.lat));
        }


    }

    private  class CustomComparatorSouth implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o1.lat).compareTo(Float.toString(o2.lat));
        }


    }
    private  class CustomComparatorEast implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o1.longitued).compareTo(Float.toString(o2.longitued));
        }


    }
    private  class CustomComparatorWest implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o2.longitued).compareTo(Float.toString(o1.longitued));
        }


    }
    private  class CustomComparatorName implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return o2.location.compareTo(o1.location);
        }


    }
    private  class CustomComparatorMonth implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return o1.month.compareTo(o2.month);
        }


    }
    private  class CustomComparatorMagnitude implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o1.magnitude).compareTo(Float.toString(o2.magnitude));
        }


    }

    private  class CustomComparatorDepth implements Comparator<Earthquake>
    {

        @Override
        public int compare(Earthquake o1, Earthquake o2)
        {
            return Float.toString(o2.depth).compareTo(Float.toString(o1.depth));
        }


    }



    public void SortButton()
    {
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PopupMenu sortMenu = new PopupMenu(MainActivity.this, sortButton);
                sortMenu.getMenuInflater().inflate(R.menu.sort_menu, sortMenu.getMenu() );

                sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId())
                        {
                            case R.id.north_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorNorth());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.east_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorEast());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.south_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorSouth());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.west_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorWest());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.location_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorName());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.depth_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorDepth());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.mag_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorMagnitude());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;
                            case R.id.month_Sort:
                                searchBar.setText("");
                                Collections.sort(earthquakes, new CustomComparatorMonth());

                                adapter = new MyAdapter(earthquakes, MainActivity.this);
                                recyclerView.setAdapter(adapter);
                                break;





                        }
                        //  Log.d("John" ,"Click");
                        return true;
                    }
                });

                sortMenu.show();



            }
        });
    }




    public void SearchBarInit()
    {
        // checks to see if any text has been added and if so compares it to every location, magnitude and depth and displays whatever results match
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                textLength = searchBar.getText().length();
                sortedList.clear();
                for (int i = 0; i < earthquakes.size(); i++)
                {
                    if (textLength <= earthquakes.get(i).location.length())
                {
                    if (earthquakes.get(i).location.toLowerCase().trim().contains(
                            searchBar.getText().toString().toLowerCase().trim()
                    ))
                    {
                        sortedList.add(earthquakes.get(i));
                    }
                }
                    if (textLength <= Float.toString(earthquakes.get(i).magnitude).length())
                    {
                        if (Float.toString(earthquakes.get(i).magnitude).toLowerCase().trim().contains(
                                searchBar.getText().toString().toLowerCase().trim()
                        ))
                        {
                            sortedList.add(earthquakes.get(i));
                        }
                    }
                    if (textLength <= Float.toString(earthquakes.get(i).depth).length())
                    {
                        if (Float.toString(earthquakes.get(i).depth).toLowerCase().trim().contains(
                                searchBar.getText().toString().toLowerCase().trim()
                        ))
                        {
                            sortedList.add(earthquakes.get(i));
                        }
                    }
                }


                //Checks what orientation the phone is to display the correct layout
                if (isPortrait == true)
                {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                else
                {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, 1, false));
                }
                adapter = new MyAdapter(sortedList, MainActivity.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


    }

    public InputStream getInputStream(URL url)
    {
        //returns the url as an Input stream
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    //This is used to parse data on the website, also done on a separate thread
    public class  ParcingData extends AsyncTask<Integer, Void, Exception>
    {


        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setMessage("Loading XML File...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers)
        {
            try
            {
                //checks to see if it can make a connection and if it can checks for all the information within the "Item" tags as they hold the necessary information and adds them to a list
                // This will loop until there are no item tags left
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem= false;

                int eventType = xpp.getEventType();

                while ( eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }

                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                descriptions.add(xpp.nextText());


                            }
                        }


                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }

            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception e)
        {


            //Loops through the descriptions just gained and splits them up using the semi-colons whithin the string and adds them to an array
            for (int i = 0; i < descriptions.size(); i++)
            {
               String[] split  = descriptions.get(i).split(";");
               SplitStrings(split);
            }


            progressDialog.dismiss();
        }


    }

    public void SplitStrings(String[] s)
    {

        //used to hold the necessary information about the earthquakes
        String day;
        String date;
        String month;
        String year;
        String hour;
        String  minute;
        String second;
        String location;
        String lat;
        String longituede;
        String depth;
        String magnitude;


        //The following splits up the information by using specific characters with the semi-colons as reference
        day = s[0].substring(18,21);
        date = s[0].substring(23,25);
        month = s[0].substring(26,30);
        year = s[0].substring(30,34);
        hour = s[0].substring(35,37);
        minute = s[0].substring(38,40);
        second = s[0].substring(41,43);
        location = s[1].split(":")[1];
        location = location.trim();
        lat = s[2].substring(11,17);
        longituede = s[2].substring(18,24);
        longituede = longituede.trim();
        depth = s[3].substring(7,10);
        depth = depth.trim();
        magnitude = s[4].substring(12,16);
        magnitude = magnitude.trim();

        //Adds to an earthquake list using the necessary information, has to be passed in as floats or it crashes for some reason
        //Log.d("John", "" );
        Earthquake e = new Earthquake(day, Float.parseFloat(date), month,
                (Float.parseFloat(year)), Float.parseFloat(hour), Float.parseFloat(minute),
                                        Float.parseFloat(second), location, Float.parseFloat(lat),
                                        Float.parseFloat(longituede), Float.parseFloat(depth), Float.parseFloat(magnitude));
        earthquakes.add(e);
    }



   public class DisplayInformation extends AsyncTask<Void, Void, Void>
   {
        //Class sets up all the UI elements and uses AsyncTask as it is a quick and easy way to get to the UI thread
       @Override
       protected Void doInBackground(Void... voids) {
           Setup();
           SearchBarInit();
           SortButton();

           return null;
       }
   }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Creates Listener to check when the orientation gets changed and ajusts the layout accordingly
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
             isPortrait = true;
            setContentView(R.layout.activity_main);
            Portrait();
            searchBar.setText(searchBar.getText());
        }
        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
             isPortrait = false;
            setContentView(R.layout.landscape_view);
            Landscape();
            searchBar.setText(searchBar.getText());
        }

    }


}