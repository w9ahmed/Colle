package com.asyn.buscardnfc;

import com.asyn.buscardnfc.tools.InputsCheck;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends Activity {

	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mSigninButton;
	protected TextView mSignupTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.signin_activity);

		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mSigninButton = (Button) findViewById(R.id.signInButton);
		mSignupTextView = (TextView) findViewById(R.id.signupLabel);

		mSigninButton.setOnClickListener(signinButtonClickListener);
		mSignupTextView.setOnClickListener(signupTextViewClickListener); //
	}

	private OnClickListener signupTextViewClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SignInActivity.this,
					SignupActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener signinButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String username = mUsername.getText().toString();
			username = username.trim();

			String password = mPassword.getText().toString();
			password = password.trim();

			if (username.isEmpty() || password.isEmpty())
				InputsCheck.displayDialog(SignInActivity.this,
						"Make sure you enter your username and password.");
			else {
				setProgressBarIndeterminateVisibility(true);
				ParseUser.logInInBackground(username, password, logInCallback);
			}

		}
	};
	
	private LogInCallback logInCallback = new LogInCallback() {
		@Override
		public void done(ParseUser user, ParseException e) {
			setProgressBarIndeterminateVisibility(false);
			if(e == null) {
				// SUCCESS
				Intent intent = new Intent(SignInActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			} else {
				InputsCheck.displayDialog(SignInActivity.this, "Unable to Sign in");
			}
		}
	};
}
