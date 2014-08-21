package com.downthestreetapp.downthestreet;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


public class Event implements Parcelable
{	
	public Integer id;
	public double lat;
	public double lng;
	public Location mylocation;
	public String eventtitle;
	public String shdesc;
	public String lgdesc;
	public String starttime;
	public String endtime;
	public String hostedBy;
	public String placeName;
	public int icon;
	public Double doubleD()
	{
		double longitude = mylocation.getLongitude();
		double latitude = mylocation.getLatitude();
		double latDif = latitude - lat;
		double latDifMiles = latDif * 69;
		double lngDif = longitude - lng;
		double lngDifMiles = lngDif * Math.cos(Math.toRadians(latitude)) * 69;
		double latSq = Math.pow(latDifMiles, 2);
		double lngSq = Math.pow(lngDifMiles, 2);
		double d = Math.sqrt(latSq + lngSq);
		return d;
	}
	public String Distance()
	{
		double longitude = mylocation.getLongitude();
		double latitude = mylocation.getLatitude();
		double latDif = latitude - lat;
		double latDifMiles = latDif * 69;
		double lngDif = longitude - lng;
		double lngDifMiles = lngDif * Math.cos(Math.toRadians(latitude)) * 69;
		double latSq = Math.pow(latDifMiles, 2);
		double lngSq = Math.pow(lngDifMiles, 2);
		double d = Math.sqrt(latSq + lngSq);
		String dString = String.format("%.2f", d) + " miles";
		return dString;
	}
	public String TimeTill()
	{
		String t = "Soon.";
		long milidate = System.currentTimeMillis();
		Date startTimeDated = null;
		Date endTimeDated = null;
		DateFormat timeLeftDays = new SimpleDateFormat("D",Locale.ENGLISH);
		timeLeftDays.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat daysTime = new SimpleDateFormat("d", Locale.ENGLISH);
		DateFormat yearsTime = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		DateFormat timeLeftHours = new SimpleDateFormat("H", Locale.ENGLISH);
		timeLeftHours.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat timeLeftMinutes = new SimpleDateFormat("m",Locale.ENGLISH);
		timeLeftMinutes.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat properDate = new SimpleDateFormat ("EEEE, MMMM d, yyyy",Locale.ENGLISH);
		DateFormat properTime = new SimpleDateFormat ("h:mm aaa", Locale.ENGLISH);
		try {
			if (starttime == null || starttime.isEmpty())
			{
				starttime = "2001-01-01 10:00:00";
			}
			startTimeDated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long milistartdate = startTimeDated.getTime();
		String properDateParsed = properDate.format(startTimeDated);
		String properTimeParsed = properTime.format(startTimeDated);
		long milidif = milistartdate - milidate;
		String tDays = null;
		String tHours;
		String tMinutes;
		String distantDate = "Starts on " + properDateParsed + " at " + properTimeParsed;
		String tomorrowDate = "Starts tomorrow at " + properTimeParsed;
		String nowDays = daysTime.format(milidate);
		String startDays = daysTime.format(startTimeDated);
		String nowYears = yearsTime.format(milidate);
		String startYears = yearsTime.format(startTimeDated);
		Long nowDaysLong = Long.valueOf(nowDays);
		Long startDaysLong = Long.valueOf(startDays);
		Long nowYearsLong = Long.valueOf(nowYears);
		Long startYearsLong = Long.valueOf(startYears);
		Long difDay;
		if (nowYearsLong < startYearsLong)
		{
			difDay = startDaysLong + 365 - nowDaysLong;
		}
		else
		{
			difDay = startDaysLong - nowDaysLong;
		}
		if (milidif > 0)
		{
			Date d = new Date(milidif);
			//Long days = Long.parseLong(timeLeftDays.format(d))-1;
			if (difDay > 1)
			{
				t = distantDate;
			}
			else 
			{
				if (difDay == 1)
				{
					t = tomorrowDate;
				}
				else
				{
					tDays = "";
				}
			}
			Long hours = Long.parseLong(timeLeftHours.format(d));
			if (hours > 1)
			{
				tHours = Long.toString(hours) + " hours ";
			}
			else 
			{
				if (hours == 1)
				{
					tHours = Long.toString(hours) + " hour ";
				}
				else
				{
					tHours = "";
				}
			}
			Long minutes = Long.parseLong(timeLeftMinutes.format(d));
			if (minutes > 1)
			{
				tMinutes = Long.toString(minutes) + " minutes";
			}
			else 
			{
				if (minutes == 1)
				{
					tMinutes = Long.toString(minutes) + " minute";
				}
				else
				{
					tMinutes = "";
				}
			}
			if (tDays != null)
			{		
				t = "Starts in " + tDays + tHours + tMinutes;
			}
		}
		else
		{
			try {
				if (endtime == null || endtime.isEmpty())
				{
					endtime = "2001-01-01 10:00:00";
				}
				endTimeDated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(endtime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long milienddate = endTimeDated.getTime();
			long milidifend = milienddate - milidate;
			if (milidifend > 0)
			{
				Date d = new Date(milidifend);
				Long days = Long.parseLong(timeLeftDays.format(d))-1;
				if (days > 1)
				{
					tDays = Long.toString(days) + " days ";
				}
				else 
				{
					if (days == 1)
					{
						tDays = Long.toString(days) + " day ";
					}
					else
					{
						tDays = "";
					}
				}
				Long hours = Long.parseLong(timeLeftHours.format(d));
				if (hours > 1)
				{
					tHours = Long.toString(hours) + " hours ";
				}
				else 
				{
					if (hours == 1)
					{
						tHours = Long.toString(hours) + " hour ";
					}
					else
					{
						tHours = "";
					}
				}
				Long minutes = Long.parseLong(timeLeftMinutes.format(d));
				if (minutes > 1)
				{
					tMinutes = Long.toString(minutes) + " minutes";
				}
				else 
				{
					if (minutes == 1)
					{
						tMinutes = Long.toString(minutes) + " minute";
					}
					else
					{
						tMinutes = "";
					}
				}
				t = "Ends in " + tDays + tHours + tMinutes;
			}
			else
			{
				t = "This event ended.";
			}
		}
		return t;
	}
	public Event()
	{
	}
	public Double doublemilidif()
	{
		Double milidate = Double.valueOf(System.currentTimeMillis());
		Date startTimeDated = null;
		Date endTimeDated = null;
		try {
			if (starttime == null)
			{
				starttime = "2001-01-01 00:00:00";
			}
			startTimeDated = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH).parse(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double milistartdate = Double.valueOf(startTimeDated.getTime());
		Double milidif = milistartdate - milidate;
		if (milidif < 0)
		{
			try {
				if (endtime == null)
				{
					endtime = "2001-01-01 00:00:00";
				}
				endTimeDated = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH).parse(endtime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Double milienddate = Double.valueOf(endTimeDated.getTime());
			milidif = milienddate - milidate;
		}
		else
		{
			milidif = milidif * 2;
		}
		return milidif;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}

	public void writeToParcel(Parcel out, int flags)
	{
		out.writeString(eventtitle);
		out.writeString(shdesc);
		out.writeString(lgdesc);
		out.writeString(starttime);
		out.writeString(endtime);
		out.writeString(hostedBy);
	}
	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>()
	{
		public Event createFromParcel(Parcel in)
		{
			return new Event(in);
		}
		public Event[] newArray(int size)
		{
			return new Event[size];
		}
	};
	private Event (Parcel in)
	{
		eventtitle = in.readString();
		shdesc = in.readString();
		lgdesc = in.readString();
		starttime = in.readString();
		endtime = in.readString();
		hostedBy = in.readString();
	}
}
