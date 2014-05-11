package com.example.reporter;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Build;
import android.widget.CheckBox;

public class FlashPhase3Activity extends ActionBarActivity implements OnCheckedChangeListener, OnClickListener {
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.activity_flash_phase3);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		bundle = getIntent().getExtras();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phase3, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_flash_phase3,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		String search = ((CheckBox) findViewById(R.id.checkBoxSearch)).isChecked() ? "1" : "0";
		String evacuation = ((CheckBox) findViewById(R.id.checkBoxEvacuation)).isChecked() ? "1" : "0";
		String protection = ((CheckBox) findViewById(R.id.checkBoxProtection)).isChecked() ? "1" : "0";
		String medicine = ((CheckBox) findViewById(R.id.checkBoxMedicine)).isChecked() ? "1" : "0";
		String shelter = ((CheckBox) findViewById(R.id.checkBoxShelter)).isChecked() ? "1" : "0";
		String food = ((CheckBox) findViewById(R.id.checkBoxFood)).isChecked() ? "1" : "0";
		String water = ((CheckBox) findViewById(R.id.checkBoxWater)).isChecked() ? "1" : "0";
		String sanitation = ((CheckBox) findViewById(R.id.checkBoxSanitation)).isChecked() ? "1" : "0";
		String repair = ((CheckBox) findViewById(R.id.checkBoxRepair)).isChecked() ? "1" : "0";
		
		
		//bundle.putString("phase3", "|" + search + "|" + evacuation + "|" + protection + "|" + medicine + "|" + shelter + "|" + food + "|" + water + "|" + sanitation + "|" + repair);
		
		// getting gps
		String stringLatitude = "0.0";
		String stringLongitude = "0.0";
		
		GPSTracker gpsTracker = new GPSTracker(this);
		if (gpsTracker.canGetLocation())
        {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            
        }
		
		String phase1 = bundle.getString("phase1")+"|"+stringLatitude+","+stringLongitude;
		String phase2 = bundle.getString("phase2");
		String phase3 = "|" + search + "|" + evacuation + "|" + protection + "|" + medicine + "|" + shelter + "|" + food + "|" + water + "|" + sanitation + "|" + repair;
		String phoneNo = "+639167343602";
    	String message = phase1 + phase2 + phase3; 
    	Log.d("text", message);
    	
    	// check if there are values for both
        if (phoneNo.length()>0 && message.length()>0)                
            sendSMS(phoneNo, message);                
        else
        	Toast.makeText(getBaseContext(), 
                "Please enter both phone number and message.", 
                Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(this, SubmitActivity.class);
		//intent.putExtras(bundle);
		startActivity(intent);
	}

	private void sendSMS(String phoneNumber, String message) {      
    	finish();
    	
    	// this will re-open the Sms1Activity upon completion
        PendingIntent pi = PendingIntent.getActivity(this, 
        											 0,
                									 new Intent(this, MainActivity.class), 
                									 0);     
        
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, 
        				    null, 			// null == use default SMSC
        				    message, 
        				    pi, 			// sent intent
        				    null);        
    }
}
