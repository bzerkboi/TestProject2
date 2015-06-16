package com.example.mandeep.testproject2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mandeep.backend.mandeepAPI.MandeepAPI;
import com.example.mandeep.backend.mandeepAPI.model.ParseUser;
import com.example.mandeep.backend.mandeepAPI.model.UserSignupResponse;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

//DispatchActivity: handles starting the logged in activity, MainActivity, or logged out activity,
// WelcomeActivity. This activity is the main entry point for the app.
public class DispatchActivity extends Activity {
    public static MandeepAPI myApiService=null;
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dispatch);

        //We need to determine if the user using the phone has a valid session token
        //Lets use SharedPreferences to save the session token
        new ValidateUserSessionGetCurrentUser(this).execute();
    }

    class ValidateUserSessionGetCurrentUser extends AsyncTask<Void, Void,Integer> {

        private Context context;

        ValidateUserSessionGetCurrentUser(Context context) {
            this.context=context;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            //Create the API service to talk to backend if it hasn't already been created
            if (myApiService == null) {
               MandeepAPI.Builder builder = new MandeepAPI.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
               /* MandeepAPI.Builder builder = new MandeepAPI.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://studied-flow-95401.appspot.com/_ah/api/");*/

                myApiService = builder.build();
            }

            try {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); //0 means readable mode_private
                String parseSessionToken = settings.getString("parseSessionToken", null); //return null if the key doesn't exist

                if (parseSessionToken != null) {
                    //we have a session token saved, now try to see if valid user
                    ParseUser parseUser = myApiService.verifyAndGetUser(parseSessionToken).execute();
                    if (parseUser != null) {
                        //we got back a valid user
                        startActivity(new Intent(context, MainActivity.class)); //The user is logged in, no need to get them to login or signup
                    } else {
                        startActivity(new Intent(context, WelcomeActivity.class));
                    }
                } else {
                    //The user either doesn't exisit or we need to re-log them in.
                    startActivity(new Intent(context, WelcomeActivity.class));
                }

                return 1;
            } catch (Exception ex) {
                System.out.println(ex);

                return  0;
            }
        }
    }
}
