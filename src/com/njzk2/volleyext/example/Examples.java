package com.njzk2.volleyext.example;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.njzk2.volleyext.DelegatingRequest;
import com.njzk2.volleyext.ImageRequestFactory;
import com.njzk2.volleyext.DelegatingRequest.NewtorkResponseParser;
import com.njzk2.volleyext.ExtendedImageLoader;

public class Examples {
	private static final String TOKEN_URL = "https://api.twitter.com/oauth2/token";
	private static String ACCESS_TOKEN = null;

	protected static final String TAG = Examples.class.getSimpleName();

	public void exampleToken(RequestQueue queue) {
		DelegatingRequest<JSONObject> tokenRequest = new DelegatingRequest<JSONObject>(
				Method.POST, TOKEN_URL, NewtorkResponseParser.jsonObjectParser,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						ACCESS_TOKEN = response.optString("access_token");
						// Do the rest of the treatment
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error during request");
						error.printStackTrace();
					}
				});
		String auth = "Basic "
				+ Base64.encodeToString(("<CONSUMER_KEY>" + ":"
						+ "<CONSUMER_SECRET>").getBytes(), Base64.NO_WRAP);
		tokenRequest.addHeader("Authorization", auth);
		tokenRequest.addParam("grant_type", "client_credentials");
		queue.add(tokenRequest);
	}
	public void exampleImageLoader(RequestQueue queue) {
		ImageLoader imageLoader = new ExtendedImageLoader(queue, new BitmapLru(64000), new ImageRequestFactory() {
			
			@Override
			public Request<?> getImageRequest(String url, Listener<Bitmap> listener,
					int maxWidth, int maxHeight, Config config,
					ErrorListener errorListener) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
