/*package flyway.lounaistieto;

import fi.nls.oskari.domain.Role;
import fi.nls.oskari.domain.User;
import fi.nls.oskari.domain.map.OskariLayer;
import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;

import org.oskari.permissions.PermissionService;
import org.oskari.permissions.PermissionServiceMybatisImpl;
import org.oskari.permissions.model.OskariLayerResource;
import org.oskari.permissions.model.Permission;
import org.oskari.permissions.model.Resource;
import fi.nls.oskari.service.ServiceException;
import fi.nls.oskari.service.UserService;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class V1_0_2__fix_roles_for_maanmittauslaitos_wmts implements JdbcMigration {
    private static final Logger LOG = LogFactory.getLogger(V1_0_2__fix_roles_for_maanmittauslaitos_wmts.class);

    public void migrate(Connection connection) throws SQLException {
        PermissionService service = new PermissionServiceMybatisImpl();
        for(Resource resToUpdate : getResources()) {
            Resource dbRes = service.findResource(resToUpdate);
            if(dbRes != null) {
                resToUpdate = dbRes;
            }
            for(Role role : getRoles()) {
                if(resToUpdate.hasPermission(role, Permissions.PERMISSION_TYPE_VIEW_LAYER)) {
                    // already had the permission
                    continue;
                }
                final Permission permission = new Permission();
                permission.setExternalType(Permissions.EXTERNAL_TYPE_ROLE);
                permission.setType(Permissions.PERMISSION_TYPE_VIEW_LAYER);
                permission.setExternalId(Long.toString(role.getId()));
                resToUpdate.addPermission(permission);
            }
            service.saveResourcePermissions(resToUpdate);
        }
    }

    // statslayers described as layer resources for permissions handling
    private List<Resource> getResources() {
        List<Resource> list = new ArrayList<>();
        list.add(new OskariLayerResource(OskariLayer.TYPE_WMTS, "http://avoindata.maanmittauslaitos.fi/mapcache/wmts", "taustakartta"));
        list.add(new OskariLayerResource(OskariLayer.TYPE_WMTS, "http://avoindata.maanmittauslaitos.fi/mapcache/wmts", "maastokartta"));
        return list;
    }

    private List<Role> getRoles() {
        List<Role> list = new ArrayList<>();
        try {
            // "logged in" user
            list.add(Role.getDefaultUserRole());
            // guest user
            User guest = UserService.getInstance().getGuestUser();
            list.addAll(guest.getRoles());
        } catch (ServiceException ex) {
            LOG.warn(ex, "Couldn't get roles listing for statistics layers permissions setup");
        }
        return list;
    }
}
*/