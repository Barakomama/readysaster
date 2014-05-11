package com.example.reporter;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.os.Build;

public class FlashPhase2Activity extends ActionBarActivity implements OnClickListener {
	private int dead = 0;
	private int injured = 0;
	private int missing = 0;
	private int shelter = 0;
	private int food = 0;
	private int water = 0;
	private int sanitation = 0;
	private int lifelines = 0;
	private TextView editTextDead;
	private TextView editTextInjured;
	private TextView editTextMissing;
	private TextView editTextShelter;
	private TextView editTextFood;
	private TextView editTextWater;
	private TextView editTextSanitation;
	private TextView editTextLifelines;
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
		setContentView(R.layout.activity_flash_phase2);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		bundle = getIntent().getExtras();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_phase2, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_flash_phase2,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		editTextDead = (TextView) findViewById(R.id.editTextDead);
		editTextDead = (TextView) findViewById(R.id.editTextDead);
		editTextInjured = (TextView) findViewById(R.id.editTextInjured);
		editTextMissing = (TextView) findViewById(R.id.editTextMissing);
		editTextShelter = (TextView) findViewById(R.id.editTextShelter);
		editTextFood = (TextView) findViewById(R.id.editTextFood);
		editTextWater = (TextView) findViewById(R.id.editTextWater);
		editTextSanitation = (TextView) findViewById(R.id.editTextSanitation);
		editTextLifelines = (TextView) findViewById(R.id.editTextLifelines);
		
		switch(v.getId()) {
			case R.id.buttonNext:
				Intent intent = new Intent(this, FlashPhase3Activity.class);
				if (bundle == null) {
					bundle = new Bundle();
				}
				
				String phase2 = "|" + dead + "|" + injured + "|" + missing + "|" + shelter + "|" + food + "|" + water + "|" + sanitation + "|" + lifelines;
				
				bundle.putString("phase2",phase2);
				
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.imageButtonLessDead:
				if (dead > 0) {
					dead--;
					editTextDead.setText(String.valueOf(dead), TextView.BufferType.EDITABLE);
				}
				break;
			case R.id.imageButtonLessInjured:
				if (injured > 0) {
					injured--;
					editTextInjured.setText(String.valueOf(injured));
				}
				break;
			case R.id.imageButtonLessMissing:
				if (missing > 0) {
					missing--;
					editTextMissing.setText(String.valueOf(missing));
				}
				break;
			case R.id.imageButtonLessShelter:
				if (shelter > 0) {
					shelter--;
					editTextShelter.setText(String.valueOf(shelter));
				}
				break;
			case R.id.imageButtonLessFood:
				if (food > 0) {
					editTextFood.setText(String.valueOf(food));
					food--;
				}
				break;
			case R.id.imageButtonLessWater:
				if (water > 0) {
					water--;
					editTextWater.setText(String.valueOf(water));
				}
				break;
			case R.id.imageButtonLessSanitation:
				if (sanitation > 0) {
					sanitation--;
					editTextSanitation.setText(String.valueOf(sanitation));
				}
				break;
			case R.id.imageButtonLessLifelines:
				if (lifelines > 0) {
					lifelines--;
					editTextLifelines.setText(String.valueOf(lifelines));
				}
				break;
			case R.id.imageButtonMoreDead:
				dead++;
				editTextDead.setText(String.valueOf(dead));
				break;
			case R.id.imageButtonMoreInjured:
				injured++;
				editTextInjured.setText(String.valueOf(injured));
				break;
			case R.id.imageButtonMoreMissing:
				missing++;
				editTextMissing.setText(String.valueOf(missing));
				break;
			case R.id.imageButtonMoreShelter:
				shelter++;
				editTextShelter.setText(String.valueOf(shelter));
				break;
			case R.id.imageButtonMoreFood:
				food++;
				editTextFood.setText(String.valueOf(food));
				break;
			case R.id.imageButtonMoreWater:
				water++;
				editTextWater.setText(String.valueOf(water));
				break;
			case R.id.imageButtonMoreSanitation:
				sanitation++;
				editTextSanitation.setText(String.valueOf(sanitation));
				break;
			case R.id.imageButtonMoreLifelines:
				lifelines++;
				editTextLifelines.setText(String.valueOf(lifelines));
				break;
		}
	}

}
