package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(req.getReader(), JsonObject.class);

            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();

            Response response = new Response();

            if (username == null || password == null) {
                response.setMessage("Username or password is incorrect");
                response.setSuccess(Boolean.FALSE);
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(response));
                return;
            }

            System.out.println(username + " " + password);


            AuthenticationParameters params = AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password));
            AuthenticationStatus status = securityContext.authenticate(req, resp, params);

            switch (status) {
                case SUCCESS:
                    // Authentication succeeded, handle role and redirect accordingly

                    Role role = userService.getRole(username);
                    String name = userService.getUsernameFromUsername(username);

                    if (role == Role.ADMIN) {

                        String redirectUrl = req.getContextPath() + "/admin/index.jsp";
                        req.getSession().setAttribute("role", role);
                        req.getSession().setAttribute("name", name);
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(redirectUrl);

                    } else if (role == Role.USER) {

                        String redirectUrl = req.getContextPath() + "/user/index.jsp";
                        req.getSession().setAttribute("role", role);
                        req.getSession().setAttribute("name", name);
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(redirectUrl);
                    }
                    break;

                case SEND_CONTINUE:
                    // Container is handling the response, do nothing here.
                    // Just return to avoid further processing.
                    return;

                case SEND_FAILURE:
                    // Authentication failed or incomplete
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.getWriter().write("Authentication failed");
                    break;
            }


            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(response));

        } catch (Exception e) {

            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }


    }
}
