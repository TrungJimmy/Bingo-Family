package com.arun.rajora.chat.chit.bingo.family.bingofamily.vh;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arun.rajora.chat.chit.bingo.family.bingofamily.R;

public class TransactionVH extends RecyclerView.ViewHolder{


	public final TextView mAmount;
	public final Button mType;
	public final ImageView mheadicon;
	public final View mHead;
	public final View mBody;
	public final TextView mDate;
	public final TextView mSource;
	public final EditText mAbout;
	public final Button mSubmit;

	public TransactionVH(View itemView) {
		super(itemView);
		mAmount= (TextView) itemView.findViewById(R.id.transaction_amount);
		mType = (Button) itemView.findViewById(R.id.transaction_type);
		mheadicon = (ImageView) itemView.findViewById(R.id.dropdown_image);
		mHead= itemView.findViewById(R.id.dropdown_container);
		mBody = itemView.findViewById(R.id.dropdown_body);
		mDate = (TextView) itemView.findViewById(R.id.transaction_date);
		mSource= (TextView) itemView.findViewById(R.id.transaction_source);
		mAbout = (EditText) itemView.findViewById(R.id.transaction_about);
		mSubmit = (Button) itemView.findViewById(R.id.transaction_done);
	}

	public void setValues(String amount,String type,String date,String source,String about,boolean isOpen,Activity activity) {
		mAmount.setText(amount+" \u20B9");
		mType.setText(type);
		mDate.setText(date);
		mSource.setText(source);
		mAbout.setText(about);
		if(!isOpen){
			mBody.setVisibility(View.GONE);
			mheadicon.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
		}
		else{
			mBody.setVisibility(View.VISIBLE);
			mheadicon.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
		}
	}
}
