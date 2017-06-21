package com.joaopratas.scenario.language;

import com.joaopratas.scenario.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

/**
 * Internationalization
 * <p>
 * Created by Joao Pratas on 2017-06-16.
 */
@Service
public class LocaleMessageImpl extends ResourceBundleMessageSource implements LocaleMessage {
    private static final String CLASS_NAME = LocaleMessageImpl.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    private Locale locale;

    public LocaleMessageImpl() {
        LOGGER.info( Constants.START );

        this.setBasename( Constants.MESSAGE );
        LOGGER.debug( StringUtils.join( Arrays.asList(
                this.getMessage( "setting.default.resource.bundle" ), Constants.MESSAGE ),
                Constants.SPACE
        ) );

        this.locale = new Locale( LocaleEnum.EN.getLanguageCode(), LocaleEnum.EN.getCountryCode() );
        LOGGER.debug( StringUtils.join( Arrays.asList(
                this.getMessage( "setting.default.locale" ),
                LocaleEnum.EN.getLanguageCode(),
                LocaleEnum.EN.getCountryCode() ),
                Constants.SPACE
        ) );

        LOGGER.info( Constants.END );
    }

    public String getMessage( final String key ) {
        return this.getMessage( key, null, this.locale );
    }

    public String getMessage( final String key, final Object[] parameters ) {
        return this.getMessage( key, parameters, this.locale );
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale( final Locale locale ) {
        this.locale = locale;
    }
}
