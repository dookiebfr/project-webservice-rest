package com.joaopratas.scenario.activiti.storage;

import java.io.IOException;

/**
 * StoreImageFile - Stores image file
 * <p>
 * Created by jcfpr on 2017-06-21.
 */
public interface StoreImageFile {
    void store( final byte[] imageBytes, final String destinationFile ) throws IOException;
}
