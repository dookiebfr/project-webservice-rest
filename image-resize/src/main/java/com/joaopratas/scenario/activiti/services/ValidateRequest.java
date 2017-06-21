package com.joaopratas.scenario.activiti.services;

import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ValidateRequest - Checks for a valid URL
 * <p>
 * Created by Joao Pratas on 2017-06-19.
 */
public class ValidateRequest implements JavaDelegate {
    private static final String CLASS_NAME = ValidateRequest.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    @Override
    public void execute( final DelegateExecution delegateExecution ) {
        LOGGER.info( Constants.START );
        LOGGER.info( "Validating request..." );

        this.validateURL( delegateExecution );
        this.validateMaxSize( delegateExecution );

        final boolean isValidRequest = (boolean) delegateExecution.getVariable( Constants.IS_VALID_REQUEST );
        if ( isValidRequest ) {
            LOGGER.info( "Request validated successfully." );
        } else {
            LOGGER.info( "Request is invalid." );
        }

        final String imageUrl = (String) delegateExecution.getVariable( Constants.IMAGE_URL );
        final String fileName = ServiceUtils.getFileNameFromURL( imageUrl );
        delegateExecution.setVariable( Constants.FILE_NAME, fileName );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Setting context variables:" );
            LOGGER.trace( Constants.IS_VALID_REQUEST + ": " + delegateExecution.getVariable( Constants.IS_VALID_REQUEST ) );
            LOGGER.trace( Constants.FILE_NAME + ": " + delegateExecution.getVariable( Constants.FILE_NAME ) );
        }

        LOGGER.info( Constants.END );
    }

    private void validateMaxSize( final DelegateExecution delegateExecution ) {
        LOGGER.info( "Validating image max size..." );
        final int imageMaxSize = (int) delegateExecution.getVariable( Constants.IMAGE_MAX_SIZE );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "imageMaxSize: " + imageMaxSize );
        }

        if ( 0 >= imageMaxSize ) {
            delegateExecution.setVariable( Constants.IS_VALID_REQUEST, false );
        }
    }

    private void validateURL( final DelegateExecution delegateExecution ) {
        LOGGER.info( "Validating URL..." );
        final String imageUrl = (String) delegateExecution.getVariable( Constants.IMAGE_URL );

        try {
            final URL url = new URL( imageUrl );

            if ( LOGGER.isDebugEnabled() ) {
                LOGGER.debug( "Got a valid URL: " + url );
            }

            if ( LOGGER.isTraceEnabled() ) {
                LOGGER.trace( "Protocol: " + url.getProtocol() );
                LOGGER.trace( "Host: " + url.getHost() );
                LOGGER.trace( "Port: " + url.getPort() );
                LOGGER.trace( "Endpoint: " + url.getPath() );
            }

            delegateExecution.setVariable( Constants.IS_VALID_REQUEST, true );
        } catch ( final MalformedURLException e ) {
            LOGGER.error( "An error occurred while building the image URL: " + imageUrl, e );
            delegateExecution.setVariable( Constants.IS_VALID_REQUEST, false );
        }
    }
}
