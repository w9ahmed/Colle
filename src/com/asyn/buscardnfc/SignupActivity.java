package com.asyn.buscardnfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.asyn.buscardnfc.tools.InputsCheck;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends Activity {

	protected EditText mUsername;
	protected EditText mFirstName;
	protected EditText mLastName;
	protected EditText mEmailAddress;
	protected EditText mPassword;
	protected Button mSignupButton;
	protected Button mCancelButon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.signup_activity);

		mUsername = (EditText) findViewById(R.id.usernameField);
		mFirstName = (EditText) findViewById(R.id.firstNameField);
		mLastName = (EditText) findViewById(R.id.lastNameField);
		mEmailAddress = (EditText) findViewById(R.id.emailField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mSignupButton = (Button) findViewById(R.id.signupButton);
		mCancelButon = (Button) findViewById(R.id.cancelButton);

		mCancelButon.setOnClickListener(cancelButtonClickListener); //
		mSignupButton.setOnClickListener(signupButtonClickListener); //

	}

	/* Cancel Button onClickListener */
	private OnClickListener cancelButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	/* Sign up button onClickListener */
	private OnClickListener signupButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {			
			String username = mUsername.getText().toString();
			username = username.trim();

			String firstName = mFirstName.getText().toString();
			String lastName = mLastName.getText().toString();

			String emailAddress = mEmailAddress.getText().toString();
			emailAddress = emailAddress.trim();

			String password = mPassword.getText().toString();
			password = password.trim();

			if (username.isEmpty() || emailAddress.isEmpty()
					|| password.isEmpty())
				InputsCheck.displayDialog(SignupActivity.this,
						"Make sure you entered the correct information.");
			else {
				setProgressBarIndeterminateVisibility(true);
				ParseUser newUser = new ParseUser();
				newUser.setUsername(username);
				newUser.setPassword(password);
				newUser.setEmail(emailAddress);
				newUser.put(Keys.FIRST_NAME, firstName);
				newUser.put(Keys.LAST_NAME, lastName);
				newUser.signUpInBackground(signUpCallback);
			} // end else

		}
	};

	/* New User Sign up in background call back */
	private SignUpCallback signUpCallback = new SignUpCallback() {
		@Override
		public void done(ParseException e) {
			setProgressBarIndeterminateVisibility(false);
			if (e == null) {
				// SUCCESS
				Intent intent = new Intent(SignupActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			} else {
				InputsCheck.displayDialog(SignupActivity.this,
						"Please try again. " + e.getMessage());
			}
		}
	}; // end of signUpInBackground
}
