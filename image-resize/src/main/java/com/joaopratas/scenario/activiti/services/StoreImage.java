package com.joaopratas.scenario.activiti.services;

import com.joaopratas.scenario.activiti.storage.StoreImageFactory;
import com.joaopratas.scenario.activiti.storage.StoreImageFile;
import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceUtils;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * StoreImage - Stores image on filesystem
 * <p>
 * Created by Joao Pratas on 2017-06-19.
 */
//@Component
public class StoreImage implements JavaDelegate {
    private static final String CLASS_NAME = StoreImage.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    private Expression customDirectory;

    // injection is not working with JavaDelegate :(
//    @Autowired
//    private
//    StoreImageFile storeImageFile;

    @Override
    public void execute( final DelegateExecution delegateExecution ) {
        LOGGER.info( Constants.START );
        LOGGER.info( "Storing image..." );

        final byte[] imageBytes = (byte[]) delegateExecution.getVariable( Constants.IMAGE_OBJECT );
        final String fileName = (String) delegateExecution.getVariable( Constants.FILE_NAME );
        final String directory = (String) this.customDirectory.getValue( delegateExecution );
        final String destinationFile = ServiceUtils.getDestinationFilepath( fileName, directory );
        final String format = destinationFile.substring( destinationFile.lastIndexOf( Constants.DOT ) + 1 );

        try {
            //TODO: find out how to inject on JavaDelegate...
            final StoreImageFile storeImageFile = StoreImageFactory.getStoreImage( Constants.FILESYSTEM );
            storeImageFile.store( imageBytes, destinationFile );
        } catch ( final IOException e ) {
            //TODO: check how to throw custom exceptions with Activiti API
            LOGGER.error( e );
            throw new BpmnError( "An error occurred while storing the image" );
        }

        LOGGER.info( "Image stored to filesystem successfully." );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Format: " + format );
            LOGGER.trace( "DestinationFile: " + destinationFile );
        }

        LOGGER.info( Constants.END );
    }

}
