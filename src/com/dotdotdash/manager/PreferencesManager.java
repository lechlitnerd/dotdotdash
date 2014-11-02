package com.dotdotdash.manager;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dotdotdash.constant.GlobalConstants;

public class PreferencesManager {
	
	public static void setAudioOn(Context context, boolean on)
	{
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(GlobalConstants.PREF_AUDIO, on);
	}
	
	public static boolean isAudioOn(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(GlobalConstants.PREF_AUDIO, true);
	}
	
	public static void setVisualOn(Context context, boolean on)
	{
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(GlobalConstants.PREF_VISUAL, on);
	}
	
	public static boolean isVisualOn(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(GlobalConstants.PREF_VISUAL, true);
	}
	
	public static void setVibrateOn(Context context, boolean on)
	{
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(GlobalConstants.PREF_VIBRATE, on);
	}
	
	public static boolean isVibrateOn(Context context)
	{
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(GlobalConstants.PREF_VIBRATE, true);
	}
}
