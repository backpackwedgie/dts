package com.downthestreetapp.downthestreet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.downthestreetapp.downthestreet.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DisplayMessageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		if (message == null)
		{
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			message = sharedPreferences.getString("phpString", "noresults");
		}
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			InputStream iStream = new ByteArrayInputStream(message.getBytes("UTF-8"));
			parser.setInput(iStream, null);
			parseXML (parser);
		}
		catch (XmlPullParserException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.display_message, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	ArrayList<Event>pubEvents = null;

	private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	String locationProvider = LocationManager.NETWORK_PROVIDER; 
    	Location location = locationManager.getLastKnownLocation(locationProvider);
		ArrayList<Event> events = null;
        int eventType = parser.getEventType();
        Event currentEvent = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
        	String name = null;
            switch (eventType)
            {
            	case XmlPullParser.START_DOCUMENT:
            		events = new ArrayList<Event>();
            		events.clear();
            		break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("markers"))
                    {
                        currentEvent = new Event();
                    }
                    else if (currentEvent != null)
                    {
                    	if (name.equals("eventtitle"))
                    	{
                            currentEvent.eventtitle = parser.nextText();
                        }
                    	else if (name.equals("eventhost"))
                    	{
                    		currentEvent.hostedBy = parser.nextText();
                    	}
                    	else if (name.equals("placename"))
                    	{
                    		currentEvent.placeName = parser.nextText();
                    	}
                    	else if (name.equals("starttime"))
                    	{
                        	currentEvent.starttime = parser.nextText();
                        }
                    	else if (name.equals("endtime"))
                    	{
                            currentEvent.endtime= parser.nextText();
                        }
                    	else if (name.equals("shdesc"))
                    	{
                        	currentEvent.shdesc = parser.nextText();
                        }
                    	else if (name.equals("longdesc"))
                    	{
                        	currentEvent.lgdesc = parser.nextText();
                        }
                    	else if (name.equals("lat"))
                    	{
                    		String latTemp = parser.nextText();
                    		if (latTemp != null)
                    		{
                    			currentEvent.lat = Double.valueOf(latTemp);
                    		} else { currentEvent.lat = 0; }
                        }
                    	else if (name.equals("lng"))
                    	{
                        	String lngTemp = parser.nextText();
                      		if (lngTemp != null)
                    		{
                    			currentEvent.lng = Double.valueOf(lngTemp);
                    		} else { currentEvent.lng = 0; }
                        	currentEvent.mylocation = location;
                        }
                	}
                    break;
                case XmlPullParser.END_TAG:
                   	name = parser.getName();
                   	if (name.equalsIgnoreCase("markers") && currentEvent != null)
                   	{
                   		events.add(currentEvent);
                    } 
               	break;
            }
            eventType = parser.next();
        }
        printEvents(events);
        pubEvents = events;
	}
	private void printEvents(ArrayList<Event> passedevents)
	{
		final ListView list;
		list = (ListView)findViewById(R.id.my_text);
		String eventNameList[] = new String[passedevents.size()];
		Iterator<Event> it = passedevents.iterator();
		int i = 0;
		while(it.hasNext())
		{
			Event currEvent  = it.next();
			eventNameList[i] = currEvent.shdesc;
			i++;
		}

		// This is a simple adapter that accepts as parameter
		// Context
		// Data list
		// The row layout that is used during the row creation
		// The keys used to retrieve the data
		// The View id used to show the data. The key number and the view id must match
		list.setAdapter(new CustomBaseAdapter(this,passedevents));
		
		list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
            	Object o = list.getItemAtPosition(position);
                Event fullObject = (Event)o;
                try
                {
                	Intent i = new Intent(getApplicationContext(), Class.forName("com.downthestreetapp.downthestreet.ItemActivity"));
                	i.putExtra("com.downthestreetapp.downthestreet.Event", fullObject);
                	startActivity(i);
                }
                catch (ClassNotFoundException e)
                {
                	e.printStackTrace();
                }
            }
        });
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
        case R.id.sort_dist:
            Collections.sort(pubEvents, sortDist);
            printEvents(pubEvents);
            return true;
        case R.id.sort_time:
            Collections.sort(pubEvents, sortTime);
            printEvents(pubEvents);
            return true;
        default:
        	return super.onOptionsItemSelected(item);
		}
	}

	public static Comparator<Event> sortDist = new Comparator<Event>()
	{
		public int compare(Event d1, Event d2)
		{
			return d1.doubleD().compareTo(d2.doubleD());
		}
	};
	public static Comparator<Event> sortTime = new Comparator<Event>()
	{
		public int compare(Event d1, Event d2)
		{
			return d1.doublemilidif().compareTo(d2.doublemilidif());
		}
	};
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}

}