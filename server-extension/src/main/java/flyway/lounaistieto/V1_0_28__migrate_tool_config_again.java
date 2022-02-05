package flyway.lounaistieto;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import fi.nls.oskari.util.JSONHelper;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Fixes mapUrlPrefix from lounaispaikka to lounaistieto from toolbar bundle
 */
public class V1_0_28__migrate_tool_config_again extends BaseJavaMigration {
    private static final Logger LOG = LogFactory.getLogger(V1_0_28__migrate_tool_config_again.class);

    public static Bundle updateBundleInView(Connection connection, Bundle bundle)
            throws SQLException {
        final String sql = "UPDATE portti_view_bundle_seq SET " +
                "config=? " +
                " WHERE bundle_id=? " +
                " AND view_id=?";

        try (final PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setString(1, bundle.config);
            statement.setLong(2, bundle.bundleId);
            statement.setLong(3, bundle.viewId);
            statement.execute();
        }
        return null;
    }

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();


        final ArrayList<Bundle> mapfullBundles = getToolbarBundles(connection);
        for (Bundle bundle : mapfullBundles) {
            if (!modifyConfig(bundle)) {
                continue;
            }
            // update view back to db
            updateBundleInView(connection, bundle);
        }

    }

    private boolean modifyConfig(Bundle bundle) throws Exception {
        JSONObject config = JSONHelper.createJSONObject(bundle.config);
        if (config == null) {
            LOG.warn("Couldn't get config JSON for view:" + bundle.viewId);
            return false;
        }
        config.remove("mapUrlPrefix");
        JSONObject urlPrefix = new JSONObject();
        urlPrefix.put("fi", "https://karttapalvelu.lounaistieto.fi?lang=fi&");
        urlPrefix.put("en", "https://karttapalvelu.lounaistieto.fi?lang=en&");
        urlPrefix.put("sv", "https://karttapalvelu.lounaistieto.fi?lang=sv&");
        config.put("mapUrlPrefix", urlPrefix);
        bundle.config = config.toString(2);
        return true;
    }

    private ArrayList<Bundle> getToolbarBundles(Connection connection) throws Exception {
        ArrayList<Bundle> ids = new ArrayList<>();
        final String sql = "SELECT view_id, bundle_id, config FROM portti_view_bundle_seq " +
                "WHERE bundle_id = (SELECT id FROM portti_bundle WHERE name = 'toolbar') AND config LIKE '%mapUrlPrefix%'";
        try (final PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Bundle b = new Bundle();
                b.viewId = rs.getLong("view_id");
                b.bundleId = rs.getLong("bundle_id");
                b.config = rs.getString("config");
                ids.add(b);
            }
        }
        return ids;
    }

}
