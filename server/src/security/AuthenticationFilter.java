package security;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import config.SecurityConstants;
import domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.Priorities;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
    private ResourceInfo resourceInfo;
	
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
		
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (!isTokenBasedAuthentication(authorizationHeader)) {
			abortWithUnauthorized(requestContext);
			return;
		}

		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

		try {
			validateToken(token);
			checkPermissions(methodRoles, token);
		} catch (Exception e) {
			abortWithUnauthorized(requestContext);
		}

	}

	private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
        	return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
            	return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                if(allowedRoles.length == 0) {
                	return Arrays.asList(Role.values());
                }
                return Arrays.asList(allowedRoles);
            }
        }
    }
	
	private boolean isTokenBasedAuthentication(String authorizationHeader) {

		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
				.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());
	}

	private void validateToken(String token) throws Exception {
		Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
	}
	
	private void checkPermissions(List<Role> allowedRoles, String jwt) throws Exception {
		Role role = getRoleFromJWT(jwt);
		if(!allowedRoles.contains(role)) throw new Exception("Unatuhorized");
    }
	
	public Role getRoleFromJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
            return Role.valueOf(claims.get("role", String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
