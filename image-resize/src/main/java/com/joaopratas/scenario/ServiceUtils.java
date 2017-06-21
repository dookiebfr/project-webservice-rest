package com.joaopratas.scenario;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * ServiceUtils - utils methods to support rest service execution
 * <p>
 * Created by Joao Pratas on 2017-06-16.
 */
@Component
public class ServiceUtils {
    private static final String CLASS_NAME = ServiceUtils.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    /**
     * Gets HttpEntity for a given byte array and media type
     *
     * @param bytes Object byte array
     * @param type  Object media type
     *
     * @return HttpEntity
     */
    public static HttpEntity<byte[]> getResponseHttpEntity( final byte[] bytes, final MediaType type ) {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Getting HttpEntity<byte[]> response..." );
        }

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType( type );
        httpHeaders.setContentLength( bytes.length );

        final HttpEntity<byte[]> httpEntity = new HttpEntity<>( bytes, httpHeaders );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "HttpEntity<byte[]> ready:" );
            LOGGER.trace( "ContentType: " + type );
            LOGGER.trace( "ContentLength: " + bytes.length );
        }

        return httpEntity;
    }

    /**
     * Gets filename from URL
     *
     * @param url URL
     *
     * @return filename
     */
    public static String getFileNameFromURL( final String url ) {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Getting Filename from URL: " );
            LOGGER.trace( "URL: " + url );
        }

        final String fileName = url.substring( url.lastIndexOf( Constants.SLASH ) + 1 );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Filename: " + fileName );
        }

        return fileName;
    }

    /**
     * Gets destination filepath for a given file and customDestination
     *
     * @param fileName        Filename
     * @param customDirectory Custom directory
     *
     * @return Destination path
     */
    public static String getDestinationFilepath( final String fileName, final String customDirectory ) {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Getting destination file path: " );
            LOGGER.trace( "Filename: " + fileName );
            LOGGER.trace( "Custom directory: " + customDirectory );
        }

        final String destinationFilePath = File.separator + Constants.IMAGES + File.separator + customDirectory + File.separator + fileName;

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Destination file path: " + destinationFilePath );
        }

        return destinationFilePath;
    }

    /**
     * Gets Image max size considering the default properties or provided values
     *
     * @param maxWidth      Provided Max Width
     * @param maxHeight     Provided Max Height
     * @param defaultWidth  Default Max Width
     * @param defaultHeight Default Max Height
     *
     * @return Image Max Size
     */
    public static int getImageMaxSize( final String maxWidth, final String maxHeight, final String defaultWidth, final String defaultHeight ) {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Getting Image max size: " );
            LOGGER.trace( "maxWidth: " + maxWidth );
            LOGGER.trace( "maxHeight: " + maxHeight );
            LOGGER.trace( "defaultWidth: " + defaultWidth );
            LOGGER.trace( "defaultHeight: " + defaultHeight );
        }

        int maxSize = 0;

        if ( StringUtils.isNotBlank( maxWidth ) || StringUtils.isNotBlank( maxHeight ) ) {
            maxSize = getImageMaxSize( maxWidth, maxHeight );
        } else if ( StringUtils.isNotBlank( defaultWidth ) || StringUtils.isNotBlank( defaultHeight ) ) {
            maxSize = getImageMaxSize( defaultWidth, defaultHeight );
        }

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Image max size: " + maxSize );
        }

        return maxSize;
    }

    /**
     * Gets max size between width and height
     *
     * @param width  Width
     * @param height Height
     *
     * @return Max size
     */
    private static int getImageMaxSize( final String width, final String height ) {
        int mW = 0;
        int mH = 0;

        if ( StringUtils.isNotBlank( width ) ) {
            mW = Integer.parseInt( width );
        }

        if ( StringUtils.isNotBlank( height ) ) {
            mH = Integer.parseInt( height );
        }

        return ( mW > mH ) ? mW : mH;
    }

    /**
     * Gets byte array from optimized image file
     *
     * @param url Image URL
     *
     * @return byte array
     *
     * @throws ServiceExeption Service error
     */
    public static byte[] getOptimizedImageBytes( final String url ) throws ServiceExeption {
        final String fileName = getFileNameFromURL( url );
        final String destinationFilepath = getDestinationFilepath( fileName, Constants.THUMBNAIL );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Getting optimized image bytes: " );
            LOGGER.trace( "url: " + url );
            LOGGER.trace( "fileName: " + fileName );
            LOGGER.trace( "destinationFilepath: " + destinationFilepath );
        }

        InputStream targetStream = null;
        final byte[] imageBytes;
        try {
            targetStream = new FileInputStream( new File( destinationFilepath ) );
            imageBytes = IOUtils.toByteArray( targetStream );
        } catch ( final IOException e ) {
            LOGGER.error( "Error while reading thumbnail image from filesystem." );
            throw new ServiceExeption( e );
        } finally {
            closeStream( targetStream );
        }

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Optimized image bytes (lenght): " + imageBytes.length );
        }

        return imageBytes;
    }

    /**
     * Closes stream
     *
     * @param stream Closeable stream
     *
     * @throws ServiceExeption Service Error
     */
    private static void closeStream( final Closeable stream ) throws ServiceExeption {
        try {
            if ( null != stream ) {
                stream.close();
            }
        } catch ( final IOException e ) {
            LOGGER.error( "Error while closing stream." );
            throw new ServiceExeption( e );
        }
    }
}
