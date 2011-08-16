package com.bugcloud.android.vg.share;

public class Common {

	public static int getRandom(int max, int min) {
		int ret = (int)Math.floor(Math.random()*(max-min+1)) + min;
		return ret;
	}
}
