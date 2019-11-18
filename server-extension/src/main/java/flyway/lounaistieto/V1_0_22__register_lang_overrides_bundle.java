package flyway.lounaistieto;

import java.sql.Connection;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import fi.nls.oskari.db.BundleHelper;
import fi.nls.oskari.domain.map.view.Bundle;

public class V1_0_22__register_lang_overrides_bundle implements JdbcMigration{
    private static final String NAMESPACE = "lounaistieto";
    private static final String LANG_OVERRIDES = "lounaistieto-lang-overrides";

    public void migrate(Connection connection) {
        // BundleHelper checks if these bundles are already registered
        Bundle linkPanel = new Bundle();
        linkPanel.setConfig("{}");
        linkPanel.setState("{}");
        linkPanel.setName(LANG_OVERRIDES);
        linkPanel.setStartup(BundleHelper.getDefaultBundleStartup(NAMESPACE, LANG_OVERRIDES, "Lang overrides"));
        BundleHelper.registerBundle(linkPanel);
    }
}

