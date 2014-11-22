package com.delcoker.homeworktracker;

import java.util.List;

import android.content.Context;
import android.media.Image.Plane;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

class Homework {
	String subject;
	String dateDue;
	boolean selected = false;

	public Homework(String subject, String dateDue) {
		super();
		this.subject = subject;
		this.dateDue = dateDue;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDateDue() {
		return dateDue;
	}

	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

public class HomeworkAdapter extends ArrayAdapter<Homework> {
	private List<Homework> homeworkList;
	private Context context;

	public HomeworkAdapter(List<Homework> homeworkList, Context context) {
		super(context, R.layout.single_listview_item, homeworkList);
		this.homeworkList = homeworkList;
		this.context = context;
	}

	private static class HomeworkHolder {
		public TextView subject;
		public TextView dateDue;
		public CheckBox chkBox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		HomeworkHolder holder = new HomeworkHolder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.single_listview_item, null);

			holder.subject = (TextView) v.findViewById(R.id.subject);
			holder.dateDue = (TextView) v.findViewById(R.id.due);
			holder.chkBox = (CheckBox) v.findViewById(R.id.chk_box);

			holder.chkBox.setOnCheckedChangeListener((HomeWorks) context);
		} else {
			holder = (HomeworkHolder) v.getTag();
		}

		Homework hw = homeworkList.get(position);
		holder.subject.setText(hw.getSubject());
		holder.dateDue.setText(hw.getDateDue());
		holder.chkBox.setChecked(hw.isSelected());
		holder.chkBox.setTag(hw);

		return v;
	}
}
