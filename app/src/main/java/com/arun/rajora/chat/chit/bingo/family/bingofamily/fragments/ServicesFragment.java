package com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.Model.Transaction;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServicesFragment extends Fragment {

	int countServices=0;
	double costPerItem=2.50;

	public ServicesFragment() {
	}

	public static ServicesFragment newInstance() {
		ServicesFragment fragment = new ServicesFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		final View v= inflater.inflate(R.layout.fragment_services, container, false);
		final CompactCalendarView compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);
		compactCalendarView.setSaveEnabled(true);
		final TextView service_submit= ((TextView) v.findViewById(R.id.service_submit));
		service_submit.setText(String.valueOf(countServices)+" x "+String.valueOf(costPerItem)+" = "+String.valueOf(countServices*costPerItem));

		compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
			@Override
			public void onDayClick(Date dateClicked) {
				List<Event> events = compactCalendarView.getEvents(dateClicked);
				if(events.size()<=0){
					Event ev1 = new Event(Color.GREEN, dateClicked.getTime(), "Fuel");
					compactCalendarView.addEvent(ev1,true);
					countServices++;
				}
				else{
					compactCalendarView.removeEvents(dateClicked);
					countServices--;
				}
				service_submit.setText(String.valueOf(countServices)+" x "+String.valueOf(costPerItem)+" = "+String.valueOf(countServices*costPerItem));
			}

			@Override
			public void onMonthScroll(Date firstDayOfNewMonth) {

			}
		});

		v.findViewById(R.id.service_submit_container).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				new AlertDialog.Builder(getContext())
						.setTitle("Confirm Transaction")
						.setMessage("Do you really want to add it as transaction?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								Calendar c=Calendar.getInstance();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
								String timestr = df.format(c.getTime());
								DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transactions/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
								mRef.push().setValue(new Transaction("SPENT",String.valueOf(countServices*costPerItem),"Service","Fuel",
										timestr,String.valueOf(countServices)+" x "+String.valueOf(costPerItem)+" = "+String.valueOf(countServices*costPerItem)));
								countServices=0;
								service_submit.setText(String.valueOf(countServices)+" x "+String.valueOf(costPerItem)+" = "+String.valueOf(countServices*costPerItem));
								compactCalendarView.clearAnimation();
								compactCalendarView.removeAllEvents();
								compactCalendarView.showCalendarWithAnimation();
								Snackbar.make(v.findViewById(R.id.service_submit_container),"Added to Transactions",Snackbar.LENGTH_SHORT).show();
							}})
						.setNeutralButton("PAY", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {

							}
						})
						.setNegativeButton(android.R.string.no, null).show();
			}
		});

		v.findViewById(R.id.clearAll).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new AlertDialog.Builder(getContext())
						.setTitle("Confirm Clear")
						.setMessage("Do you really want to clear all transactions?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								countServices=0;
								service_submit.setText(String.valueOf(countServices)+" x "+String.valueOf(costPerItem)+" = "+String.valueOf(countServices*costPerItem));
								compactCalendarView.clearAnimation();
								compactCalendarView.removeAllEvents();
								compactCalendarView.showCalendarWithAnimation();
							}})
						.setNegativeButton(android.R.string.no, null).show();
			}
		});
		return v;
	}

}
