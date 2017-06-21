package com.joaopratas.scenario.activiti.services;

import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceUtils;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * ResizeImage - Resizes image to given max width / height
 * <p>
 * Created by Joao Pratas on 2017-06-19.
 */
public class ResizeImage implements JavaDelegate {
    private static final String CLASS_NAME = ResizeImage.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    @Override
    public void execute( final DelegateExecution delegateExecution ) {
        LOGGER.info( Constants.START );
        LOGGER.info( "Resizing image..." );

        final int maxSize = (int) delegateExecution.getVariable( Constants.IMAGE_MAX_SIZE );
        final String fileName = (String) delegateExecution.getVariable( Constants.FILE_NAME );
        final String destinationFile = ServiceUtils.getDestinationFilepath( fileName, Constants.ORIGINAL );
        final String format = fileName.substring( fileName.lastIndexOf( Constants.DOT ) + 1 );
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            final BufferedImage image = ImageIO.read( new File( destinationFile ) );
            final BufferedImage thumbnail = Scalr.resize( image, maxSize );

            ImageIO.write( thumbnail, format, byteArrayOutputStream );
            byteArrayOutputStream.flush();
            final byte[] thumbnailBytes = byteArrayOutputStream.toByteArray();

            delegateExecution.setVariable( Constants.IMAGE_OBJECT, thumbnailBytes );
        } catch ( IOException e ) {
            //TODO: check how to throw custom exceptions with Activiti API
            LOGGER.error( e );
            throw new BpmnError( "An error occurred while resizing the image" );
        }

        LOGGER.info( "Image resized successfully" );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Setting context variables:" );
            LOGGER.trace( Constants.IMAGE_OBJECT + ": " + delegateExecution.getVariable( Constants.IMAGE_OBJECT ) );
        }

        LOGGER.info( Constants.END );
    }
}
