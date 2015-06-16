/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Mandeep.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.ThreadManager;


import javax.inject.Named;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

import static com.example.Mandeep.backend.OfyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(name = "mandeepAPI", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.Mandeep.example.com", ownerName = "backend.Mandeep.example.com", packagePath = ""))
public class MyEndpoint {

    public MyEndpoint()
    {

    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "insertPersonObjectify", httpMethod="post")
    public void insertPersonObjectify(@Named("name") String name,@Named("age") String age) {
        //This api end point is for adding a new person using google datastore with objectify
        MyBean personToAdd = new MyBean();
        personToAdd.name=name;
        personToAdd.age=age;

        ofy().save().entity(personToAdd).now();
    }

    @ApiMethod(name = "userSignup", httpMethod = "post")
    public UserSignupResponse userSignup(@Named ("userName") final String userName, @Named ("password") final String password)
    {

        try {

            //Get a subset of the response
            UserSignupResponse response = ParseRestClient.get().ParseSignupUser(new UserSignupRequest(userName, password, "")); //we need to send a json version of the user
            //response.setSuccess(true);
            //String responseParsed = new String(((TypedByteArray) response.getBody()).getBytes()); <--to get the raw http response
            return response;
        }
        catch (Exception ex)
        {
            UserSignupResponse response = new UserSignupResponse();
            response.setSuccess(false);
            return response;
        }

    }

    //Need to specify path because the session token has a colon in it and it doesn't parse well in the URL.
    @ApiMethod(name="verifyAndGetUser", httpMethod = HttpMethod.GET, path="verifyAndGetUser")
    public ParseUser verifyAndGetUser(@Named("parseSessionToken") final String parseSessionToken)
    {
        try {

            ParseUser parseUser = ParseRestClient.get().ValidateUserSessionGetCurrentUser(parseSessionToken);
            return parseUser;
        }
        catch (Exception ex)
        {
            ParseUser parseUser = new ParseUser();
            return parseUser;
        }
    }

}
