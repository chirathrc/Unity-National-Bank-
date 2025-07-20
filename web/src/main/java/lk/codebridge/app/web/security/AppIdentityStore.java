package lk.codebridge.app.web.security;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.UserService;
import lk.codebridge.app.core.util.EncryptPassword;

import java.util.Set;

@ApplicationScoped
@Named("appIdentityStore")
public class AppIdentityStore implements IdentityStore {

    @EJB
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

//            userService.validateUser(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
            User userCheck = userService.validateUser(usernamePasswordCredential.getCaller());

            if (userCheck != null) {
                if (userCheck.getRole() == Role.USER){
                    if (EncryptPassword.checkPassword(usernamePasswordCredential.getPasswordAsString(),userCheck.getPassword())) {
                        User user = userService.getUser(usernamePasswordCredential.getCaller());
                        System.out.println("User: " + user.getRole().name());
                        return new CredentialValidationResult(user.getUsername(), Set.of(user.getRole().name()));
                    }
                }else if (userCheck.getRole() == Role.ADMIN){
                    if (userCheck.getPassword().equals(usernamePasswordCredential.getPasswordAsString())) {
                        User user = userService.getUser(usernamePasswordCredential.getCaller());
                        System.out.println("User: " + user.getRole().name());
                        return new CredentialValidationResult(user.getUsername(), Set.of(user.getRole().name()));
                    }
                }

            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
