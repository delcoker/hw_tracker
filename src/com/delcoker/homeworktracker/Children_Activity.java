package com.delcoker.homeworktracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Children_Activity extends Activity {

	ListView l;
	private Intent fromMain;
	private String pid; // parent id
	private List<String> sids; // student it
	private List<String> children;
	private List<String> classes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children);

		fromMain = getIntent();

		pid = fromMain.getStringExtra("pid");

		getChildren();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.children_, menu);

		return true;
	}

	public boolean getChildren() {
		// String pid = ((EditText)
		// findViewById(R.id.editText1)).getText().toString();
		sids = new ArrayList<String>();
		children = new ArrayList<String>();
		classes = new ArrayList<String>();

		MyTask task = new MyTask();
		String value = null;

		String url = "http://50.63.128.135/~csashesi/class2015/kingston-coker/mobile_web/hw_tracker/android_requests/get_details.php?pid="
				+ EncodeHelper(pid);
		Log.v("url", url);

		try {
			value = task.execute(new String[] { url }).get();

			// HashMap<String, String> map = null;

			// String a = "1#Sule Man#,3#Nii Apa#,";
			// String[] b = new String[2];
			// b = a.split("#,");
			//
			// Log.v("usedddd", b[0]);
			// Log.v("usedddd", b[1]);

			String[] users = value.split("#,");
			// 1#Sule Man#$3#Nii Apa#$

			Log.v("users", users.length + "");
			Log.v("users", value);

			for (int i = 0; i < users.length; i++) {

				String[] details = users[i].split("#");
//				{
//					int j = 0;
//					while (j < details.length) {
//						Log.v("vvvvvvvvvvvvvvvv", details[j]);
//						j++;
//					}
//				}

				Log.v("details.length", details.length + "");
				// using list
				sids.add(details[0]);
				children.add(details[1]);
				classes.add(details[2]);
			}

			l = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_2, android.R.id.text1,
					children) {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);

					TextView text2 = (TextView) view
							.findViewById(android.R.id.text2);
					text2.setText("Student ID: "
							+ String.valueOf(sids.get(position)));

					return view;
				}
			};

			l.setAdapter(adapter);
			l.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					Intent intent = new Intent(Children_Activity.this,
							HomeWorks.class);
					// String message = "abc";
					intent.putExtra("student_id", sids.get(position));
					intent.putExtra("name", children.get(position));
					intent.putExtra("pid", pid);
					intent.putExtra("class_id", classes.get(position));
					startActivity(intent);
				}
			});

			return true;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// if (task.execute(new String[] { url }).get().substring(0, 1)
		// .equalsIgnoreCase("t")) {
		// Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
		// ((EditText) findViewById(R.id.editText1)).setText("");
		// // ((EditText) findViewById(R.id.editText2)).setText("");
		// // ((EditText) findViewById(R.id.editText3)).setText("");
		// } else {
		// Toast.makeText(this, "Could not add income", Toast.LENGTH_LONG)
		// .show();
		// }
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return false;

	}

	private class MyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {

			String response = "";
			for (String url : urls) {
				System.out.println(url);
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out
							.println("EEEEERRRRRRRRRRRRRRROOOOOOOORRRRRRRRRRRRR"
									+ e.getMessage());
				} finally {
					client.getConnectionManager().shutdown();
				}
			}
			return response;

		}
	}

	private String EncodeHelper(String text) {
		String result = "";
		try {
			result = URLEncoder.encode(text, "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(").replaceAll("\\%27", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			// logger.error("Error encoding text ", e);
			System.out.println("Error encoding text " + e);
			return null;
		}
		return result;
	}

}
