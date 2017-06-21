package com.joaopratas.scenario.controller;

import com.joaopratas.scenario.activiti.processes.ResizeImageProcess;
import com.joaopratas.scenario.Constants;
import com.joaopratas.scenario.ServiceExeption;
import com.joaopratas.scenario.ServiceUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApplicationRestController - Handles REST requests
 * <p>
 * Created by Joao Pratas on 2017-06-16.
 */
@RestController
public class ApplicationRestController {
    private static final String CLASS_NAME = ApplicationRestController.class.getName();
    private static final Logger LOGGER = LogManager.getLogger( CLASS_NAME );

    @Autowired
    private
    ResizeImageProcess imageProcessor;

    /**
     * REST service to resize an image for a given URL and size using Activiti orchestration
     *
     * @param url       Image URL (required)
     * @param maxWidth  Max width to apply on resized image (optional)
     * @param maxHeight Max height to apply on resized image (optional)
     *
     * @return image bytes
     */
    @RequestMapping (value = "/resizeImage", method = RequestMethod.GET, produces = "image/jpg")
    public HttpEntity<byte[]> resizeImage( @RequestParam ("url") final String url,
                                           @RequestParam (value = "width", required = false) final String maxWidth,
                                           @RequestParam (value = "height", required = false) final String maxHeight ) throws ServiceExeption {
        LOGGER.info( Constants.START );
        LOGGER.info( "Got resize image REST request" );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Requested parms:" );
            LOGGER.trace( "url: " + url );
            LOGGER.trace( "width: " + maxWidth );
            LOGGER.trace( "height: " + maxHeight );
        }

        this.imageProcessor.executeProcess( url, maxWidth, maxHeight );

        final HttpEntity<byte[]> httpEntity = ServiceUtils.getResponseHttpEntity( ServiceUtils.getOptimizedImageBytes( url ), MediaType.IMAGE_JPEG );

        LOGGER.info( Constants.END );

        return httpEntity;
    }

}
