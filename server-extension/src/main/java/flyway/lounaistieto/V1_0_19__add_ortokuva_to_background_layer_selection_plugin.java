package flyway.lounaistieto;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import fi.nls.oskari.util.JSONHelper;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class V1_0_19__add_ortokuva_to_background_layer_selection_plugin extends BaseJavaMigration {
    private static final String PLUGIN_ID = "Oskari.mapframework.bundle.mapmodule.plugin.BackgroundLayerSelectionPlugin";
    private final Logger LOG = LogFactory.getLogger(V1_0_19__add_ortokuva_to_background_layer_selection_plugin.class);

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();
        Integer ortokuvaId = getOrtokuvaLayer(connection);
        if (ortokuvaId == null) {
            LOG.warn("No ortokuva layer found, skipping rest of migration!");
            return;
        }

        updateViewsWithBaselayerSelector(connection, ortokuvaId);
    }

    private Integer getOrtokuvaLayer(Connection conn) throws SQLException {
        // find first Selkokartta layer
        final String sql = "SELECT id FROM oskari_maplayer WHERE name = 'ortokuva' AND url LIKE 'https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts%' LIMIT 1";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }

    private void updateViewsWithBaselayerSelector(Connection conn, int selkokarttaId) throws SQLException {
        final String sql = "SELECT view_id, seqno, config FROM portti_view_bundle_seq WHERE bundle_id = (select id from portti_bundle WHERE name = 'mapfull')";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    JSONObject config = JSONHelper.createJSONObject(rs.getString("config"));
                    updateOneViewIfNeeded(conn, rs.getInt("view_id"), rs.getInt("seqno"), config, selkokarttaId);
                }
            }
        }
    }

    private void updateOneViewIfNeeded(Connection conn, int viewId, int seqNo, JSONObject config, int selkokarttaId) throws SQLException {
        JSONArray plugins = JSONHelper.getJSONArray(config, "plugins");
        for (int i = 0; i < plugins.length(); i++) {
            try {
                JSONObject plugin = (JSONObject) plugins.get(i);
                if (PLUGIN_ID.equals(JSONHelper.getStringFromJSON(plugin, "id", null))) {
                    JSONObject pluginConfig = JSONHelper.getJSONObject(plugin, "config");
                    if (pluginConfig == null) {
                        continue;
                    }
                    JSONArray baseLayers = JSONHelper.getJSONArray(pluginConfig, "baseLayers");
                    if (baseLayers == null) {
                        continue;
                    }

                    boolean hasOrtokuvaLayer = false;
                    for (int j = 0; j < baseLayers.length(); j++) {
                        int current;
                        try {
                            current = baseLayers.getInt(j);
                        } catch (Exception e) {
                            continue;
                        }
                        if (selkokarttaId == current) {
                            hasOrtokuvaLayer = true;
                        }
                    }
                    if (hasOrtokuvaLayer) {
                        continue;
                    }
                    baseLayers.put(selkokarttaId);

                    final String sql = "UPDATE portti_view_bundle_seq SET config=? WHERE view_id=? AND seqno=?";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, config.toString(2));
                        statement.setInt(2, viewId);
                        statement.setInt(3, seqNo);
                        statement.execute();
                    }
                }
            } catch (JSONException e) {
                LOG.error("Error in migration:", e.getMessage());
            }
        }
    }
}
