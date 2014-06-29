package com.asyn.buscardnfc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

public class MainActivity extends Activity {

	public static final String SPACE = " ";
	protected TextView welcomeTextView;

	private ParseUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		welcomeTextView = (TextView) findViewById(R.id.welcomeUserTextView);

		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			navigateToSignin();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (currentUser != null)
			welcomeTextView.setText(getString(R.string.welcome_main_screen)
					+ SPACE + currentUser.getString(Keys.FIRST_NAME) + "!");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
			case R.id.action_logout:
				ParseUser.logOut();
				navigateToSignin();
				break;
			case R.id.action_edit_profile:
				intent = new Intent(this, EditProfile.class);
				startActivity(intent);
				break;
			case R.id.action_manage_cards:
				intent = new Intent(this, ManageCards.class);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void navigateToSignin() {
		Intent intent = new Intent(this, SignInActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

}
