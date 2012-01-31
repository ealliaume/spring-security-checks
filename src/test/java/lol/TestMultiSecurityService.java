package lol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/java/applicationContext.xml")
public class TestMultiSecurityService {

	@Autowired
	public MultiSecurityService service;
	
	private UserAccessible mockUserAccessible;
	private User mockUser;

	@Before
	public void setup() {
		mockUserAccessible = mock(UserAccessible.class);
		mockUser = mock(User.class);
		
		Authentication mockAuthentication = mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(mockAuthentication);
		
		when(mockAuthentication.isAuthenticated()).thenReturn(true);
		when(mockAuthentication.getPrincipal()).thenReturn(mockUser);
	}
	
	@Test
	public void shouldHaveReadPermissionToRead() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(BasePermission.READ));

		service.beAReader(mockUserAccessible);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldFailReadWhenNoPermissions() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions());

		service.beAReader(mockUserAccessible);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldFailModerateWhenNoModeratePermissions() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(BasePermission.READ));

		service.beAModerator(mockUserAccessible);
	}

	@Test
	public void shouldHaveModeratePermissionToModerate() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(ExtendedPermission.MODERATE));

		service.beAModerator(mockUserAccessible);
	}

	@Test
	public void shouldHaveSubadminPermissionToSubadmin() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(ExtendedPermission.SUB_ADMIN));

		service.beASubadmin(mockUserAccessible);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldFailModerateWhenOnlySubadminPermission() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(ExtendedPermission.SUB_ADMIN));

		service.beAModerator(mockUserAccessible);
	}

	@Test(expected = AccessDeniedException.class)
	public void shouldFailSubadminWhenOnlyModeratePermission() {
		when(mockUserAccessible.getPermissionsFor(mockUser)).thenReturn(permissions(ExtendedPermission.MODERATE));

		service.beASubadmin(mockUserAccessible);
	}
	
	private static Set<Permission> permissions(Permission... permissions) {
		Set<Permission> values = new HashSet<Permission>();

		Collections.addAll(values, permissions);

		return values;
	}
}
