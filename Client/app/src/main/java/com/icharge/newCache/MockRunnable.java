package com.icharge.newCache;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface MockRunnable {

	void run() throws ClientProtocolException, JSONException, Exception;

	void timeoutCallback();
}
