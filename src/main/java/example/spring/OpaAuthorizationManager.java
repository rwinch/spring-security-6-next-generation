package example.spring;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class OpaAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
	private final OpaService opaService;

	public OpaAuthorizationManager(OpaService opaService) {
		this.opaService = opaService;
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
		String principal = authentication.get().getName();
		String[] authorities = authentication.get().getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new);
		String requestURI = context.getRequest().getRequestURI();
		OpaService.Request request = new OpaService.Request(requestURI);
		OpaService.User user = new OpaService.User(principal, authorities);
		OpaService.Input input = new OpaService.Input(request, user);
		boolean allow = this.opaService.checkAccess(input).result().spring().allow();
		return new AuthorizationDecision(allow);
	}
}
