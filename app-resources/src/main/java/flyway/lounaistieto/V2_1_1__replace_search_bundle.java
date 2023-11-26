package flyway.lounaistieto;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.oskari.helpers.AppSetupHelper;
import fi.nls.oskari.domain.map.view.Bundle;

import java.sql.Connection;
import java.util.List;

public class V2_1_1__replace_search_bundle extends BaseJavaMigration {
    private static final Logger LOG = LogFactory.getLogger(V2_1_1__replace_search_bundle.class);
    private static final String BUNDLE_TO_REMOVE_ID = "search";
    private static final String PLUGIN_SEARCH = "Oskari.mapframework.bundle.mapmodule.plugin.SearchPlugin";

    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();

        try {
            final List<Long> views = AppSetupHelper.getSetupsForUserAndDefaultType(connection);
            for (Long viewId : views) {
                if (AppSetupHelper.appContainsBundle(connection, viewId, BUNDLE_TO_REMOVE_ID)) {
                    AppSetupHelper.removeBundleFromApp(connection, viewId, BUNDLE_TO_REMOVE_ID);
                    // add searchplugin to appsetup
                    Bundle mapfull = AppSetupHelper.getAppBundle(connection, viewId, "mapfull");
                    if (addSearchPlugin(mapfull.getConfigJSON())) {
                        AppSetupHelper.updateAppBundle(connection, viewId, mapfull);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error removing bundle: ", e);
        }
    }

    private boolean addSearchPlugin(JSONObject config) throws JSONException {
        if (config == null) {
            return false;
        }
        JSONArray plugins = config.optJSONArray("plugins");
        if (plugins == null) {
            return false;
        }
        boolean found = false;
        for (int i = 0; i < plugins.length(); i++) {
            if (found) {
                break;
            }
            JSONObject plug = plugins.optJSONObject(i);
            if (plug == null) {
                continue;
            }
            found = PLUGIN_SEARCH.equals(plug.optString("id"));
        }
        if (found) {
            return false;
        }
        JSONObject plug = new JSONObject();
        plug.put("id", PLUGIN_SEARCH);
        plugins.put(plug);
        return true;
    }
}
