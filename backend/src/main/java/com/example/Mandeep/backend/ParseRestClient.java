package com.example.Mandeep.backend;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mandeep on 6/13/2015.
 */
public class ParseRestClient {
    private static ParseAPI Parse_REST_CLIENT;
    private static String ROOT = "https://api.parse.com";
    static {
        setupRestClient();
    }

    private ParseRestClient() {}

    public static ParseAPI get() {
        return Parse_REST_CLIENT;
    }

    private static void setupRestClient() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Parse-Application-Id","pVFihPrMiG1wW239c5KBGXvMUYhNUbHTAXcSa4oF");
                request.addHeader("X-Parse-REST-API-Key","TgK1hYyMEOBm6LsL6ZkDEY3hKKWIpH3Hu21DxUVh");
            }
        };

        RestAdapter builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .build();

        Parse_REST_CLIENT = builder.create(ParseAPI.class);
    }
}
