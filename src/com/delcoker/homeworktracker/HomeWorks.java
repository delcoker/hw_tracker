package com.delcoker.homeworktracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeWorks extends FragmentActivity {

	private Intent fromChildren;
	private String pid;
	private String class_id;

	private List<String> subjects;
	private List<String> assignement;
	private List<String> due_date;

	private ListView l;

	// private String
	// private

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_works);

		fromChildren = getIntent();
		
		pid = fromChildren.getStringExtra("pid");
		class_id = fromChildren.getStringExtra("class_id");

		// getAssignments();

		// intent.putExtra("student_id", position);
		// intent.putExtra("name", children.get(position));

		// fragments
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		Frag01_hw_tracker startFragment = new Frag01_hw_tracker();
		
		Bundle bundle = new Bundle();
		bundle.putString("radio","0");
		bundle.putString("pid", pid);
		bundle.putString("class_id", class_id);
		startFragment.setArguments(bundle);

		transaction.add(R.id.fragment_placeholder, startFragment);
		transaction.commit();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.home_works, menu);
	// return true;
	// }

	public void exit(View view) {
		finish();
	}

	public void selectFragment(View view) {

		Fragment newFragment;

		// save
		if (view == findViewById(R.id.radio0)) {
			printer("radio oooooooooooooooo00000000000");
			newFragment = new Frag01_hw_tracker();
			// save();

			Bundle bundle = new Bundle();
			// bundle.putString("Spinner", String.valueOf(spinnerPosition));
			// bundle.putString("Item", item);
			bundle.putString("radio","0");
			bundle.putString("pid", pid);
			bundle.putString("class_id", class_id);
			// set Fragmentclass Arguments
			newFragment.setArguments(bundle);
			printer("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

			// view
		} else if (view == findViewById(R.id.radio1)) {
			newFragment = new Frag01_hw_tracker();
			Bundle bundle = new Bundle();
			// bundle.putString("amount_left", amountLeft());
			// set Fragmentclass Arguments
			bundle.putString("radio","1");
			bundle.putString("pid", pid);
			bundle.putString("class_id", class_id);
			newFragment.setArguments(bundle);

			// newFragment.onCreateView(this, this, this)
		} else if (view == findViewById(R.id.btnSign)) {
			newFragment = new Frag02_signoff();

		} else {
			printer("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			newFragment = new Frag01_hw_tracker();
			// amountLeft();

			Bundle bundle = new Bundle();
			// bundle.putString("amount_left", amountLeft());
			// // set Fragmentclass Arguments
			// newFragment.setArguments(bundle);
			bundle.putString("pid", pid);
			bundle.putString("class_id", class_id);
			newFragment.setArguments(bundle);
			printer("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		}

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragment_placeholder, newFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	public void printer(String text) {
		Log.v("Hmm", text);
	}

	// private boolean getAssignments() {
	// String value = null;
	// String url =
	// "http://50.63.128.135/~csashesi/class2015/kingston-coker/mobile_web/hw_tracker/android_requests/get_details.php?pid2="
	// + EncodeHelper(pid) + "&cid=" + EncodeHelper(class_id);
	// Log.v("url", url);
	//
	// MyTask task = new MyTask();
	// try {
	// value = task.execute(new String[] { url }).get();
	// Log.v("val", value);
	//
	// String[] assignments = value.split("#,");
	// for (int i = 0; i < assignments.length; i++) {
	// String[] details = assignments[i].split("[#]+");
	//
	//
	// subjects.add(details[0]);
	// assignement.add(details[1]);
	// due_date.add(details[2]);
	// //
	// // items.add(details[1]);
	// // amount.add(details[2]);
	//
	// }
	//
	// l = (ListView) findViewById(R.id.listView1);
	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_list_item_2, android.R.id.text1,
	// subjects) {
	//
	// @Override
	// public View getView(int position, View convertView,
	// ViewGroup parent) {
	// View view = super.getView(position, convertView, parent);
	//
	// TextView text2 = (TextView) view
	// .findViewById(android.R.id.text2);
	// text2.setText("Student ID: "
	// + String.valueOf(due_date.get(position)));
	//
	// return view;
	// }
	// };
	//
	// l.setAdapter(adapter);
	// l.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1,
	// int position, long id) {
	// // Intent intent = new Intent(HomeWorks.this,
	// // Details.class);
	// // String message = "abc";
	// // intent.putExtra("student_id", position);
	// // intent.putExtra("name", children.get(position));
	// // intent.putExtra("parent_id", pid);
	// // intent.putExtra("class_id", classes.get(position));
	// // startActivity(intent);
	// }
	// });
	//
	// return true;
	//
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return false;
	// }

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
