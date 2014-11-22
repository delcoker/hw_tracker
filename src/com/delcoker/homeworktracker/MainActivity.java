package com.delcoker.homeworktracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	public void getChildren(View view) {
		String pid = ((EditText) findViewById(R.id.editText1)).getText().toString();
		Intent intent_convert = new Intent(MainActivity.this, Children_Activity.class);
		intent_convert.putExtra("pid", pid);
		
//		System.out.println("herere------------------------------" + g.getCheckedRadioButtonId());
		startActivity(intent_convert);
	
	}
//
//	private class MyTask extends AsyncTask<String, Void, String> {
//
//		@Override
//		protected String doInBackground(String... urls) {
//
//			String response = "";
//			for (String url : urls) {
//				System.out.println(url);
//				DefaultHttpClient client = new DefaultHttpClient();
//				HttpGet httpGet = new HttpGet(url);
//				try {
//					HttpResponse execute = client.execute(httpGet);
//					InputStream content = execute.getEntity().getContent();
//
//					BufferedReader buffer = new BufferedReader(
//							new InputStreamReader(content));
//					String s = "";
//					while ((s = buffer.readLine()) != null) {
//						response += s;
//					}
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out
//							.println("EEEEERRRRRRRRRRRRRRROOOOOOOORRRRRRRRRRRRR"
//									+ e.getMessage());
//				} finally {
//					client.getConnectionManager().shutdown();
//				}
//			}
//			return response;
//
//		}
//	}
//
//	private String EncodeHelper(String text) {
//		String result = "";
//		try {
//			result = URLEncoder.encode(text, "UTF-8").replaceAll("\\+", "%20")
//					.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
//					.replaceAll("\\%28", "(").replaceAll("\\%27", ")")
//					.replaceAll("\\%7E", "~");
//		} catch (UnsupportedEncodingException e) {
//			// logger.error("Error encoding text ", e);
//			System.out.println("Error encoding text " + e);
//			return null;
//		}
//		return result;
//	}

}
