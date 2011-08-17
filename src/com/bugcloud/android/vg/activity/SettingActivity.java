package com.bugcloud.android.vg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.share.Constants;

public class SettingActivity extends BaseActivity {
	private static final int MAX_SEEKBAR_VALUE = 125;
	private SeekBar seekBarRed;
	private SeekBar seekBarGreen;
	private SeekBar seekBarBlue;
	
	private int mSeekBarValueRed;
	private int mSeekBarValueGreen;
	private int mSeekBarValueBlue;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        
        seekBarRed = (SeekBar) findViewById(R.id.seekBarRangeRed);
        seekBarGreen = (SeekBar) findViewById(R.id.seekBarRangeGreen);
        seekBarBlue = (SeekBar) findViewById(R.id.seekBarRangeBlue);
        
        mSeekBarValueRed = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED);
        mSeekBarValueGreen = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN);
        mSeekBarValueBlue = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE);
        
        seekBarRed.setMax(MAX_SEEKBAR_VALUE);
        seekBarGreen.setMax(MAX_SEEKBAR_VALUE);
        seekBarBlue.setMax(MAX_SEEKBAR_VALUE);
        seekBarRed.setProgress(mSeekBarValueRed);
        seekBarGreen.setProgress(mSeekBarValueGreen);
        seekBarBlue.setProgress(mSeekBarValueBlue);
        
        setSeekBarListeners();
        
        ImageButton btnSave = (ImageButton) findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED, mSeekBarValueRed);
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN, mSeekBarValueGreen);
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE, mSeekBarValueBlue);
				backToRoot();
			}
		});
    }
	
	private void setSeekBarListeners() {
		seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarValueRed = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        	
        });
		
		seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarValueGreen = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        	
        });
		
		seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarValueBlue = progress;
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
        	
        });
	}
}
