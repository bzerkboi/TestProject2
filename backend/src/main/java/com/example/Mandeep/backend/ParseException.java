package com.example.Mandeep.backend;

import com.google.api.server.spi.ServiceException;

/**
 * Created by Mandeep on 6/18/2015.
 */
public class ParseException extends Exception {

    public enum HTTPErrorCodes{
        InvalidSessionToken (209);

        private final int errorCode;
        HTTPErrorCodes(int errorCode)
        {
            this.errorCode=errorCode;
        }

        public int errorCode() { return errorCode;}
    }
}
