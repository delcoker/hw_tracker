package com.delcoker.homeworktracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Frag01_hw_tracker extends Fragment {
	private String pid;
	private String class_id;
	private String radioDue;
	private String date;

	private List<String> subjects;
	private List<String> assignement;
	private List<String> due_date;

	private ListView l;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		subjects = new ArrayList<String>();
		assignement = new ArrayList<String>();
		due_date = new ArrayList<String>();
		// getAll();
		return inflater.inflate(R.layout.frag01_hw, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		radioDue = getArguments().getString("radio");
		Log.v("radioDue", radioDue);

		pid = getArguments().getString("pid");
		class_id = getArguments().getString("class_id");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		date = df.format(dateobj);

		Log.v("sdf", date);
		getAssignmentsTomorrow();

	}

	private boolean getAssignmentsTomorrow() {
		String value = null;
		String url = null;
		TextView timePeriod = (TextView)getActivity().findViewById(R.id.textView1);
		if (radioDue.equals("0")) {
			timePeriod.setText("Due Tomorrow");
			
			url = "http://50.63.128.135/~csashesi/class2015/kingston-coker/mobile_web/hw_tracker/android_requests/get_details.php?pid2="
					+ EncodeHelper(pid)
					+ "&cid="
					+ EncodeHelper(class_id)
					+ "&date=" + EncodeHelper(date);
		} else if (radioDue.equals("1")) {
			timePeriod.setText("Due Week");
			url = "http://50.63.128.135/~csashesi/class2015/kingston-coker/mobile_web/hw_tracker/android_requests/get_details.php?pid3="
					+ EncodeHelper(pid)
					+ "&cid="
					+ EncodeHelper(class_id)
					+ "&date=" + EncodeHelper(date);
		}
		Log.v("url", url);

		MyTask task = new MyTask();
		try {
			value = task.execute(new String[] { url }).get();
			Log.v("val", value);

			if (value.length() > 0) {
				String[] assignments = value.split("#,");
				for (int i = 0; i < assignments.length; i++) {
					String[] details = assignments[i].split("[#]+");

					subjects.add(details[0]);
					assignement.add(details[1]);
					due_date.add(details[2]);
					//
					// items.add(details[1]);
					// amount.add(details[2]);

				}
			}

			l = (ListView) getActivity().findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_2,
					android.R.id.text1, subjects) {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);

					TextView text2 = (TextView) view
							.findViewById(android.R.id.text2);
					text2.setText("Due: "
							+ String.valueOf(due_date.get(position)));

					return view;
				}
			};

			l.setAdapter(adapter);
			l.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					// Intent intent = new Intent(HomeWorks.this,
					// Details.class);
					// String message = "abc";
					// intent.putExtra("student_id", position);
					// intent.putExtra("name", children.get(position));
					// intent.putExtra("parent_id", pid);
					// intent.putExtra("class_id", classes.get(position));
					// startActivity(intent);
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
