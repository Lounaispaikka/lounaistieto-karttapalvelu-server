package flyway.lounaistieto;


import fi.nls.oskari.domain.map.view.Bundle;
import helpers.AdditionalBundleHelperMethods;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.BundleHelper;

import java.sql.Connection;

/**
 * Checks if bundle is already present in the db and inserts it if not
 */
public class V1_0_3__register_start_info_popup extends BaseJavaMigration {

    private static final String NAME = "start-info-popup";

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        // BundleHelper checks if these bundles are already registered
        Bundle bundle = new Bundle();
        bundle.setName(NAME);
        bundle.setConfig(AdditionalBundleHelperMethods.getBundleStartup("/Oskari/packages/lounaistieto/bundle/", NAME, "start-info-popup"));
        BundleHelper.registerBundle(connection, bundle);

    }
}
