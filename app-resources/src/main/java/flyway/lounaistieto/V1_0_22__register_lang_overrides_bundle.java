package flyway.lounaistieto;

import fi.nls.oskari.domain.map.view.Bundle;
import helpers.AdditionalBundleHelperMethods;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.oskari.helpers.BundleHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class V1_0_22__register_lang_overrides_bundle extends BaseJavaMigration {
    private static final String NAMESPACE = "lounaistieto";
    private static final String LANG_OVERRIDES = "lounaistieto-lang-overrides";

    @Override
    public void migrate(Context context) throws SQLException {
        Connection connection = context.getConnection();

        // BundleHelper checks if these bundles are already registered
        Bundle bundle = new Bundle();
        bundle.setName(LANG_OVERRIDES);
        bundle.setConfig("{}");
        bundle.setState("{}");
        bundle.setConfig(AdditionalBundleHelperMethods.getBundleStartup("/Oskari/packages/lounaistieto/bundle/", LANG_OVERRIDES, "start-info-popup"));
        BundleHelper.registerBundle(connection, bundle);
    }
}

