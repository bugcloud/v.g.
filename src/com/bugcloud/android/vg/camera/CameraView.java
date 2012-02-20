package com.bugcloud.android.vg.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.bugcloud.android.vg.R;
import com.bugcloud.android.vg.activity.BaseActivity;
import com.bugcloud.android.vg.share.Common;
import com.bugcloud.android.vg.share.Constants;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class CameraView extends CameraViewBase {
    private static final String TAG = "Vg::CameraView";
    private int mLevel;
    private int mMinColorValue;
    private int mMaxColorValue;
    private boolean mNeedGlitch;

    private Context mContext;
    private byte[] mBitmapBytes;
    
    private Mat mYuv;
    private Mat mRgba;
    private Mat mGraySubmat;
    private Mat mIntermediateMat;
    
    public CameraView(Context context) {
        super(context);
        mContext = context;
        mLevel = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_GLITCH_LEVEL);
        if (mLevel == 0) mLevel = 1;
        mMinColorValue = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_COLOR_MIN_VALUE);
        mMaxColorValue = ((BaseActivity)mContext).getIntSharedPreferences(Constants.KEY_NAME_COLOR_MAX_VALUE);
        if (mMinColorValue > 9) mMinColorValue = 9;
        if (mMaxColorValue > 9) mMaxColorValue = 9;
        mNeedGlitch = ((BaseActivity)mContext).getBooleanSharedPreferences(Constants.KEY_NAME_NEED_GLITCH);
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder _holder, int format, int width, int height) {
        super.surfaceChanged(_holder, format, width, height);
        
        synchronized (this) {
            // initialize Mats before usage
            mYuv = new Mat(getFrameHeight() + getFrameHeight() / 2, getFrameWidth(), CvType.CV_8UC1);
            mGraySubmat = mYuv.submat(0, getFrameHeight(), 0, getFrameWidth());

            mRgba = new Mat();
            mIntermediateMat = new Mat();
        }
    }
    
    @Override
    protected Bitmap processFrame(byte[] data) {
        mYuv.put(0, 0, data);
        Imgproc.cvtColor(mYuv, mRgba, Imgproc.COLOR_YUV420sp2RGB, 4);
//        if (mNeedGlitch) {
//            FindFeatures(mGraySubmat.getNativeObjAddr(), mRgba.getNativeObjAddr());
//        }
        Bitmap bmp = Bitmap.createBitmap(getFrameWidth(), getFrameHeight(), Bitmap.Config.ARGB_8888);

        if (Utils.matToBitmap(mRgba, bmp)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.JPEG, 100, bos);
            mBitmapBytes = bos.toByteArray();
            
            if (mNeedGlitch && (mMinColorValue < mMaxColorValue)) {
//              int pad = "data:image/jpeg".getBytes().length;
                for (int i=0; i<mLevel; i++) {
                    mBitmapBytes[Common.getRandom(0, mBitmapBytes.length-1)] = (byte)Common.getRandom(mMinColorValue, mMaxColorValue);
                }
                bmp = BitmapFactory.decodeByteArray(mBitmapBytes, 0, mBitmapBytes.length);
            }
            
            return bmp;
        }

        bmp.recycle();
        return null;
    }
    
    @Override
    public void run() {
        super.run();

        synchronized (this) {
            // Explicitly deallocate Mats
            if (mYuv != null)
                mYuv.release();
            if (mRgba != null)
                mRgba.release();
            if (mGraySubmat != null)
                mGraySubmat.release();
            if (mIntermediateMat != null)
                mIntermediateMat.release();

            mYuv = null;
            mRgba = null;
            mGraySubmat = null;
            mIntermediateMat = null;
        }
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
        values.put(MediaColumns.TITLE, name);
        values.put(MediaColumns.DISPLAY_NAME, name);
        values.put(ImageColumns.DATE_TAKEN, now);
        values.put(MediaColumns.DATE_MODIFIED, now/1000);
        values.put(MediaColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaColumns.DATA, file.getPath());
        values.put(MediaColumns.SIZE, file.length());
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    
    private void failedMessage() {
        ((BaseActivity)mContext).showToast(mContext.getString(R.string.message_picture_not_saved));
    }
    
    public native void FindFeatures(long matAddrGr, long matAddrRgba);

    static {
        System.loadLibrary("mixed_vg");
    }
}
