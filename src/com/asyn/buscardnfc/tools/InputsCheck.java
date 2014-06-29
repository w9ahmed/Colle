package com.asyn.buscardnfc.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;


public final class InputsCheck {
	
	public void  checkEmailInput(EditText editText) {
		String input = editText.getText().toString().trim();
	}
	
	public boolean checkEmptyInput(EditText editText) {
		return editText.getText().toString().trim().isEmpty();		
	}
	
	
	/* Dialog method for checking Inputs */
	public static void displayDialog(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Oops!")
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
