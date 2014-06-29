package com.asyn.buscardnfc;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ManageCards extends ListActivity {

	public static final int LIMIT = 100;

	private List<ParseUser> mUsers;
	protected ParseRelation<ParseUser> mRelation;
	protected ParseUser mCurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.manage_cards_activity);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mRelation = mCurrentUser.getRelation(Keys.USER_RELATION);
		
		setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByAscending(Keys.USERNAME);
		query.setLimit(LIMIT);
		query.findInBackground(findCallback);

	}

	FindCallback<ParseUser> findCallback = new FindCallback<ParseUser>() {
		@Override
		public void done(List<ParseUser> users, ParseException e) {
			setProgressBarIndeterminateVisibility(false);
			if (e == null) {
				mUsers = users;
				String[] usernames = new String[mUsers.size()];
				int i = 0;
				for (ParseUser user : mUsers) {
					usernames[i] = user.getUsername();
					i++;
				} // end foreach

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						ManageCards.this,
						android.R.layout.simple_list_item_checked, usernames);
				setListAdapter(adapter);
			}
		}
	};

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(getListView().isItemChecked(position)) {
			mRelation.add(mUsers.get(position));
			mCurrentUser.saveInBackground(saveCallback);
		} else {
			
		}
	};
	
	SaveCallback saveCallback = new SaveCallback() {		
		@Override
		public void done(ParseException e) {
			
		}
	};
	
}
