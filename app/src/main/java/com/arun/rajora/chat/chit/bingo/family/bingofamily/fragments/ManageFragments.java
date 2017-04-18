package com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageFragments extends Fragment {

	TimeAdapter adapter;
	String types[]={"Self","Food","Vehicle","Mobile Recharge","Education","Others","Purchases"};

	public ManageFragments() {
	}
	public static ManageFragments newInstance() {
		ManageFragments fragment = new ManageFragments();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_manage_fragments, container, false);
		v.findViewById(R.id.img_add_alert).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		v.findViewById(R.id.img_add_notify).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar c=Calendar.getInstance();
				TimePickerDialog pickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int i, int i1) {
						addNotifyItem(timePicker);
					}
				},c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false);
				pickerDialog.show();
			}
		});

		GridView gridview = (GridView) v.findViewById(R.id.gridview_notify);
		adapter=new TimeAdapter(getContext());
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				adapter.removeItem(i);
			}
		});
		return v;
	}

	public void addNotifyItem(TimePicker picker){
		int hod=picker.getCurrentHour();
		String apm=hod>=12?"AM":"PM";
		hod=hod>=12?hod-12:hod;
		adapter.addItem(String.valueOf(hod)+":"+String.valueOf(picker.getCurrentMinute())+"\n"+apm);
	}


	public class TimeAdapter extends BaseAdapter {
		private Context mContext;

		final List<String> notifyList = new ArrayList<String>();


		public TimeAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return notifyList.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View item;
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				item = inflater.inflate(R.layout.vh_notify,null);
			} else {
				item = convertView;
			}

			((TextView) item.findViewById(R.id.notify_time_id)).setText(notifyList.get(position));
			return item;
		}

		public void addItem(String str){
			notifyList.add(str);
			notifyDataSetChanged();
		}


		public void removeItem(int pos){
			notifyList.remove(pos);
			notifyDataSetChanged();
		}

	}
}
