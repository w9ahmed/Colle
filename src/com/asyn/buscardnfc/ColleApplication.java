package com.asyn.buscardnfc;

import android.app.Application;

import com.parse.Parse;

public class ColleApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "ZbdVW34aOiLw32J2m3kQFZpRekE2iNxVZrJzsOSa", "aeyijxgjEoqEaMEv7pFhQIS1iKh8MkmkX3v9ZDKo");
	}

}
