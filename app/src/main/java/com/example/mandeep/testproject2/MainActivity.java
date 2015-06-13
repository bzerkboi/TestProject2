package com.example.mandeep.testproject2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.mandeep.backend.mandeepAPI.MandeepAPI;
import com.example.mandeep.backend.mandeepAPI.model.AddPersonResponse;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.parse.Parse;
import com.parse.ParseObject;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertPerson(View v)
    {
        EditText personText=(EditText)findViewById(R.id.nameText);
        EditText ageText=(EditText)findViewById(R.id.ageText);
        new EndpointsAsyncTask(this, personText.getText().toString(),ageText.getText().toString()).execute();
    }
}

class EndpointsAsyncTask extends AsyncTask<Void, Void,Integer>
{
    private Context context;
    private String personName, personAge;
    private static MandeepAPI myApiService=null;

    EndpointsAsyncTask(Context context, String personName, String personAge)
    {
        this.context=context;
        this.personName=personName;
        this.personAge=personAge;

    }

    @Override
    protected Integer doInBackground(Void... params) {


        if(myApiService == null) { // Only do this once
            /*QuoteEndpoint.Builder builder = new QuoteEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            MandeepAPI.Builder builder = new MandeepAPI.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            /*MandeepAPI.Builder builder = new MandeepAPI.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://studied-flow-95401.appspot.com/_ah/api/");*/

// end options for devappserver

            myApiService = builder.build();
        }

            //Insert a person into the DB
/*
            try
            {
                // Set up a progress dialog
                //final ProgressDialog dialog = new ProgressDialog();
                //dialog.setMessage("Saving person...");
                //dialog.show();
                //AddPersonResponse addPersonResponse=myApiService.insertPersonParse(personName,personAge).execute();
               // dialog.dismiss();
            }
            catch (IOException e)
            {
                return 0;
            }
*/

        return 0;
    }
}
