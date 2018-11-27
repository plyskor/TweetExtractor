
package es.uam.eps.tweetextractor.server;

import javax.jws.WebService;

@WebService(endpointInterface = "es.uam.eps.tweetextractor.server.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

