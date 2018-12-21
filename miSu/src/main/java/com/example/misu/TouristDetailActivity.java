package com.example.misu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class TouristDetailActivity extends Activity {
TextView tx_details;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tourist_detail);
		
		tx_details=(TextView) findViewById(R.id.tx_showText);
		Intent i=getIntent();
		String details=i.getStringExtra("details");
		tx_details.setText(details);
	}
}
