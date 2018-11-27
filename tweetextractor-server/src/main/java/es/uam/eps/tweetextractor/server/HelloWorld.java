package es.uam.eps.tweetextractor.server;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
    String sayHi(String text);
}

