package com.bugcloud.android.vg.activity;

import com.bugcloud.android.vg.camera.CameraView;

import android.os.Bundle;
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
        
        redRange = 100;
        greenRange = 100;
        blueRange = 100;
    }
    
    @Override
	public void onStart() {
		super.onStart();
    }
}