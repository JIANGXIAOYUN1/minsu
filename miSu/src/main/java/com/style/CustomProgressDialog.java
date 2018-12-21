package com.style;
import com.example.misu.R;

import android.app.Dialog;
import android.content.Context;

public class CustomProgressDialog extends Dialog {
	public CustomProgressDialog(Context context) {
		this(context, R.style.CustomProgressDialog);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.custom_dialog);
		//this.getWindow().getAttributes().gravity = Gravity.CENTER;
	
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		if (!hasFocus) {
			dismiss();
		}
	}
}