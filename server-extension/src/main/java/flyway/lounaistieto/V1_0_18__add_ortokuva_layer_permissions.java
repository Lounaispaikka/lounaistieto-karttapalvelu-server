package flyway.lounaistieto;

import fi.nls.oskari.domain.Role;
import fi.nls.oskari.domain.User;
import fi.nls.oskari.domain.map.OskariLayer;
import fi.nls.oskari.map.layer.OskariLayerService;
import fi.nls.oskari.service.OskariComponentManager;
import fi.nls.oskari.service.ServiceException;
import fi.nls.oskari.service.ServiceRuntimeException;
import fi.nls.oskari.service.UserService;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.oskari.permissions.PermissionService;
import org.oskari.permissions.PermissionServiceMybatisImpl;
import org.oskari.permissions.model.*;

import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.log.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Inserts viewlayer permission for the ortokuva layers used as regionsets for the new thematic maps
 */
public class V1_0_18__add_ortokuva_layer_permissions implements JdbcMigration {
    private static final Logger LOG = LogFactory.getLogger(V1_0_18__add_ortokuva_layer_permissions.class);

    public void migrate(Connection connection)
            throws SQLException {
        PermissionService service = new PermissionServiceMybatisImpl();
        for(Resource resToUpdate : getResources()) {
            Optional<Resource> dbRes = service.findResource(ResourceType.maplayer, resToUpdate.getMapping());
            if(dbRes.isPresent()) {
                resToUpdate = dbRes.get();
            }
            for(Role role : getRoles()) {
                if(resToUpdate.hasPermission(role, PermissionType.VIEW_LAYER)) {
                    // already had the permission
                    continue;
                }
                final Permission permission = new Permission();
                permission.setType(PermissionType.VIEW_LAYER);
                permission.setRoleId((int) role.getId());
                resToUpdate.addPermission(permission);
            }
            service.saveResource(resToUpdate);
        }
    }

    // statslayers described as layer resources for permissions handling
    protected List<Resource> getResources() {
        List<Resource> list = new ArrayList<>();
        OskariLayerService service = OskariComponentManager.getComponentOfType(OskariLayerService.class);
        List<OskariLayer> layers = service.findByUrlAndName(
                "https://avoin-karttakuva.maanmittauslaitos.fi/avoin/wmts", "ortokuva");
        for (OskariLayer layer : layers) {
            Resource res = new Resource();
            res.setType(ResourceType.maplayer);
            res.setMapping(Integer.toString(layer.getId()));
            list.add(res);
        }

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
            throw new ServiceRuntimeException("Unable to get roles");
        }
        return list;
    }
}
