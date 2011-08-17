package com.bugcloud.android.vg.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.activity.BaseActivity;
import com.bugcloud.android.vg.share.Common;
import com.bugcloud.android.vg.share.Constants;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.MotionEvent;

public class CameraView extends CameraViewBase {
	private static final String TAG = "Vg::CameraView";
	private int mRedRange;
	private int mGreenRange;
	private int mBlueRange;
	private int mMinColorValue;
	private int mMaxColorValue;

	private Context mContext;
	private byte[] mBitmapBytes;
	
	public CameraView(Context context) {
		super(context);
		mContext = context;
		mRedRange = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_RED);
		mGreenRange = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_GREEN);
		mBlueRange = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_RANGE_OF_BLUE);
		mMinColorValue = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE);
		mMaxColorValue = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE);
	}

	@Override
	protected Bitmap processFrame(byte[] data) {
		int frameSize = getFrameWidth() * getFrameHeight();
        int[] rgba = new int[frameSize];

        for (int i = 0; i < getFrameHeight(); i++)
            for (int j = 0; j < getFrameWidth(); j++) {
                int y = (0xff & ((int) data[i * getFrameWidth() + j]));
                int u = (0xff & ((int) data[frameSize + (i >> 1) * getFrameWidth() + (j & ~1) + 0]));
                int v = (0xff & ((int) data[frameSize + (i >> 1) * getFrameWidth() + (j & ~1) + 1]));
                y = y < 16 ? 16 : y;

                int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
                int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
                int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));

                r = r < 0 ? 0 : (r > 255 ? 255 : r);
                g = g < 0 ? 0 : (g > 255 ? 255 : g);
                b = b < 0 ? 0 : (b > 255 ? 255 : b);
                
                r = (r > (255/2-mRedRange) && r < (255/2+mRedRange))? Common.getRandom(mMinColorValue, mMaxColorValue) : r;
                g = (g > (255/2-mGreenRange) && g < (255/2+mGreenRange))? Common.getRandom(mMinColorValue, mMaxColorValue) : g;
                b = (b > (255/2-mBlueRange) && b < (255/2+mBlueRange))? Common.getRandom(mMinColorValue, mMaxColorValue) : b;

                rgba[i * getFrameWidth() + j] = 0xff000000 + (b << 16) + (g << 8) + r;
            }

        Bitmap bmp = Bitmap.createBitmap(getFrameWidth(), getFrameHeight(), Bitmap.Config.ARGB_8888);
        bmp.setPixels(rgba, 0/* offset */, getFrameWidth() /* stride */, 0, 0, getFrameWidth(), getFrameHeight());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 100, bos);
        mBitmapBytes = bos.toByteArray();
        return bmp;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		if(me.getAction() == MotionEvent.ACTION_DOWN) {
			if (mBitmapBytes != null) {
				saveBitmapAsJPGImage(mBitmapBytes);
				((BaseActivity)mContext).showToast(mContext.getString(R.string.message_picture_saved));
			} else {
				failedMessage();
			}
		}
		return true;
	}
	
	private void saveBitmapAsJPGImage(byte[] bytes) {
		String path = Environment.getExternalStorageDirectory().toString() + "/" + mContext.getPackageName();
		File dir = new File(path);
		dir.mkdir();
		Date today = new Date();
		SimpleDateFormat sdFormat= new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
		String name = sdFormat.format(today);
		String fileName = name + ".jpg";
		File file = new File(path, fileName);
		FileOutputStream fos = null;
		try {
			 if (file.createNewFile()) {
				 fos = new FileOutputStream(file);
				 fos.write(bytes);
				 fos.close();
			 }
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.getMessage());
			failedMessage();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			failedMessage();
		}
		
		//insert into MediaStore
		long now = System.currentTimeMillis();
		ContentResolver cr = mContext.getContentResolver();
		ContentValues values = new ContentValues(7);
		values.put(Images.Media.TITLE, name);
		values.put(Images.Media.DISPLAY_NAME, name);
		values.put(Images.Media.DATE_TAKEN, now);
		values.put(Images.Media.DATE_MODIFIED, now/1000);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.DATA, file.getPath());
		values.put(Images.Media.SIZE, file.length());
		cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	private void failedMessage() {
		((BaseActivity)mContext).showToast(mContext.getString(R.string.message_picture_not_saved));
	}
}
