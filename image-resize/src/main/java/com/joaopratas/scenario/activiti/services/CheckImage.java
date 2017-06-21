package com.joaopratas.scenario.activiti.services;

import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * CheckImage - Checks if image exists in filesystem
 * <p>
 * Created by Joao Pratas on 2017-06-19.
 */
public class CheckImage implements JavaDelegate {
    private static final String CLASS_NAME = CheckImage.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    private Expression customDirectory;
    private Expression contextVar;

    @Override
    public void execute( final DelegateExecution delegateExecution ) {
        LOGGER.info( Constants.START );
        LOGGER.info( "Checking existing image..." );

        final String directory = (String) this.customDirectory.getValue( delegateExecution );
        final String fileName = (String) delegateExecution.getVariable( Constants.FILE_NAME );
        final String destinationFile = ServiceUtils.getDestinationFilepath( fileName, directory );
        final File image = new File( destinationFile );

        final String varContext = (String) this.contextVar.getValue( delegateExecution );

        final boolean imageExists = image.exists();
        delegateExecution.setVariable( varContext, imageExists );

        if ( imageExists ) {
            LOGGER.info( "Image " + directory + " exists: " + directory );
        } else {
            LOGGER.info( "Image " + directory + " does not exist." );
        }

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Setting context variables:" );
            LOGGER.trace( varContext + ": " + delegateExecution.getVariable( varContext ) );
        }

        LOGGER.info( Constants.END );
    }
}
