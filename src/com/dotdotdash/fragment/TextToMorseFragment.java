package com.dotdotdash.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dotdotdash.R;
import com.dotdotdash.manager.SoundManager;
import com.dotdotdash.manager.TranslateManager;
import com.dotdotdash.manager.db.TranslationDataSource;
import com.dotdotdash.model.Translation;
import com.dotdotdash.ui.MorseListAdapter;

public class TextToMorseFragment extends Fragment {

	private static final String TAG = "TextToMorseFragment";

	private Context mContext;

	private TextView mTextView;
	private ListView mRecentTranslations;
	private MorseListAdapter mRecentTransAdapter;
	private List<Translation> mTranslations;

	private TranslationDataSource mTransDataSource;
	private TranslateManager mTranslateManager;
	private SoundManager mSoundManager;
	private Vibrator mVibrator;

	// Morse Code result iteration
	private String mResult;
	private boolean mShow;
	private Thread mWorker;

	// Preferences
	private boolean mAudioPref;
	private boolean mVisualPref;
	private boolean mVibratePref;
	private int mTone;
	private int mInterval;

	// Animations
	Animation mPulseAnim = null;
	Animation pulse_wave = null;
	AnimationDrawable pulse_wave_frames = null;

	// Need handler for callbacks to the UI thread
	final Handler mHandler = new Handler();

	public TextToMorseFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		View content = inflater.inflate(R.layout.fragment_text_to_morse, null);
		mContext = this.getActivity();

		// Import Preferences
		setPreferences();

		// Gain access to the database which holds our history of translations
		// by the user
		mTransDataSource = new TranslationDataSource(mContext);
		mTransDataSource.open();

		// Change the volume control for this activity to adjust the Media
		// Volume
		this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Create a new TranslateManager. This performs the translation from
		// text to Morse code
		mTranslateManager = new TranslateManager();

		// Create a new SoundManager. This performs the task of playing sounds
		// when needed
		mSoundManager = new SoundManager();
		mSoundManager.initSounds(this.getActivity());

		// Create an instance of the vibrator
		mVibrator = (Vibrator) mContext
				.getSystemService(Context.VIBRATOR_SERVICE);

		// Morse Code visual members
		mShow = false;
		mWorker = new Thread(null, mDoBothMorseCode, "Background");

		// Grab UI Views
		final EditText input = (EditText) content
				.findViewById(R.id.edittext_input);
		mRecentTranslations = (ListView) content
				.findViewById(R.id.recent_translations);
		mTextView = (TextView) content.findViewById(R.id.translation);
		// mMorseCodeShow = (ImageView) content
		// .findViewById(R.id.morse_code_image);

		mTranslations = mTransDataSource.getAllTranslations();
		mRecentTransAdapter = new MorseListAdapter(mContext, mTranslations);
		mRecentTranslations.setAdapter(mRecentTransAdapter);

		mRecentTranslations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mResult = ((Translation) mRecentTransAdapter.getItem(position))
						.getTranslation();
				
				broadcastResult();
			}
		});

		content.findViewById(R.id.translate).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						try {

							final String source = input.getText().toString();

							InputMethodManager imm = (InputMethodManager) mContext
									.getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									input.getApplicationWindowToken(), 0);

							translate(source);

						} catch (Exception e) {
							Log.i("ERROR Thread", e.getMessage());
						}
					}
				});

		// mMorseCodeShow.setOnTouchListener(new OnTouchListener() {
		// public boolean onTouch(View v, MotionEvent e) {
		// if (!mWorker.isAlive()) {
		// int event = e.getAction();
		// switch (event) {
		// case MotionEvent.ACTION_DOWN:
		// if (!mSoundManager.isPlaying()) {
		// mMorseCodeShow.startAnimation(mPulseAnim);
		// }
		// mSoundManager.playSound(mTone);
		// break;
		// case MotionEvent.ACTION_UP:
		// mSoundManager.stopSound();
		// break;
		// }
		// } else if (mWorker.isAlive()) {
		// mWorker.interrupt();
		// mSoundManager.stopSound();
		// Toast.makeText(mContext, "Stopping", Toast.LENGTH_SHORT)
		// .show();
		// }
		// return true;
		// }
		// });

		return content;
	}

	@Override
	public void onPause() {
		super.onPause();
		mSoundManager.stopSound();
		mTransDataSource.close();
	}

	@Override
	public void onResume() {
		super.onResume();
		setPreferences();
		mTransDataSource.open();
	}

	private void translate(String text) {
		if (text.length() > 0) {

			// Translate the text into Morse Code
			mResult = mTranslateManager.textToMorse(text);

			// Add it to the database and update the ListView
			mRecentTransAdapter.addTranslation(mTransDataSource
					.createTranslation(text, mResult));

			broadcastResult();
		}
	}

	private void broadcastResult() {
		if (mWorker.isAlive()) {
			mWorker.interrupt();
		}

		mWorker = new Thread(null, mDoBothMorseCode, "Background");
		mWorker.start();
	}

	private void setPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);

		// Set Preferences
		mAudioPref = prefs.getBoolean("Audio", true);
		mVisualPref = prefs.getBoolean("Visual", true);
		mVibratePref = prefs.getBoolean("Vibrate", false);
		mInterval = Integer.parseInt(prefs.getString("Interval", "100"));
		if (mPulseAnim != null) {
			mPulseAnim.setDuration(Long.parseLong(prefs.getString("Interval",
					"100")));
		}
		if (pulse_wave != null) {
			pulse_wave.setDuration(Long.parseLong(prefs.getString("Interval",
					"100")));
		}
		mTone = Integer.parseInt(prefs.getString("Tones", "0"));
	}

	// Create runnable for update both sound and visual
	final Runnable mUpdateBoth = new Runnable() {
		public void run() {
			updateAudioVisual();
		}
	};

	// Create runnable to update the TextView display
	final Runnable mUpdateTextView = new Runnable() {
		public void run() {
			update_text_ui();
		}
	};

	// Create runnable to display both visual and audio indicators of Morse code
	// translation
	private Runnable mDoBothMorseCode = new Runnable() {
		public void run() {
			try {
				if (!mResult.equals("")) {
					mHandler.post(mUpdateTextView);
					mShow = false;
					// pulse_wave_frames.setOneShot(false);
					// pulse_wave_frames.setVisible(true, true);
					transmitAudioVisual(mResult);
					// pulse_wave_frames.setOneShot(true);

				}
			} catch (Exception e) {
				Log.i("doBothMorseCode", e.getMessage());
			}
		}
	};

	// Update the text to show the translation of the text in dits and dats
	private void update_text_ui() {
		mTextView.setText(mResult);
	}

	private void updateAudioVisual() {
		if (mShow) {
			// if (mVisualPref) {
			// mMorseCodeShow.startAnimation(mPulseAnim);
			// }
			if (mAudioPref) {
				mSoundManager.playSound(mTone);
			}
			if (mVibratePref) {
				mVibrator.vibrate(mInterval);
			}
		} else {
			if (mAudioPref) {
				mSoundManager.stopSound();
			}
			if (mVibratePref) {
				mVibrator.cancel();
			}
		}
	}

	private void transmitAudioVisual(String text) {
		
		Log.d(TextToMorseFragment.class.getName(), "Transmitting: " + text);
		
		int i = 0;
		String character = "";
		try {
			for (i = 0; i < text.length(); i++) {
				character = text.subSequence(i, i + 1).toString();
				if (character.equals(" ")) {
					Log.d(TextToMorseFragment.class.getName(), "space");
					mShow = false;
					mHandler.post(mUpdateBoth);
					Thread.sleep(mInterval * 7);
				} else if (character.equals(".")) {
					Log.d(TextToMorseFragment.class.getName(), "dot");
					mShow = true;
					mHandler.post(mUpdateBoth);
					Thread.sleep(mInterval);
					mShow = false;
					mHandler.post(mUpdateBoth);
					Thread.sleep(mInterval * 3);
				} else if (character.equals("-")) {
					Log.d(TextToMorseFragment.class.getName(), "dot");
					mShow = true;
					mHandler.post(mUpdateBoth);
					Thread.sleep(mInterval * 3);

					mShow = false;
					mHandler.post(mUpdateBoth);
					Thread.sleep(mInterval * 3);
				}
			}

			mShow = false;
			mHandler.post(mUpdateBoth);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
}
