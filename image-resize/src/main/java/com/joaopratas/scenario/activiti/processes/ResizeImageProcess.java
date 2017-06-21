package com.joaopratas.scenario.activiti.processes;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * ResizeImageProcess - Abstract class
 * <p>
 * Created by jcfpr on 2017-06-21.
 */
public abstract class ResizeImageProcess {
    static final String IMAGE_RESIZE_FLOW = "imageResizeFlow";
    static final int HARDCODED_MAX_SIZE = 800;

    //    @Autowired
//    private LocaleMessageImpl localeMessage;

    @Autowired
    Environment environment;

    @Autowired
    RuntimeService runtimeService;

    /**
     * Executes ResizeImageProcess that downloads image from URL
     * and stores original and optimized versions on filesystem.
     * <p>
     * In case an optimized version already exists only return it's bytes.
     * <p>
     * If only the original version exists then it will resize, storage and return
     * an optimized version.
     *
     * @param url       Image URL
     * @param maxWidth  Max width
     * @param maxHeight Max height
     */
    public abstract void executeProcess( final String url, final String maxWidth, final String maxHeight );
}
