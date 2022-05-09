package flyway.lounaistieto;

import fi.nls.oskari.domain.map.OskariLayer;
import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import fi.nls.oskari.util.JSONHelper;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class V1_0_20__fix_and_repopulate_preparsed_layer_capabilities_for_layers extends BaseJavaMigration {
    private static final Logger LOG = LogFactory.getLogger(V1_0_20__fix_and_repopulate_preparsed_layer_capabilities_for_layers.class);

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();
    }

    List<OskariLayer> getLayers(Connection conn)
            throws SQLException {
        List<OskariLayer> layers = new ArrayList<>();
        // process all layers
        final String sql = "SELECT id, url, type, name FROM oskari_maplayer WHERE capabilities_last_updated < NOW() - INTERVAL '1 days' OR capabilities_last_updated IS NULL";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    OskariLayer layer = new OskariLayer();
                    layer.setId(rs.getInt("id"));
                    layer.setUrl(rs.getString("url"));
                    layer.setName(rs.getString("name"));
                    layer.setType(rs.getString("type"));
                    layers.add(layer);
                }
            }
        }
        return layers;
    }
}
