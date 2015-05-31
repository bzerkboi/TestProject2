package com.example.mandeep.testproject2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

//DispatchActivity: handles starting the logged in activity, MainActivity, or logged out activity,
// WelcomeActivity. This activity is the main entry point for the app.
public class DispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dispatch);

        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) { //This will grab information from the cache to see if the user has already been logged in or not
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class)); //The user is logged in, no need to get them to login or signup
        } else {
            // Start an intent for the logged out activity
            //The user is not cached so they either have to signup or login, which is the same
            // //screen as if they logged out
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }
}
