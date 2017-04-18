package com.arun.rajora.chat.chit.bingo.family.bingofamily;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;


public class GenerateQR extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_qr);

		String sendDirection=getIntent().getExtras().getString("ISSEND");
		String amount=getIntent().getExtras().getString("AMOUNT");
		String receiver=getIntent().getExtras().getString("RECEIVER");

		Bitmap myBitmap = QRCode.from(sendDirection+" "+amount+" "+receiver).bitmap();
		ImageView myImage = (ImageView) findViewById(R.id.qr_view);
		myImage.setImageBitmap(myBitmap);
	}
}
