package ro.pub.cs.toops.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class QrClient {
	private String url;
	
	public QrClient(String url) {
		this.url = url;
	}
	
	public void postQr(String code) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		HttpClient httpclient = new DefaultHttpClient();

		Log.e("mesaje", code);
		HttpPost request = new HttpPost(url + "postqr");
		request.addHeader("content-type", "application/json");

		JSONObject json = new JSONObject();
		try {
			json.put("code", code);
			StringEntity se = new StringEntity( "JSON: " + json.toString());
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			request.setEntity(se);
			httpclient.execute(request);
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getQr(long id) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet request = new HttpGet(url + "getqr?code=" + id);
		request.addHeader("content-type", "application/x-www-form-urlencoded");

		try {
			httpclient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String listQr() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet request = new HttpGet(url + "list");
		request.addHeader("content-type", "application/x-www-form-urlencoded");
		JSONArray json = null;

		try {
			HttpResponse rsp = httpclient.execute(request);
			json = new JSONArray(getResponse(rsp.getEntity().getContent()));
			
			for (int i = 0; i < json.length(); i++) {
				Log.d("mesaje", json.toString());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return json.toString();
	}

	private String getResponse(InputStream is) {
		String buffer = "";
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder();

		try {
			while ((buffer = rd.readLine()) != null)
				response.append(buffer);
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response.toString();
	}
}