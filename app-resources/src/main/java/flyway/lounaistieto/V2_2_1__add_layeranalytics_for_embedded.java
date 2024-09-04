package flyway.lounaistieto;

import fi.nls.oskari.domain.map.view.Bundle;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;

import java.sql.Connection;
import java.util.List;

/**
 * Adds layer analytics bundle to embedded map views (and updates ones that currently have it)
 */
public class V2_2_1__add_layeranalytics_for_embedded extends BaseJavaMigration {
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        String bundleName = "layeranalytics";
        Bundle layerAnalyticsBundle = new Bundle(bundleName);
        List<Long> appsetupIds = AppSetupHelper.getSetupsForType(connection, null);
        AppSetupHelper.addOrUpdateBundleInApps(connection, layerAnalyticsBundle, appsetupIds);
    }
}
