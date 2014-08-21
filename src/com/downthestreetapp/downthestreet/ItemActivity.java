package com.downthestreetapp.downthestreet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//import java.util.TimeZone;

import com.downthestreetapp.downthestreet.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item_activity_layout);
         
        TextView eventHeading = (TextView) findViewById(R.id.eventname);
        TextView eventDetails = (TextView) findViewById(R.id.eventdetails);
        TextView startTime = (TextView) findViewById(R.id.sttime);
        TextView endTime = (TextView) findViewById(R.id.entime);
        TextView hostedByV = (TextView) findViewById(R.id.hosted_by);
        Bundle b = getIntent().getExtras();
        Event itemEvent = b.getParcelable("com.downthestreetapp.downthestreet.Event");
        String ename = itemEvent.eventtitle;
        String details = itemEvent.lgdesc;
        String ssstime = itemEvent.starttime;
        String eeetime = itemEvent.endtime;
        String hBy = itemEvent.hostedBy;
        Date startTimeParsed = null;
        Date endTimeParsed = null;
        DateFormat itemDateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy",Locale.ENGLISH);
        //itemDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat itemTimeFormat = new SimpleDateFormat("h:mm aaa", Locale.ENGLISH);
        //itemTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
			startTimeParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(ssstime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			endTimeParsed = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(eeetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String sDateParsed = itemDateFormat.format(startTimeParsed);
        String eDateParsed = itemDateFormat.format(endTimeParsed);
        String sTimeParsed = itemTimeFormat.format(startTimeParsed);
        String eTimeParsed = itemTimeFormat.format(endTimeParsed);
        eventHeading.setText(ename);
        eventDetails.setText(details);
        startTime.setText("Starts on " + sDateParsed + " at " + sTimeParsed);
        endTime.setText("Ends on " + eDateParsed + " at " + eTimeParsed);
        hostedByV.setText("Hosted by: " + hBy);
    }

}
