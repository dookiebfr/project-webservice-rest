package com.joaopratas.scenario.language;

/**
 * Locale Exception
 *
 * Created by Joao Pratas on 2017-06-16.
 */
public class LocaleException extends Exception {
    private static final long serialVersionUID = -1797874063113209434L;

    public LocaleException( final String message ) {
        super( message );
    }

    public LocaleException( final String message, final Throwable error ) {
        super( message, error );
    }
}
