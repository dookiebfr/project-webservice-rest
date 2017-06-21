package com.joaopratas.scenario.activiti.services;

import com.joaopratas.scenario.Constants;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * DownloadImage - Downloads image from URL
 * <p>
 * Created by Joao Pratas on 2017-06-19.
 */
public class DownloadImage implements JavaDelegate {
    private static final String CLASS_NAME = DownloadImage.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    @Override
    public void execute( final DelegateExecution delegateExecution ) {
        LOGGER.info( Constants.START );
        LOGGER.info( "Downloading image..." );

        final String imageUrl = (String) delegateExecution.getVariable( Constants.IMAGE_URL );
        final String fileName = (String) delegateExecution.getVariable( Constants.FILE_NAME );

        try {
            final URL url = new URL( imageUrl );
            final String format = fileName.substring( fileName.lastIndexOf( Constants.DOT ) + 1 );
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final BufferedImage image = ImageIO.read( url );

            ImageIO.write( image, format, byteArrayOutputStream );
            byteArrayOutputStream.flush();

            final byte[] imageBytes = byteArrayOutputStream.toByteArray();

            delegateExecution.setVariable( Constants.IMAGE_OBJECT, imageBytes );
        } catch ( final IOException e ) {
            //TODO: check how to throw custom exceptions with Activiti API
            LOGGER.error( e );
            throw new BpmnError( "An error occurred while downloading the image" );
        }

        LOGGER.info( "Image downloaded successfully" );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Setting context variables:" );
            LOGGER.trace( Constants.IMAGE_OBJECT + ": " + delegateExecution.getVariable( Constants.IMAGE_OBJECT ) );
        }

        LOGGER.info( Constants.END );
    }
}
