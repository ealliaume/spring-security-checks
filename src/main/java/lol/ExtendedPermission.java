package lol;

import org.springframework.security.acls.domain.BasePermission;

public class ExtendedPermission extends BasePermission {
	public static ExtendedPermission MODERATE = new ExtendedPermission(32, 'M');
	public static ExtendedPermission SUB_ADMIN = new ExtendedPermission(64, 'S');

	protected ExtendedPermission(int mask, char code) {
		super(mask, code);
	}
}
