package kr.co.nexters.together.common.resource;

import com.google.inject.Inject;
import kr.co.nexters.together.common.util.PermissionManager;
import org.hibernate.SessionFactory;

public abstract class AbstractResource {
    private SessionFactory sessionFactory;
    private PermissionManager permissionManager;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Inject
    private void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    @Inject
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
}
