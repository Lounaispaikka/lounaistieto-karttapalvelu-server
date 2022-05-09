package flyway.lounaistieto;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;
import org.oskari.helpers.BundleHelper;

import java.sql.Connection;
import java.util.List;

public class V1_32__remove_layerselection2_from_views extends BaseJavaMigration {
    private static final Logger LOG = LogFactory.getLogger(V1_32__remove_layerselection2_from_views.class);
    private static final String BUNDLE_TO_REMOVE_ID = "layerselection2";

    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        try {
            final List<Long> views = AppSetupHelper.getSetupsForUserAndDefaultType(connection);
            for (Long viewId : views) {
                if (AppSetupHelper.appContainsBundle(connection, viewId, BUNDLE_TO_REMOVE_ID)) {
                    AppSetupHelper.removeBundleFromApp(connection, viewId, BUNDLE_TO_REMOVE_ID);
                }
            }
            if (BundleHelper.isBundleRegistered(BUNDLE_TO_REMOVE_ID)) {
                BundleHelper.unregisterBundle(connection, BUNDLE_TO_REMOVE_ID);
            }
        } catch (Exception e) {
            LOG.error("Error removing bundle: ", e);
        }
    }
}
