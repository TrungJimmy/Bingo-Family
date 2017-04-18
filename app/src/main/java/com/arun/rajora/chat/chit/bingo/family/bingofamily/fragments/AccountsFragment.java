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
import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;
import com.arun.rajora.chat.chit.bingo.family.bingofamily.vh.AccountVH;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;

public class AccountsFragment extends Fragment {

	FirebaseRecyclerAdapter mAdapter;

	public AccountsFragment() {
	}

	public static AccountsFragment newInstance() {
		AccountsFragment fragment = new AccountsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_accounts, container, false);
		RecyclerView recycler = (RecyclerView) v.findViewById(R.id.recycler_account);
		recycler.setHasFixedSize(false);
		recycler.setLayoutManager(new LinearLayoutManager(getContext()));
		DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
		mAdapter = new FirebaseRecyclerAdapter<Account, AccountVH>(Account.class, R.layout.vh_account, AccountVH.class, mRef) {
			@Override
			public void populateViewHolder(AccountVH item, Account chatMessage, int position) {
				item.setName(chatMessage.getName());
				item.setBalance(chatMessage.getBalance());
				item.setNumber(chatMessage.getAccount_no());
			}
		};
		recycler.setAdapter(mAdapter);
		FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.account_fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				show_add_account_dialog();
			}
		});
		return v;
	}

	private void show_add_account_dialog(){
		LayoutInflater factory = LayoutInflater.from(getContext());
		final View dialogView = factory.inflate(R.layout.dialog_add_account, null);
		final TextView add_name=(TextView)dialogView.findViewById(R.id.add_acc_name);
		final TextView add_number=(TextView)dialogView.findViewById(R.id.add_acc_number);

		String names[]={"ICICI","Arun","Rajora","Bingo"};
		Random rnd=new Random();
		add_name.setText(names[rnd.nextInt(3)]);
		String acc_no="";
		for(int i=0;i<16;i++){
			acc_no+=String.valueOf(rnd.nextInt(9)+1);
		}

		add_number.setText(acc_no);

		final AlertDialog deleteDialog = new AlertDialog.Builder(getContext()).create();
		deleteDialog.setView(dialogView);
		deleteDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
				int balance= (new Random()).nextInt((50000 - 5000) + 1) + 5000;
				mRef.push().setValue(new Account(add_name.getText().toString(),add_number.getText().toString(),String.valueOf(balance)));
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