package com.example.Mandeep.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mandeep on 6/15/2015.
 */
public class ParseError {
    @SerializedName("code")
    public int code;
    @SerializedName("error")
    public String error;
    @SerializedName("error_description")
    public String error_description;
}
