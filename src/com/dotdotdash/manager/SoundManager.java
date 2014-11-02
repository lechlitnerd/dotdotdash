package com.dotdotdash.manager;

import java.util.HashMap;

import android.content.Context;
import android.media.MediaPlayer;

import com.dotdotdash.R;

public class SoundManager {

	private HashMap<Integer, Integer> mSoundPoolMap;
	private Context mContext;
	private MediaPlayer mPlayer;
	
	public SoundManager()
	{
		
	}
	
	public int getRes(int index)
	{
		return mSoundPoolMap.get(index);
	}
	
	public void initSounds(Context theContext) { 
		 mContext = theContext;
	     mSoundPoolMap = new HashMap<Integer, Integer>(); 

	     mPlayer = new MediaPlayer();
	     
	     //Add sounds in the raw folder
	     mSoundPoolMap.put(0, R.raw.tone);
	     mSoundPoolMap.put(1, R.raw.blah);
	     mSoundPoolMap.put(2, R.raw.dolphin);
	     
	     //Create a new sound in the player
	     mPlayer = MediaPlayer.create(mContext, mSoundPoolMap.get(0));
	} 
	
	public void addSound(int Index, int SoundID)
	{
		mSoundPoolMap.put(Index, SoundID);
	}
	
	public void createSound(int index) {
		mPlayer.release();
		mPlayer = MediaPlayer.create(mContext, mSoundPoolMap.get(index));
	}
	
	public void playSound(int index) { 
		if (mPlayer.isPlaying()) { mPlayer.stop(); }
		mPlayer.release();
		mPlayer = MediaPlayer.create(mContext, mSoundPoolMap.get(index));
		mPlayer.setLooping(true);
		mPlayer.start();
	}
	
	public void stopSound() {
		mPlayer.stop();
	}
	
	public boolean isPlaying() {
		return mPlayer.isPlaying();
	}
}
