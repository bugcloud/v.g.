package com.bugcloud.android.vg.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.share.Constants;

public class SettingActivity extends BaseActivity {
	private SeekBar seekBarMax;
	private SeekBar seekBarMin;
	private Spinner mSpinner;
	private CheckBox checkboxNeedGlitch;
	
	private int mSeekBarValueMax;
	private int mSeekBarValueMin;
	private boolean mNeedMoreGlitch;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        
        seekBarMax = (SeekBar) findViewById(R.id.seekBarMaxValue);
        seekBarMin = (SeekBar) findViewById(R.id.seekBarMinValue);
        mSpinner = (Spinner) findViewById(R.id.spinnerCharset);
        checkboxNeedGlitch = (CheckBox) findViewById(R.id.checkboxNeedGlitch);
        
        mSeekBarValueMax = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE);
        mSeekBarValueMin = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE);
        mNeedMoreGlitch = getBooleanSharedPreferences(Constants.KEY_NAME_NEED_GLITCH);
        
        seekBarMax.setMax(255);
        seekBarMin.setMax(255);
        seekBarMax.setProgress(mSeekBarValueMax);
        seekBarMin.setProgress(mSeekBarValueMin);
        checkboxNeedGlitch.setChecked(mNeedMoreGlitch);
        
        String charset = getStringSharedPreferences(Constants.KEY_NAME_CHARSET);
        if (charset == null) charset = "ISO-8859-1";
        String[] charsets = getResources().getStringArray(R.array.charsets);
        int i = 0;
        for (String c : charsets) {
        	if (c.equals(charset)) break;
        	i++;
        }
        mSpinner.setSelection(i);
        
        setSeekBarListeners();
        
        ImageButton btnSave = (ImageButton) findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				putStringSharedPreferences(Constants.KEY_NAME_CHARSET, mSpinner.getSelectedItem().toString());
				putIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE, mSeekBarValueMax);
				putIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE, mSeekBarValueMin);
				putBooleanSharedPreferences(Constants.KEY_NAME_NEED_GLITCH, checkboxNeedGlitch.isChecked());
				backToRoot();
			}
		});
    }
	
	private void setSeekBarListeners() {
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
