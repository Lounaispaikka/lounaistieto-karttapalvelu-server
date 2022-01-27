package flyway.lounaistieto;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;
import java.sql.Connection;
import java.util.List;


/**
 * Adds printout bundle to default and user views.
 */
public class V1_0_6__add_printout_to_default_views extends BaseJavaMigration {
    private static final String BUNDLE_ID = "printout";

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
