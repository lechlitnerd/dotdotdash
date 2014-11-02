package com.dotdotdash.model;

public class Translation {

	private long mId;
	private String mSource;
	private String mTranslation;
	
	public Translation(long id, String source, String translation)
	{
		this.mId = id;
		this.mSource = source;
		this.mTranslation = translation;
	}
	
	public Translation(String source, String translation)
	{
		this.mId = -1;
		this.mSource = source;
		this.mTranslation = translation;
	}
	
	public long getId() {
		return mId;
	}

	public String getSource() {
		return mSource;
	}

	public String getTranslation() {
		return mTranslation;
	}
	
	
}
