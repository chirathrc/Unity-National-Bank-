package lk.codebridge.app.web.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@ApplicationScoped
@AutoApplySession
public class AuthMechanism implements HttpAuthenticationMechanism {

    @Inject
    @Named("appIdentityStore")
    private IdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {

        AuthenticationParameters authenticationParameters = httpMessageContext.getAuthParameters();

        if (authenticationParameters.getCredential() != null) {

            CredentialValidationResult result = identityStore.validate(authenticationParameters.getCredential());
            if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                return httpMessageContext.notifyContainerAboutLogin(result);
            } else {
                return AuthenticationStatus.SEND_FAILURE;
            }
        }

        if (httpMessageContext.isProtected() && httpMessageContext.getCallerPrincipal() == null) {
            try {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
            } catch (IOException e) {
                throw new RuntimeException("Redirect to login", e);
            }
        }
        return httpMessageContext.doNothing();
    }
}
