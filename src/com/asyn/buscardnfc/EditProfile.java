package com.asyn.buscardnfc;

import com.asyn.buscardnfc.tools.InputsCheck;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {

	protected EditText mUsername;
	protected EditText mFirstName;
	protected EditText mLastName;
	protected EditText mEmailAddress;
	protected EditText mPassword;
	protected Button mSaveButton;
	protected Button mDiscardButon;

	private ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.edit_profile_activity);

		mUsername = (EditText) findViewById(R.id.usernameField);
		mFirstName = (EditText) findViewById(R.id.firstNameField);
		mLastName = (EditText) findViewById(R.id.lastNameField);
		mEmailAddress = (EditText) findViewById(R.id.emailField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mSaveButton = (Button) findViewById(R.id.saveButton);
		mDiscardButon = (Button) findViewById(R.id.discardButton);

		currentUser = ParseUser.getCurrentUser();
		mUsername.setText(currentUser.getUsername());
		mFirstName.setText(currentUser.getString(Keys.FIRST_NAME));
		mLastName.setText(currentUser.getString(Keys.LAST_NAME));
		mEmailAddress.setText(currentUser.getEmail());

		mDiscardButon.setOnClickListener(discardButtonClickListener);
		mSaveButton.setOnClickListener(saveButtonClickListener);
	}

	private OnClickListener discardButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	private OnClickListener saveButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			String firstName = mFirstName.getText().toString();
			String lastName = mLastName.getText().toString();

			setProgressBarIndeterminateVisibility(true);
			currentUser.put(Keys.FIRST_NAME, firstName);
			currentUser.put(Keys.LAST_NAME, lastName);
			currentUser.saveInBackground(saveCallback);

		}
	};

	private SaveCallback saveCallback = new SaveCallback() {
		@Override
		public void done(ParseException e) {
			setProgressBarIndeterminateVisibility(false);
			if (e == null) {
				Toast.makeText(EditProfile.this, "Profile saved!",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(EditProfile.this, "ERROR!", Toast.LENGTH_LONG)
						.show();
			}
		}
	};

}
