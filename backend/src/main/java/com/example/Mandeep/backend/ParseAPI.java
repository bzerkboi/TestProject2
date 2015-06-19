package com.example.Mandeep.backend;

import com.google.api.server.spi.response.UnauthorizedException;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Mandeep on 6/13/2015.
 */
public interface ParseAPI {

    @Headers({
            "X-Parse-Revocable-Session: 1",
            "Content-Type: application/json"
    })
    @POST("/1/users")
    UserSignupResponse ParseSignupUser(@Body UserSignupRequest userSignupRequest);

    @GET("/1/users/me")
    ParseUser ValidateUserSessionGetCurrentUser(@Header("X-Parse-Session-Token") String parseSessionToken) throws UnauthorizedException;

    @Headers("Content-Type: application/json")
    @POST("/1/classes/PeopleInfo")
    CreateObjectResponse InsertPersonInfo(@Body PersonInfo personInfo);


}
