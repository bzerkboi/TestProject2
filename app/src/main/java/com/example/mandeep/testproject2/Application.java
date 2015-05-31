package com.example.mandeep.testproject2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseInstallation;

/*
This class is used as a starting point for the application
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "pVFihPrMiG1wW239c5KBGXvMUYhNUbHTAXcSa4oF", "8U4BQahvk7CC3wkU46yhLLtTpfe02fmZ00BsOxnP");
    }
}
