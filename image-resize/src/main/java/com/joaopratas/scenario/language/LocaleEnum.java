package com.joaopratas.scenario.language;

/**
 * Internationalization com.joaopratas.com.joaopratas.scenario.language.LocaleEnum
 *
 * Created by Joao Pratas on 2017-06-16.
 */
public enum LocaleEnum {

    EN( "en" ),
    US( "en" ),
    PT( "pt" ),
    BR( "pt" );

    private final String languageCode;

    LocaleEnum( final String languageCode ) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public String getCountryCode() {
        return this.name();
    }
}
