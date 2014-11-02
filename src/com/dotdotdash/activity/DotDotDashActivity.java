package com.dotdotdash.activity;

import com.dotdotdash.R;
import com.dotdotdash.R.drawable;
import com.dotdotdash.R.id;
import com.dotdotdash.R.layout;
import com.dotdotdash.R.menu;
import com.dotdotdash.R.string;
import com.dotdotdash.constant.GlobalConstants;
import com.dotdotdash.fragment.SettingsFragment;
import com.dotdotdash.fragment.ReferenceFragment;
import com.dotdotdash.fragment.TextToMorseFragment;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class DotDotDashActivity extends TabActivity {
	
	// Preferences
	private boolean mAudioPref;
	private boolean mVisualPref;
	private boolean mVibratePref;
	private int mTone;
	private int mInterval;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TextToMorseFragment.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("t2m").setIndicator("Text to Morse",
                          res.getDrawable(R.drawable.ic_tab_t2m))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MorseToTextActivity.class);
        spec = tabHost.newTabSpec("m2t").setIndicator("Morse to Text",
                res.getDrawable(R.drawable.ic_tab_t2m))
            .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, ReferenceFragment.class);
        spec = tabHost.newTabSpec("ref").setIndicator("Reference",
                res.getDrawable(R.drawable.ic_tab_t2m))
            .setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0); 
    }
    
    @Override 
    public void onResume() { 
    	super.onResume(); 
    	setPreferences();
    } 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.action_settings:
            showPreferences();
            return true;
        case R.id.action_about:
            showInfo();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void setPreferences()
    {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
    	//Set Preferences
    	mAudioPref = prefs.getBoolean(GlobalConstants.PREF_AUDIO, true);
    	mVisualPref = prefs.getBoolean(GlobalConstants.PREF_VISUAL, true);
    	mVibratePref = prefs.getBoolean(GlobalConstants.PREF_VIBRATE, false);
    	mInterval = Integer.parseInt(prefs.getString(GlobalConstants.PREF_INTERVAL, "100"));
    	mTone = Integer.parseInt(prefs.getString(GlobalConstants.PREF_TONE, "0"));
    }
    
    private void showInfo()
    {
    	try {
            // Display welcome message
            	AlertDialog.Builder builder;
            	AlertDialog alertDialog;

            	LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            	View layout = inflater.inflate(R.layout.info_dialog,
            	                               (ViewGroup) findViewById(R.id.layout_root));


            	ImageView image = (ImageView) layout.findViewById(R.id.image_greeting);
            	image.setImageResource(R.drawable.monster_icon);
            	TextView text = (TextView) layout.findViewById(R.id.text_greeting);
            	text.setText(R.string.info);

            	builder = new AlertDialog.Builder(this);
            	builder.setView(layout);
            	builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                   }
               });
            	alertDialog = builder.create();
            	alertDialog.show();
            }
            catch (Exception e) {
            	Log.i("WELCOME ERORR", e.getMessage());
            }
    }
    
    private void showPreferences()
    {
    	startActivity(new Intent(this, SettingsFragment.class)); 
    }
}