package flyway.lounaistieto;

import fi.nls.oskari.util.FlywayHelper;
import fi.nls.oskari.util.PropertyUtil;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.util.List;

/**
 * Adds start-info-popup bundle to default and user views.
 */
public class V1_0_4__add_start_info_popup_to_default_views implements JdbcMigration {
    private static final String BUNDLE_ID = "start-info-popup";

    public void migrate(Connection connection) throws Exception {

        final List<Long> views = FlywayHelper.getUserAndDefaultViewIds(connection);
        for(Long viewId : views){
            if (FlywayHelper.viewContainsBundle(connection, BUNDLE_ID, viewId)) {
                continue;
            }
            FlywayHelper.addBundleWithDefaults(connection, viewId, BUNDLE_ID);
        }
    }
}
