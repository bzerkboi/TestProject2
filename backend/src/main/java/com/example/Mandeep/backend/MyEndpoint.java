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
import com.google.api.server.spi.response.UnauthorizedException;
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

    public MyEndpoint() {

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
    public ParseUser verifyAndGetUser(@Named("parseSessionToken") final String parseSessionToken) throws UnauthorizedException {
        try {

            ParseUser parseUser = ParseRestClient.get().ValidateUserSessionGetCurrentUser(parseSessionToken);
            return parseUser;
        }
        catch (UnauthorizedException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            ParseUser parseUser = new ParseUser();
            return parseUser;
        }
    }

    @ApiMethod(name="insertPersonInfo", httpMethod = HttpMethod.POST, path="insertPersonInfo")
    public CreateObjectResponse InsertPersonInfo(@Named("personName") String personName, @Named("personAge") int personAge)
    {
        try
        {
            PersonInfo personInfo=new PersonInfo();
            personInfo.setPersonName(personName);
            personInfo.setPersonAge(personAge);
            CreateObjectResponse createObjectResponse=ParseRestClient.get().InsertPersonInfo(personInfo);
            return createObjectResponse;
        }
        catch (Exception ex)
        {
            System.out.print(ex.toString());
            CreateObjectResponse createObjectResponse=new CreateObjectResponse();
            return createObjectResponse;
        }
    }

}
