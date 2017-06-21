package com.joaopratas.scenario.activiti.processes;

import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ResizeImageProcess - Processes incoming request using Activiti orchestration
 * <p>
 * Created by jcfpr on 2017-06-21.
 */
@Component
public class ResizeImageProcessImpl extends ResizeImageProcess {
    private static final String CLASS_NAME = ResizeImageProcessImpl.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    public void executeProcess( final String url, final String maxWidth, final String maxHeight ) {
        //Loads default properties
        final String defaultWidth = this.environment.getProperty( "default.image.max.width" );
        final String defaultHeight = this.environment.getProperty( "default.image.max.height" );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Default values:" );
            LOGGER.trace( "default.image.max.width: " + defaultWidth );
            LOGGER.trace( "default.image.max.height: " + defaultHeight );
        }

        // Gets the image max size
        final int imageMaxSize = ServiceUtils.getImageMaxSize( maxWidth, maxHeight, defaultWidth, defaultHeight );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Calculated max size:" );
            LOGGER.trace( "max size: " + imageMaxSize );
        }

        // Sets Activiti context variables shared by all services
        final Map<String, Object> variables = new HashMap<>();
        variables.put( Constants.IMAGE_URL, url );
        variables.put( Constants.IMAGE_MAX_SIZE, imageMaxSize );

        // Starts proccess flow to generate thumbnail image
        this.runtimeService.startProcessInstanceByKey( IMAGE_RESIZE_FLOW, variables );
    }
}
