package flyway.lounaistieto;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;
import org.oskari.helpers.BundleHelper;

import java.sql.Connection;
import java.util.List;

public class V1_30__replace_layerselector2_with_layerlist_in_views extends BaseJavaMigration {
    private static final String BUNDLE_TO_ADD_ID = "layerlist";
    private static final String BUNDLE_TO_REMOVE_ID = "layerselector2";

    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        final List<Long> views = AppSetupHelper.getSetupsForUserAndDefaultType(connection);
        for(Long viewId : views){
            if (AppSetupHelper.appContainsBundle(connection, viewId, BUNDLE_TO_REMOVE_ID)) {
                AppSetupHelper.removeBundleFromApp(connection, viewId, BUNDLE_TO_REMOVE_ID);
            }

            if (AppSetupHelper.appContainsBundle(connection, viewId, BUNDLE_TO_ADD_ID)) {
                continue;
            }
            AppSetupHelper.addBundleToApp(connection, viewId, BUNDLE_TO_ADD_ID);
        }
        if (BundleHelper.isBundleRegistered(BUNDLE_TO_REMOVE_ID)) {
            BundleHelper.unregisterBundle(connection, BUNDLE_TO_REMOVE_ID);
        }
    }
}
