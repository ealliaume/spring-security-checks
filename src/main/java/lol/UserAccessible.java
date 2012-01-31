package lol;

import org.springframework.security.acls.model.Permission;

import java.util.Set;

public interface UserAccessible {
	Set<Permission> getPermissionsFor(User user);
}
