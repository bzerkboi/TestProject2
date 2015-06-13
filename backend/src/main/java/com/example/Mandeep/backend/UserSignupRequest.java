package com.example.Mandeep.backend;

/**
 * Created by Mandeep on 6/13/2015.
 */
public class UserSignupRequest {

    final String username;
    final String password;
    final String phone;

    UserSignupRequest (String username, String password, String phone)
    {
        this.username=username;
        this.password=password;
        this.phone="";
    }
}
