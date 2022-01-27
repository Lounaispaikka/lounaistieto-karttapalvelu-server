package flyway.lounaistieto;

import java.sql.Connection;
import java.util.List;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;

/**
 * Adds userguide bundle to default and user views.
 */
public class V1_0_7__add_userguide_to_default_views extends BaseJavaMigration {
    private static final String BUNDLE_ID = "userguide";

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        final List<Long> views = AppSetupHelper.getSetupsForUserAndDefaultType(connection);
        for (Long viewId : views) {
            if (AppSetupHelper.appContainsBundle(connection, viewId, BUNDLE_ID)) {
                continue;
            }
            AppSetupHelper.addBundleToApp(connection, viewId, BUNDLE_ID);
        }
    }
}
