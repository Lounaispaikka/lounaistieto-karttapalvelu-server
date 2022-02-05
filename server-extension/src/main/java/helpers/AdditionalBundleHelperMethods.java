package helpers;

import fi.nls.oskari.util.OskariRuntimeException;

public class AdditionalBundleHelperMethods {

    public static String getBundleStartup(String path, String bundleid, String title) {
        if (bundleid == null) {
            throw new OskariRuntimeException("Missing bundleid");
        } else {
            return null;
        }
    }
}
