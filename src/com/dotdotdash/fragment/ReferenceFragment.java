package com.dotdotdash.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dotdotdash.R;

public class ReferenceFragment extends ListFragment {
	
	private String[] ref;
	
    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)  {
    	
    	// Get list of resources
    	ref = getResources().getStringArray(R.array.reference);
    	
    	// Set this view with the array
    	setListAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.list_item, R.id.label, ref));
    	
    	return super.onCreateView(inflater, container, savedInstanceState);
    }
}
