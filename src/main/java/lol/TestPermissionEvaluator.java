package lol;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TestPermissionEvaluator implements PermissionEvaluator {
	private PermissionFactory permissionFactory = new ExtendedPermissionFactory();

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		Permission p = permissionFactory.buildFromName((String)permission);
		
		User user = (User)authentication.getPrincipal();
		UserAccessible target = (UserAccessible)targetDomainObject;
		
		return target.getPermissionsFor(user).contains(p);
	}

	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}

	private static class ExtendedPermissionFactory extends DefaultPermissionFactory {
		private ExtendedPermissionFactory() {
			super(ExtendedPermission.class);
		}
	}
}
