package com.arun.rajora.chat.chit.bingo.family.bingofamily;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQR extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr);
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
		integrator.setPrompt("");
		integrator.setOrientationLocked(true);
		integrator.setCameraId(0);
		integrator.setBeepEnabled(true);
		integrator.setBarcodeImageEnabled(true);
		integrator.initiateScan();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if(result != null) {
			finish();
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
