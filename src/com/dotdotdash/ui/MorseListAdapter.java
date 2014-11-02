package com.dotdotdash.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dotdotdash.R;
import com.dotdotdash.model.Translation;

public class MorseListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Translation> mTranslations;
	
	public MorseListAdapter(Context context, List<Translation> translations)
	{
		this.mInflater = (LayoutInflater) (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mTranslations = new ArrayList<Translation>(translations);
	}
	
	@Override
	public int getCount() {
		return mTranslations.size();
	}

	@Override
	public Object getItem(int position) {
		return mTranslations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private static class ViewHolder {
		TextView mmSource;
		TextView mmTranslation;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder vh = null;
		
		final Translation t = mTranslations.get(mTranslations.size() - position);
		
		if (convertView == null)
		{
			vh = new ViewHolder();
			
			convertView = (ViewGroup) mInflater.inflate(R.layout.list_item_morse, null);
			
			vh.mmSource = (TextView) convertView.findViewById(R.id.source);
			vh.mmTranslation = (TextView) convertView.findViewById(R.id.translation);
			
			convertView.setTag(vh);
		}
		else
		{
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.mmSource.setText(t.getSource());
		vh.mmTranslation.setText(t.getTranslation());
		
		return convertView;
	}

	public void addTranslation(Translation t)
	{
		mTranslations.add(t);
		this.notifyDataSetChanged();
	}
}
