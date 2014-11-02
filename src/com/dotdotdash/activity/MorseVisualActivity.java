package com.dotdotdash.activity;

import com.dotdotdash.R;
import com.dotdotdash.R.layout;

import android.app.Activity;
import android.os.Bundle;

public class MorseVisualActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.morse_visual);
    }
}
