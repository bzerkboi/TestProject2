package com.example.Mandeep.backend;

import com.google.api.server.spi.response.UnauthorizedException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mandeep on 6/15/2015.
 */
public class MyErrorHandler implements ErrorHandler {
    @Override public Throwable handleError(RetrofitError cause) {
        ParseError error= (ParseError) cause.getBodyAs(ParseError.class);
        if (error != null && error.code == ParseException.HTTPErrorCodes.InvalidSessionToken.errorCode()) { //InvalidSessionToken
            return new UnauthorizedException(error.error_description);
        }
        else
        {
            return cause;
        }
    }
}
