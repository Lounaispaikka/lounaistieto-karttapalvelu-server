package flyway.lounaistieto;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;

import java.sql.Connection;
import java.util.List;

public class V2_1_0__remove_old_layerlist_bundle extends BaseJavaMigration {
    private static final Logger LOG = LogFactory.getLogger(V2_1_0__remove_old_layerlist_bundle.class);
    private static final String BUNDLE_TO_REMOVE_ID = "hierarchical-layerlist";

    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        try {
            final List<Long> views = AppSetupHelper.getSetupsForUserAndDefaultType(connection);
            for (Long viewId : views) {
                AppSetupHelper.removeBundleFromApp(connection, viewId, BUNDLE_TO_REMOVE_ID);
            }
        } catch (Exception e) {
            LOG.error("Error removing bundle: ", e);
        }
    }
}
