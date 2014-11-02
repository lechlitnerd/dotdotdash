package com.dotdotdash.activity;

import com.dotdotdash.R;
import com.dotdotdash.R.id;
import com.dotdotdash.R.layout;
import com.dotdotdash.manager.TranslateManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MorseToTextActivity extends Activity {
	
	private Button mTranslate;
	private Button mDot;
	private Button mDash;
	private Button mSpace;
	private Button mNewWord;
	private EditText mEditText;
	private TextView mTextView;
	
	private TranslateManager mTranslateManager;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.fragment_morse_to_text);
	    
	    mTranslateManager = new TranslateManager();
	    
	    //UI Elements
	    mTranslate = (Button) findViewById(R.id.translate);
	    mDot = (Button) findViewById(R.id.dot);
	    mDash = (Button) findViewById(R.id.dash);
	    mSpace = (Button) findViewById(R.id.space);
        mEditText = (EditText) findViewById(R.id.edittext_input);
        mTextView = (TextView) findViewById(R.id.translation);
	    
        //Button Clicks
	    mTranslate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (mEditText.getText().length() > 0)
					{
						String s = mTranslateManager.morseToText(mEditText.getText().toString());
						mTextView.setText(s);
						mEditText.setText("");
					}
				} catch (Exception e) {
					Log.i("ERROR", e.getMessage());
				}
			}
        });
	    
	    mDot.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String s = mEditText.getText().toString();
					s = s.concat(".");
					mEditText.setText(s);
				} catch (Exception e) {
					Log.i("ERROR", e.getMessage());
				}
			}
        });
	    
	    mDash.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String s = mEditText.getText().toString();
					s = s.concat("-");
					mEditText.setText(s);
				} catch (Exception e) {
					Log.i("ERROR", e.getMessage());
				}
			}
        });
	    
	    mSpace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					String s = mEditText.getText().toString();
					s = s.concat(" ");
					mEditText.setText(s);
				} catch (Exception e) {
					Log.i("ERROR", e.getMessage());
				}
			}
        });
    }
}
