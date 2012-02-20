package com.bugcloud.android.vg.activity;

import java.util.Map;

import com.bugcloud.android.vg.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity {	
	public void centeringWindowTitle() {
		ViewGroup decorView= (ViewGroup) getWindow().getDecorView();
		LinearLayout root= (LinearLayout) decorView.getChildAt(0);
		FrameLayout titleContainer= (FrameLayout) root.getChildAt(0);
		TextView title= (TextView) titleContainer.getChildAt(0);
		title.setGravity(Gravity.CENTER);
	}
	
	public void backToRoot() {
		Intent i = new Intent(getApplicationContext(), TopActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public void showToast(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show(); 
	}
	
	public void showShortToast(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_SHORT).show(); 
	}
	
	public Map<String, ?> getAllSharedPreferences() {
		return this.getSP().getAll();
	}
	
	public boolean getBooleanSharedPreferences(String key, boolean defValue) {
		return this.getSP().getBoolean(key, defValue);
	}
	
	public boolean getBooleanSharedPreferences(String key) {
		return this.getBooleanSharedPreferences(key, false);
	}
	
	public float getFloatSharedPreferences(String key, float defValue) {
		return this.getSP().getFloat(key, defValue);
	}
	
	public float getFloatSharedPreferences(String key) {
		return this.getFloatSharedPreferences(key, 0);
	}
	
	public int getIntSharedPreferences(String key, int defValue) {
		return this.getSP().getInt(key, defValue);
	}
	
	public int getIntSharedPreferences(String key) {
		return this.getIntSharedPreferences(key, 0);
	}
	
	public long getLongSharedPreferences(String key, long defValue) {
		return this.getSP().getLong(key, defValue);
	}
	
	public long getLongSharedPreferences(String key) {
		return this.getLongSharedPreferences(key, 0);
	}
	
	public String getStringSharedPreferences(String key, String defValue) {
		return this.getSP().getString(key, defValue);
	}
	
	public String getStringSharedPreferences(String key) {
		return this.getStringSharedPreferences(key, null);
	}
	
	public void putBooleanSharedPreferences(String key, boolean val) {
		SharedPreferences.Editor editor = this.getSP().edit();
		editor.putBoolean(key, val);
		editor.commit();
	}
	
	public void putFloatSharedPreferences(String key, float val) {
		SharedPreferences.Editor editor = this.getSP().edit();
		editor.putFloat(key, val);
		editor.commit();
	}
	
	public void putIntSharedPreferences(String key, int val) {
		SharedPreferences.Editor editor = this.getSP().edit();
		editor.putInt(key, val);
		editor.commit();
	}
	
	public void putLongSharedPreferences(String key, long val) {
		SharedPreferences.Editor editor = this.getSP().edit();
		editor.putLong(key, val);
		editor.commit();
	}
	
	public void putStringSharedPreferences(String key, String val) {
		SharedPreferences.Editor editor = this.getSP().edit();
		editor.putString(key, val);
		editor.commit();
	}
	
	private SharedPreferences getSP() {
		return this.getSharedPreferences(getString(R.string.preference_key_name), Context.MODE_PRIVATE);
	}
}
