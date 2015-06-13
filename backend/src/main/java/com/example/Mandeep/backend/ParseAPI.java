package com.example.Mandeep.backend;

import retrofit.Callback;
import retrofit.http.Body;
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
}
