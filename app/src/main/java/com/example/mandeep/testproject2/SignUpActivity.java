package com.example.mandeep.testproject2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mandeep.backend.mandeepAPI.model.UserSignupResponse;

import java.text.ParseException;

//This activity takes care of signing up the user
//It is called from the welcome activity, if the user choses to signup
public class SignUpActivity extends Activity {

    // Text input from the UI
    private EditText
            usernameEditText,
            passwordEditText,
            passwordAgainEditText;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //We need to first get the signup information
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        passwordAgainEditText = (EditText) findViewById(R.id.password_again_edit_text);

        //Now we will set up a button listner for when the user clicks signup
        Button mActionButton = (Button) findViewById(R.id.action_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(); //when the user clicks a button, we go to signup method
            }
        });
    }

    private void signup()
    {
        //We should trim the information to avoid extra white spaces
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        /*
        We should be doing some checking in here to ensure the user has inputed valid data.
        I am skipping it for now to speed up the demo
         */

        //At this point we are ready to register the user
        //Because the signup may take some time, we should throw up a progres dialog

        final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Signing you up!");
        dialog.show();
        new SignUpUserTask(this, username,password).execute();
        dialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    class SignUpUserTask extends AsyncTask<Void, Void,Integer> {

        private Context context;
        private String userName, userPassword;

        SignUpUserTask(Context context, String userName, String userPassword){
            this.context=context;
            this.userName=userName;
            this.userPassword=userPassword;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            //Now we can use the ParseUser object to setup the user and sign them up

            try {
                //Lets call the GAE endpoint to signup the user
                UserSignupResponse signupResponse= DispatchActivity.myApiService.userSignup(userName, userPassword).execute();

                //Now we need to write the session token to shared preferences
                SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("parseSessionToken", signupResponse.getSessionToken()); //write session token to memory so user doesn't have to sign up again
                editor.commit();

                Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                return 1;
            }
            catch (Exception ex)
            {
                return 0;
            }
        }
    }
}
