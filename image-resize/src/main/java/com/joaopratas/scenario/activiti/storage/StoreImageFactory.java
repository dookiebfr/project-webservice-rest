package com.joaopratas.scenario.activiti.storage;

import com.joaopratas.scenario.Constants;
import org.apache.commons.lang3.StringUtils;

public class StoreImageFactory {
    public static StoreImageFile getStoreImage( final String type ) {
        if ( StringUtils.isBlank( type ) ) {
            return null;
        }

        if ( Constants.FILESYSTEM.equals( type ) ) {
            return new StoreImageFileImpl();
        }

        return null;
    }
}
