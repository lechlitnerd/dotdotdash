package com.dotdotdash.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.dotdotdash.R;

public class SettingsFragment extends PreferenceFragment {
	
	SwitchPreference seek;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		addPreferencesFromResource(R.xml.preferences);
	}
}
