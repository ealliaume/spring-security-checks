package lol;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MultiSecurityService {
	@PreAuthorize("hasPermission(#o, 'READ')")
	public void beAReader(UserAccessible o) {
	}

	@PreAuthorize("hasPermission(#o, 'MODERATE')")
	public void beAModerator(UserAccessible o) {
	}

	@PreAuthorize("hasPermission(#o, 'SUB_ADMIN')")
	public void beASubadmin(UserAccessible o) {
	}
}
