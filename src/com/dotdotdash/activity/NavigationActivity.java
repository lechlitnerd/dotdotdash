package com.dotdotdash.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dotdotdash.R;
import com.dotdotdash.constant.GlobalConstants;
import com.dotdotdash.fragment.ReferenceFragment;
import com.dotdotdash.fragment.SettingsFragment;
import com.dotdotdash.fragment.TextToMorseFragment;

/**
 * @author benlechlitner
 * 
 */
public class NavigationActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;

	private String mTitle;
	private String[] mNavItems;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			replaceFragment(new SettingsFragment());
			return true;
		case R.id.action_about:
			Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) this.findViewById(R.id.left_drawer);
		mNavItems = this.getResources().getStringArray(R.array.nav_values);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mNavItems));

		// Setup the Drawer Toggle listener
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(GlobalConstants.APP_NAME);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle listener on the drawer
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onNavItemSelected(position);
			}
		});

		// Allow users to open and close the nav drawer by touching the home
		// icon
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		onNavItemSelected(0);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title.toString();
		getActionBar().setTitle(mTitle);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void onNavItemSelected(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		Fragment fragment = null;

		switch (position) {
		// Text to Morse
		case 0:
			fragment = new TextToMorseFragment();
			break;
		case 1:
			fragment = new ReferenceFragment();
			break;
		case 2:
			fragment = new SettingsFragment();
			break;
		}
		
		replaceFragment(fragment);

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mNavItems[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	private void replaceFragment(Fragment f)
	{
		// Insert the fragment by replacing any existing fragment
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, f).commit();
	}
}
