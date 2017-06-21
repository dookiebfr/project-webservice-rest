package com.joaopratas.scenario.language;

import java.util.Locale;

/**
 * Locale Message Interface
 *
 * Created by Joao Pratas on 2017-06-16.
 */
public interface LocaleMessage {
    String getMessage( final String key );

    String getMessage( final String key, final Object[] parameters );

    void setLocale( final Locale locale );

    Locale getLocale();

    void setBasename( String resourceBundle );
}
