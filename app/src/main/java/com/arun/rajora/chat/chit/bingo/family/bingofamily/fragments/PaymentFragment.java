package com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.GenerateQR;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.Model.Transaction;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.ScanQR;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class PaymentFragment extends Fragment {

	View v;

	public PaymentFragment() {
	}

	public static PaymentFragment newInstance() {
		PaymentFragment fragment = new PaymentFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		v=inflater.inflate(R.layout.fragment_payment, container, false);
		v.findViewById(R.id.payment_send).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setupSend();
			}
		});

		v.findViewById(R.id.payment_loan).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setupLoan();
			}
		});

		v.findViewById(R.id.payment_receive).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setupReceive();
			}
		});
		return v;
	}

	private void setupSend(){
		final String recipients[]=new String[]{"Newspaper (9999111100)", "Milk (8888222211)", "Friend (7777111133)", "Add New Number"};
		new AlertDialog.Builder(getContext())
				.setTitle("Select Method")
				.setMessage("For Number wallet, choose Wallet.Device with Internet should choose Scan and other devices Generate.")
				.setPositiveButton("Scan", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						selectAmount(true,false,"",true,"SEND");
					}})
				.setNegativeButton("Generate", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						selectAmount(false,false,"",true,"SEND");
					}
				})
				.setNeutralButton("Wallet", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
							new AlertDialog.Builder(getContext())
									.setTitle("Select Number")
									.setItems(recipients, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialogInterface, int i) {
											if(i>=0 && i<3){
												selectAmount(false,true,recipients[i],true,"WALLET");
											}
										}
									})
									.show();
					}
				}).show();
	}

	private void setupLoan(){
		new AlertDialog.Builder(getContext())
				.setTitle("Select Method")
				.setMessage("Device with Internet should choose Scan and other devices Generate.")
				.setPositiveButton("Scan", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						selectAmount(true,false,"",true,"LOAN");
					}})
				.setNegativeButton("Generate", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						selectAmount(false,false,"",true,"LOAN");
					}
				})
				.setNeutralButton("Cancel",null).show();
	}

	private void setupReceive(){
		new AlertDialog.Builder(getContext())
				.setTitle("Select Method")
				.setMessage("Device with Internet should choose Scan and other devices Generate.")
				.setPositiveButton("Scan", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						selectAmount(true,false,"",false,"RECEIVE");
					}})
				.setNegativeButton("Generate", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						selectAmount(false,false,"",false,"RECEIVE");
					}
				})
				.setNeutralButton("Cancel",null).show();
	}

	private void selectAmount(final boolean isScan, final boolean iswallet, final String reciepient, final boolean isSend, final String sendType){

		AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getContext())
				.setTitle("Select Amount")
				.setNegativeButton("Cancel", null);

		final EditText input = new EditText(getContext());
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		input.setMaxLines(1);

		Random rnd=new Random();
		String amt="";
		for(int i=0;i<4;i++){
			amt+=String.valueOf(rnd.nextInt(9)+1);
		}

		input.setText(amt);

		dialogBuilder.setView(input);

		dialogBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if(!iswallet){
					String nreceipent="";
					if(!isSend){
						FirebaseAuth auth=FirebaseAuth.getInstance();
						FirebaseUser x=auth.getCurrentUser();
						if(x.getDisplayName()!=null){
							nreceipent=x.getDisplayName();
						}
					}
					if(isScan){
						callScan(nreceipent,input.getText().toString(),sendType);
					}
					else{
						callGenerate(nreceipent,input.getText().toString(),sendType);
					}
				}
				else{
					Calendar c=Calendar.getInstance();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
					String timestr = df.format(c.getTime());
					DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transactions/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
					mRef.push().setValue(new Transaction("SPENT",input.getText().toString(),"Service","Others",
							timestr,"Sent to mobile wallet of "+reciepient+" ."));
					Snackbar.make(v.findViewById(R.id.payment_receive),"Payment to "+reciepient+" Successful",Snackbar.LENGTH_SHORT).show();
				}
			}});
		dialogBuilder.show();
	}

	private void callGenerate(String receipent,String amount,String sendType){
		Intent intent = new Intent(getContext(), GenerateQR.class);
		intent.putExtra("RECEIVER", receipent);
		intent.putExtra("AMOUNT", amount);
		intent.putExtra("ISSEND", sendType);
		startActivity(intent);
	}

	private void callScan(String receipent,String amount,String sendType){
		Intent intent = new Intent(getContext(), ScanQR.class);
		intent.putExtra("RECEIVER", receipent);
		intent.putExtra("AMOUNT", amount);
		intent.putExtra("ISSEND", sendType);
		startActivity(intent);
	}

}
