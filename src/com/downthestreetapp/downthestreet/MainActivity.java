package com.downthestreetapp.downthestreet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.downthestreetapp.downthestreet.R;

public class MainActivity extends ActionBarActivity {
	public static String EXTRA_MESSAGE = "com.downthestreetapp.downthestreet.MESSAGE";
    public static String opm;
	private Button button;
	private CheckBox checkbox0;
	private CheckBox checkbox1;
	private CheckBox checkbox2;
	private CheckBox checkbox3;
	private Spinner toTimeSpinner;
	private Spinner fromTimeSpinner;
	private Spinner placeSpinner;
	private EditText keywordText;
	String typeFood;
	String typeDrinks;
	String typeMusic;
	String typeOther;
    String placePostText = "no";
    String fromTimePostText = "0";
    String toTimePostText = "0";
    int placeDefaultPreference;
    int fromTimeDefaultPreference;
    int toTimeDefaultPreference;
    String placeDefaultString;
    String fromTimeDefaultString;
    String toTimeDefaultString;
    String checkbox0DefaultString;
    String checkbox1DefaultString;
    String checkbox2DefaultString;
    String checkbox3DefaultString;
    String keywordDefaultString;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Search button stuff
        button = (Button) findViewById (R.id.mysql_button);
        button.setOnClickListener(new View.OnClickListener()
        {
        	@Override
        	public void onClick (View v)
        	{
        		String serverURL = "http://www.down-the-street.com/mobilesearch.php";
        		new workIt().execute(serverURL);
        	}
        });
        //placeSpinner stuff
        placeSpinner = (Spinner) findViewById (R.id.place_spinner);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		String placePosition = sharedPreferences.getString("placeSpinnerPosition", "0");
		double placeDouble = Double.valueOf(placePosition);
		placeDefaultPreference = (int) placeDouble;
        placeSpinner.setSelection(placeDefaultPreference);
        placeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
        	public void onItemSelected(AdapterView<?> arg0, View view, int position, long id)
        	{
        		String placeSpinnerText = placeSpinner.getSelectedItem().toString();
        	    switch (placeSpinnerText)
        	    {
        	       	case "Near me":
        	       		placePostText = "0";
        	       		placeDefaultString = "0";
        	       		break;
        	       	case "Union Valley":
        	       		placePostText = "3";
        	       		placeDefaultString = "1";
        	       		break;
        	       	case "Quinlan":
        	       		placePostText = "1";
        	       		placeDefaultString = "2";
        	       		break;
        	       	case "Rockwall Harbor":
        	       		placePostText = "2";
        	       		placeDefaultString = "3";
        	       		break;
        	       	default:
        	       		placePostText = "0";
        	       		break;
        	    }
        	}
        	public void onNothingSelected(AdapterView<?> arg0) { }
        });
        //fromTimeSpinner stuff
        fromTimeSpinner = (Spinner) findViewById(R.id.from_time_spinner);
		String fromTimePosition = sharedPreferences.getString("fromTimeSpinnerPosition", "0");
		double fromTimeDouble = Double.valueOf(fromTimePosition);
		fromTimeDefaultPreference = (int) fromTimeDouble;
        fromTimeSpinner.setSelection(fromTimeDefaultPreference);
        fromTimeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
        	public void onItemSelected(AdapterView<?> arg0, View view, int position, long id)
        	{
        		Double miliNow = (double) System.currentTimeMillis();
        		Double fromTimePostDouble;
        		String fromTimeSpinnerText = fromTimeSpinner.getSelectedItem().toString();
        	    switch (fromTimeSpinnerText)
        	    {
    	       	case "now":
    	       		fromTimePostDouble = miliNow;
    	       		fromTimePostText = String.valueOf(fromTimePostDouble);
    	       		fromTimeDefaultString = "0";
    	       		break;
        	    case "1 hour from now":
        	       		fromTimePostDouble = miliNow + 3600000;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "1";
        	       		break;
        	       	case "5 hours from now":
        	       		fromTimePostDouble = miliNow + 18000000;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "2";
        	       		break;
        	       	case "12 hours from now":
        	       		fromTimePostDouble = miliNow + 43200000;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "3";
        	       		break;
        	       	case "tomorrow":
        	       		fromTimePostDouble = miliNow + 86400000;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "4";
        	       		break;
        	       	case "next week":
        	       		fromTimePostDouble = miliNow + 604800000;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "5";
        	       		break;
        	       	case "next month":
        	       		fromTimePostDouble = miliNow + 2505600000L;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "6";
        	       		break;
        	       	case "next year":
        	       		fromTimePostDouble = miliNow + 31536000000L;
        	       		fromTimePostText = String.valueOf(fromTimePostDouble);
        	       		fromTimeDefaultString = "7";
        	       		break;
        	       	default:
        	       		fromTimePostText = "0";
        	       		break;
        	    }
        	}
        	public void onNothingSelected(AdapterView<?> arg0) { }
        });
        //toTimeSpinner stuff
        toTimeSpinner = (Spinner) findViewById(R.id.to_time_spinner);
		String toTimePosition = sharedPreferences.getString("toTimeSpinnerPosition", "0");
		double toTimeDouble = Double.valueOf(toTimePosition);
		toTimeDefaultPreference = (int) toTimeDouble;
        toTimeSpinner.setSelection(toTimeDefaultPreference);
        toTimeSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
        	public void onItemSelected(AdapterView<?> arg0, View view, int position, long id)
        	{
        		Double miliNow = (double) System.currentTimeMillis();
        		Double toTimePostDouble;
        		String toTimeSpinnerText = toTimeSpinner.getSelectedItem().toString();
        	    switch (toTimeSpinnerText)
        	    {
        	       	case "1 hour from now":
        	       		toTimePostDouble = miliNow + 3600000;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "0";
        	       		break;
        	       	case "5 hours from now":
        	       		toTimePostDouble = miliNow + 18000000;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "1";
        	       		break;
        	       	case "12 hours from now":
        	       		toTimePostDouble = miliNow + 43200000;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "2";
        	       		break;
        	       	case "tomorrow":
        	       		toTimePostDouble = miliNow + 86400000;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "3";
        	       		break;
        	       	case "next week":
        	       		toTimePostDouble = miliNow + 604800000;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "4";
        	       		break;
        	       	case "next month":
        	       		toTimePostDouble = miliNow + 2505600000L;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "5";
        	       		break;
        	       	case "next year":
        	       		toTimePostDouble = miliNow + 31536000000L;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "6";
        	       		break;
        	       	case "forever":
        	       		toTimePostDouble = miliNow + 315360000000L;
        	       		toTimePostText = String.valueOf(toTimePostDouble);
        	       		toTimeDefaultString = "7";
        	       		break;
        	       	default:
        	       		toTimePostText = "0";
        	       		break;
        	    }
        	}
        	public void onNothingSelected(AdapterView<?> arg0) { }
        });
        //check box stuff
        checkbox0 = (CheckBox) findViewById (R.id.type_box_food);
        String checkbox0Preference = sharedPreferences.getString("box0check", "true");
        Boolean check0bool = Boolean.valueOf(checkbox0Preference);
        checkbox0.setChecked(check0bool);
        checkbox0DefaultString = String.valueOf(check0bool);
        if (check0bool == true){typeFood="Food";}else{typeFood="no";}
        checkbox0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (checkbox0.isChecked())
		        {
		        	typeFood="Food";
		        	checkbox0DefaultString = "true";
		        }
				else
		        { 
					typeFood="no";
					checkbox0DefaultString = "false";
				}
			}
		});
        checkbox1 = (CheckBox) findViewById (R.id.type_box_drinks);
        String checkbox1Preference = sharedPreferences.getString("box1check", "true");
        Boolean check1bool = Boolean.valueOf(checkbox1Preference);
        checkbox1.setChecked(check1bool);
        checkbox1DefaultString = String.valueOf(check1bool);
        if (check1bool == true){typeDrinks="Drinks";}else{typeDrinks="no";}
        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (checkbox1.isChecked())
		        {
		        	typeDrinks="Drinks";
		        	checkbox1DefaultString = "true";
		        }
				else
				{
					typeDrinks="no";
					checkbox1DefaultString = "false";
				}
			}
		});
        checkbox2 = (CheckBox) findViewById (R.id.type_box_music);
        String checkbox2Preference = sharedPreferences.getString("box2check", "true");
        Boolean check2bool = Boolean.valueOf(checkbox2Preference);
        checkbox2.setChecked(check2bool);
        checkbox2DefaultString = String.valueOf(check2bool);
        if (check2bool == true){typeMusic="Music";}else{typeMusic="no";}
        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (checkbox2.isChecked())
		        {
		        	typeMusic="Music";
		        	checkbox2DefaultString = "true";
		        }
				else
				{
					typeMusic="no";
					checkbox2DefaultString = "false";
				}
			}
		});
        checkbox3 = (CheckBox) findViewById (R.id.type_box_other);
        String checkbox3Preference = sharedPreferences.getString("box3check", "true");
        Boolean check3bool = Boolean.valueOf(checkbox3Preference);
        checkbox3.setChecked(check3bool);
        checkbox3DefaultString = String.valueOf(check3bool);
        if (check3bool == true){typeOther="Other";}else{typeOther="no";}
        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (checkbox3.isChecked())
		        {
		        	typeOther="Other";
		        	checkbox3DefaultString = "true";
		        }
				else
				{
					typeOther="no";
					checkbox3DefaultString = "false";
				}
			}
		});
        if (savedInstanceState == null)
        {
           	getSupportFragmentManager().beginTransaction()
           	.add(R.id.container, new PlaceholderFragment())
           	.commit();
        }
        //keyword stuff
        keywordText = (EditText) findViewById (R.id.keyword_box);
        keywordText.setText(keywordDefaultString,TextView.BufferType.EDITABLE);
    }


    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	String outputContent = opm;
    	keywordDefaultString = keywordText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, outputContent);
    	startActivity(intent);
        // Do something in response to button
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
  	//AsyncTask is a separate thread than the thread that runs the GUI
	//Any type of networking should be done with asynctask.
	class workIt extends AsyncTask<String, String, String> {
		Context context;
	    private String Error = null;
	    private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
	         
	    TextView uiUpdate = (TextView) findViewById(R.id.output);
		//three methods get called, first preExecture, then do in background, and once do
		//in back ground is completed, the onPost execture method will be called.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog.setMessage("Downloading events...");
            Dialog.show();
        }

		@Override
		protected String doInBackground(String... urls) 
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://www.down-the-street.com/mobilesearch.php");
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("place", placePostText));
			nameValuePairs.add(new BasicNameValuePair("starttime", fromTimePostText));
			nameValuePairs.add(new BasicNameValuePair("endtime", toTimePostText));
			nameValuePairs.add(new BasicNameValuePair("foodbox", typeFood));
			nameValuePairs.add(new BasicNameValuePair("drinksbox", typeDrinks));
			nameValuePairs.add(new BasicNameValuePair("musicbox", typeMusic));
			nameValuePairs.add(new BasicNameValuePair("otherbox", typeOther));
			nameValuePairs.add(new BasicNameValuePair("keyword", keywordDefaultString));
			try
			{
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity);
				opm = result; 
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			}
            return null;
		}

        protected void onPostExecute(String file_url) {
        	Dialog.dismiss();
            
            if (Error != null) {
                 
                uiUpdate.setText("Output : "+Error);
                 
            } else {
            	savePreferences("phpString", opm);
            	savePreferences("placeSpinnerPosition", placeDefaultString);
            	savePreferences("fromTimeSpinnerPosition", fromTimeDefaultString);
            	savePreferences("toTimeSpinnerPosition", toTimeDefaultString);
            	savePreferences("defaultKeyword", keywordDefaultString);
            	savePreferences("box0check", checkbox0DefaultString);
            	savePreferences("box1check", checkbox1DefaultString);
            	savePreferences("box2check", checkbox2DefaultString);
            	savePreferences("box3check", checkbox3DefaultString);
            	Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            	intent.putExtra(EXTRA_MESSAGE, opm);
            	startActivity(intent);
             }
        }

	}
    private void savePreferences(String key, String opm2)
    {
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	Editor editor = sharedPreferences.edit();editor.putString(key, opm2);
    	editor.commit();
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
