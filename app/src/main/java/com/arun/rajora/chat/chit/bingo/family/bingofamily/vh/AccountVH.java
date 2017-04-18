package com.arun.rajora.chat.chit.bingo.family.bingofamily.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;

public class AccountVH extends RecyclerView.ViewHolder {

	private final TextView mName;
	private final TextView mNumber;
	private final TextView mBalance;

	public AccountVH(View itemView) {
		super(itemView);
		mName= (TextView) itemView.findViewById(R.id.acc_name);
		mNumber = (TextView) itemView.findViewById(R.id.acc_number);
		mBalance = (TextView) itemView.findViewById(R.id.acc_balance);
	}

	public void setName(String name) {
		mName.setText(name);
	}

	public void setBalance(String text) {
		mBalance.setText(text+" \u20B9");
	}

	public void setNumber(String text) {
		mNumber.setText(text);
	}
}