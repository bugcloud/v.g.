package com.bugcloud.android.vg.activity;

import com.bugcloud.android.vg.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.splashscreen);
        
      //Display the current version number
        PackageManager pm = getPackageManager();
        try {
			PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
			TextView versionNumber = (TextView) findViewById(R.id.versionNumber);           
            versionNumber.setText("Version " + pi.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(SplashScreenActivity.this, TopActivity.class);
				SplashScreenActivity.this.startActivity(i);
				SplashScreenActivity.this.finish();
			}
        	
        }, 2900);
    }
}