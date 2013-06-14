package com.njzk2.volleyext;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

public interface ImageRequestFactory {

	public Request<?> getImageRequest(String url, Listener<Bitmap> listener,
			int maxWidth, int maxHeight, Config config, ErrorListener errorListener);
}
