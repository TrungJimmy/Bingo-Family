package com.arun.rajora.chat.chit.bingo.family.bingofamily.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.Model.Account;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.Model.Transaction;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.vh.AccountVH;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.vh.TransactionVH;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;

public class TransactionsFragment extends Fragment {

	FirebaseRecyclerAdapter mAdapter;
	String types[]={"Self","Food","Vehicle","Mobile Recharge","Education","Others","Purchases"};

	int open_position=-1;
	String categories[]={};

	public TransactionsFragment() {
	}

	public static TransactionsFragment newInstance() {
		TransactionsFragment fragment = new TransactionsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.fragment_transactions, container, false);
		RecyclerView recycler = (RecyclerView) v.findViewById(R.id.recycler_transaction);
		recycler.setHasFixedSize(false);
		recycler.setLayoutManager(new LinearLayoutManager(getContext()));
		DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transactions/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
		mAdapter = new FirebaseRecyclerAdapter<Transaction, TransactionVH>(Transaction.class, R.layout.vh_transactions, TransactionVH.class, mRef) {
			@Override
			public void populateViewHolder(final TransactionVH item, Transaction chatMessage, final int position) {
				item.setValues(chatMessage.getAmount(),chatMessage.getCategory(),chatMessage.getTime(),chatMessage.getSource(),chatMessage.getNotes(),position==open_position,getActivity());

				DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(getActivity(), item.mType);

				for (String x:types) {
					droppyBuilder.addMenuItem(new DroppyMenuItem(x));
				}
				droppyBuilder.addSeparator();
				droppyBuilder.addMenuItem(new DroppyMenuItem("Add New"));

				droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
					@Override
					public void call(View v, int id) {
						if(id<types.length){
							item.mType.setText(types[id]);
						}
					}
				});
				droppyBuilder.build();

				item.mHead.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(open_position==position){
							open_position=-1;
						}
						else{
							open_position=position;
						}
						boolean isOpen=open_position==position;
						if(!isOpen){
							item.mBody.setVisibility(View.GONE);
							item.mheadicon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
						}
						else{
							item.mBody.setVisibility(View.VISIBLE);
							item.mheadicon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
						}
					}
				});
			}
		};
		recycler.setAdapter(mAdapter);
		FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.transaction_fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				show_add_transaction_dialog();
			}
		});
		return v;
	}

	private void show_add_transaction_dialog(){
		LayoutInflater factory = LayoutInflater.from(getContext());
		final View dialogView = factory.inflate(R.layout.dialog_add_transaction, null);
		final TextView add_amount=(TextView)dialogView.findViewById(R.id.add_trans_amount);
		final ToggleSwitch add_drawn=(ToggleSwitch) dialogView.findViewById(R.id.add_trans_drawn);
		add_drawn.setCheckedTogglePosition(0);
		Random rnd=new Random();
		String amount="";
		for(int i=0;i<4;i++){
			amount+=String.valueOf(rnd.nextInt(9)+1);
		}

		add_amount.setText(amount);

		final AlertDialog deleteDialog = new AlertDialog.Builder(getContext()).create();
		deleteDialog.setView(dialogView);
		deleteDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Calendar c=Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
				String timestr = df.format(c.getTime());
				DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transactions/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
				mRef.push().setValue(new Transaction(add_drawn.getCheckedTogglePosition()!=1?"EARNED":"SPENT",add_amount.getText().toString(),"custom","Others",
						timestr,""));
			}
		});

		deleteDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "CANCEL", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				deleteDialog.dismiss();
			}
		});

		deleteDialog.show();
	}
}
