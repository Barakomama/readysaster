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
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import android.os.Build;

public class SubmitActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
		
		if (savedInstanceState == null) {
			savedInstanceState = getIntent().getExtras();
		}
		
		/*String phase1 = savedInstanceState.getString("phase1");
		String phase2 = savedInstanceState.getString("phase2");
		String phase3 = savedInstanceState.getString("phase3");
		
    	String message = phase1 + phase2 + phase3; 
    	Log.d("text", message);
    	
    	// check if there are values for both
        if (phoneNo.length()>0 && message.length()>0)                
            sendSMS(phoneNo, message);                
        else
        	Toast.makeText(getBaseContext(), 
                "Please enter both phone number and message.", 
                Toast.LENGTH_SHORT).show();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
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
