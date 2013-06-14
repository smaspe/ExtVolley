package com.njzk2.volleyext;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.njzk2.volleyext.parsers.JsonObjectParser;
import com.njzk2.volleyext.parsers.StringParser;

public class DelegatingRequest<T> extends Request<T> {

	Listener<T> listener;
	NewtorkResponseParser<T> parser;
	Map<String, String> headers = new HashMap<String, String>();
	byte[] customBody = null;
	Map<String, String> params = new HashMap<String, String>();
	private String customBodyContentType = null;

	public DelegatingRequest(int method, String url, NewtorkResponseParser<T> parser, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.parser = parser;
		this.listener = listener;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}
	public void addParam(String key, String value) {
		params.put(key, value);
	}
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}
	public void setCustomBody(byte[] customBody, String contentType) {
		this.customBody = customBody;
		this.customBodyContentType = contentType;
	}
	@Override
	public byte[] getBody() throws AuthFailureError {
		return customBody != null ? customBody : super.getBody();
	}
	@Override
	public String getBodyContentType() {
		return customBodyContentType != null ? customBodyContentType : super.getBodyContentType();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		return parser.parseResponse(response);
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	public interface NewtorkResponseParser<T> {
		public static JsonObjectParser jsonObjectParser = new JsonObjectParser();
		public static StringParser stringParser = new StringParser();
		public Response<T> parseResponse(NetworkResponse response);
	}
}
