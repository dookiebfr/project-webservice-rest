package com.joaopratas.scenario;

/**
 * ServiceExeption - Custom Exception
 * <p>
 * Created by Joao Pratas on 2017-06-16.
 */
public class ServiceExeption extends Exception {

    public ServiceExeption() {
    }

    public ServiceExeption( String message ) {
        super( message );
    }

    public ServiceExeption( Throwable cause ) {
        super( cause );
    }

    public ServiceExeption( String message, Throwable cause ) {
        super( message, cause );
    }
}
