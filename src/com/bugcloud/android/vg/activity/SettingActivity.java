package com.bugcloud.android.vg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.share.Constants;

public class SettingActivity extends BaseActivity {
	private static final int MAX_SEEKBAR_VALUE = 125;
	private SeekBar seekBarRed;
	private SeekBar seekBarGreen;
	private SeekBar seekBarBlue;
	private SeekBar seekBarMax;
	private SeekBar seekBarMin;
	private CheckBox checkboxNeedMoreGlitch;
	
	private int mSeekBarValueRed;
	private int mSeekBarValueGreen;
	private int mSeekBarValueBlue;
	private int mSeekBarValueMax;
	private int mSeekBarValueMin;
	private boolean mNeedMoreGlitch;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        
        seekBarRed = (SeekBar) findViewById(R.id.seekBarRangeRed);
        seekBarGreen = (SeekBar) findViewById(R.id.seekBarRangeGreen);
        seekBarBlue = (SeekBar) findViewById(R.id.seekBarRangeBlue);
        seekBarMax = (SeekBar) findViewById(R.id.seekBarMaxValue);
        seekBarMin = (SeekBar) findViewById(R.id.seekBarMinValue);
        checkboxNeedMoreGlitch = (CheckBox) findViewById(R.id.checkboxNeedMoreGlitch);
        
        mSeekBarValueRed = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED);
        mSeekBarValueGreen = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN);
        mSeekBarValueBlue = getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE);
        mSeekBarValueMax = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE);
        mSeekBarValueMin = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE);
        mNeedMoreGlitch = getBooleanSharedPreferences(Constants.KEY_NAME_NEED_MORE_GLITCH);
        
        seekBarRed.setMax(MAX_SEEKBAR_VALUE);
        seekBarGreen.setMax(MAX_SEEKBAR_VALUE);
        seekBarBlue.setMax(MAX_SEEKBAR_VALUE);
        seekBarMax.setMax(255);
        seekBarMin.setMax(255);
        seekBarRed.setProgress(mSeekBarValueRed);
        seekBarGreen.setProgress(mSeekBarValueGreen);
        seekBarBlue.setProgress(mSeekBarValueBlue);
        seekBarMax.setProgress(mSeekBarValueMax);
        seekBarMin.setProgress(mSeekBarValueMin);
        checkboxNeedMoreGlitch.setChecked(mNeedMoreGlitch);
        
        setSeekBarListeners();
        
        ImageButton btnSave = (ImageButton) findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED, mSeekBarValueRed);
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN, mSeekBarValueGreen);
				putIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE, mSeekBarValueBlue);
				putIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE, mSeekBarValueMax);
				putIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE, mSeekBarValueMin);
				putBooleanSharedPreferences(Constants.KEY_NAME_NEED_MORE_GLITCH, checkboxNeedMoreGlitch.isChecked());
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
		
		seekBarMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarValueMax = progress;
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
		
		seekBarMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mSeekBarValueMin = progress;
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
