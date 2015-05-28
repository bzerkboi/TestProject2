/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.Mandeep.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;
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
    @ApiMethod(name = "insertPerson", httpMethod="post")
    public void insertPerson(@Named("name") String name,@Named("age") String age) {
        MyBean personToAdd = new MyBean();
        personToAdd.name=name;
        personToAdd.age=age;

        ofy().save().entity(personToAdd).now();
    }

}
