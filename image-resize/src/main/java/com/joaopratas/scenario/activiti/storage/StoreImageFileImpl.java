package com.joaopratas.scenario.activiti.storage;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Stores image bytes as a file on filesystem
 * <p>
 * Created by jcfpr on 2017-06-21.
 */
public class StoreImageFileImpl implements StoreImageFile {
    private static final String CLASS_NAME = StoreImageFileImpl.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    public void store( final byte[] imageBytes, final String destinationFile ) throws IOException {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Storing image on destination:" );
            LOGGER.trace( "imageBytes.length: " + imageBytes.length );
            LOGGER.trace( "destinationFile: " + destinationFile );
        }

        final File outputfile = new File( destinationFile );

        FileUtils.touch( outputfile );

        try ( OutputStream outputStream = new FileOutputStream( destinationFile ) ) {
            outputStream.write( imageBytes );
        }

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Image stored successfully" );
        }
    }
}
