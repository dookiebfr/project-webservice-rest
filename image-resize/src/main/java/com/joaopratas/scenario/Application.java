package com.joaopratas.scenario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * project-webservice-rest
 * <p>
 * Created by Joao Pratas on 2017-06-16.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure( final SpringApplicationBuilder application ) {
        return application.sources( Application.class );
    }

    public static void main( String[] args ) {
        SpringApplication.run( Application.class, args );
    }
}