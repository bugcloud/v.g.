package com.bugcloud.android.vg.activity;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.camera.CameraView;
import com.bugcloud.android.vg.share.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class TopActivity extends BaseActivity {
	public static int redRange;
	public static int greenRange;
	public static int blueRange;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new CameraView(this));
        showToast(getString(R.string.message_intro));
    }
    
    @Override
	public void onStart() {
		super.onStart();
		
		redRange = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED);
        greenRange = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN);
        blueRange = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE);
    }
    
 // Add settings to menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_top, menu);
		return true;
	}
	
	// Menu actions
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuTopSetting:
			Intent i = new Intent(getApplicationContext(), SettingActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;
		}
		return false;

	}
}