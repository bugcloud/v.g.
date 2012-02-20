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
    private CheckBox checkboxLaughingMan;
    
    private int mSeekBarValueMax;
    private int mSeekBarValueMin;
    private boolean mLaughingManGlitch;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        
        seekBarMax = (SeekBar) findViewById(R.id.seekBarMaxValue);
        seekBarMin = (SeekBar) findViewById(R.id.seekBarMinValue);
        mSpinner = (Spinner) findViewById(R.id.spinnerLevel);
        checkboxLaughingMan = (CheckBox) findViewById(R.id.checkboxLaughingMan);
        
        mSeekBarValueMax = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE);
        mSeekBarValueMin = getIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE);
        if (mSeekBarValueMax > 9) mSeekBarValueMax = 9;
        if (mSeekBarValueMin > 9) mSeekBarValueMin = 9;
        mLaughingManGlitch = getBooleanSharedPreferences(Constants.KEY_NAME_LAUGHING_MAN);
        
        seekBarMax.setMax(9);
        seekBarMin.setMax(9);
        seekBarMax.setProgress(mSeekBarValueMax);
        seekBarMin.setProgress(mSeekBarValueMin);
        checkboxLaughingMan.setChecked(mLaughingManGlitch);
        
        int level = getIntSharedPreferences(Constants.KEY_NAME_GLITCH_LEVEL);
        String[] levels = getResources().getStringArray(R.array.levels);
        if (level == 0) level = 1;
        int i=0;
        for (String l : levels) {
            if (l.equals(String.valueOf(level))) break;
            i++;
        }
        mSpinner.setSelection(i);
        
        setSeekBarListeners();
        
        ImageButton btnSave = (ImageButton) findViewById(R.id.btnSaveSettings);
        btnSave.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                putStringSharedPreferences(Constants.KEY_NAME_GLITCH_LEVEL, mSpinner.getSelectedItem().toString());
                putIntSharedPreferences(Constants.KEY_NAME_GLITCH_LEVEL, (new Integer(mSpinner.getSelectedItem().toString())).intValue());
                putIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE, mSeekBarValueMax);
                putIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE, mSeekBarValueMin);
                putBooleanSharedPreferences(Constants.KEY_NAME_LAUGHING_MAN, checkboxLaughingMan.isChecked());
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
