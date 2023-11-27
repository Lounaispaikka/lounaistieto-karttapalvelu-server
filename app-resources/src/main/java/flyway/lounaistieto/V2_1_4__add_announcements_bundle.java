package flyway.lounaistieto;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.AppSetupHelper;

public class V2_1_4__add_announcements_bundle extends BaseJavaMigration {

    public void migrate(Context context) throws Exception {
        AppSetupHelper.addBundleToApps(context.getConnection(), "announcements");
    }
}
