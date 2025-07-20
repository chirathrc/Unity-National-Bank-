package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.mail.RegistrationEmail;
import lk.codebridge.app.core.model.Gender;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.provider.MaliServiceProvider;
import lk.codebridge.app.core.service.UserService;
import lk.codebridge.app.core.util.EncryptPassword;
import lk.codebridge.app.core.util.UsernamePasswordGenerator;

import java.io.IOException;
import java.time.LocalDate;


@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
@WebServlet("/admin/addCustomer")
public class AddCustomer extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Response response = new Response();
        response.setSuccess(true);

        String fullName = req.getParameter("fullName");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String nic = req.getParameter("nic");
        String addressLine1 = req.getParameter("addressLine1");
        String addressLine2 = req.getParameter("addressLine2");
        String city = req.getParameter("city");
        String postalCode = req.getParameter("postalCode");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");

        // Files
//        Part nicFrontPart = req.getPart("nicFront");
//        Part nicBackPart = req.getPart("nicBack");

        User checkUser = userService.getDirectUserFromNic(nic);
        if (checkUser != null) {
            response.setSuccess(false);
            response.setMessage("Already exist NIC");
            responseMake(resp, response);
            return;
        }

        if (isEmpty(fullName) || isEmpty(firstName) || isEmpty(lastName) ||
                isEmpty(dob) || isEmpty(gender) || isEmpty(nic) ||
                isEmpty(addressLine1) || isEmpty(city) || isEmpty(postalCode) ||
                isEmpty(mobile) || isEmpty(email)) {

            response.setSuccess(false);
            response.setMessage("Please fill all the required fields");
            responseMake(resp, response);
            return;

        }

        // Optional: Pattern validation
        if (!mobile.matches("\\d{9}")) {

            response.setSuccess(false);
            response.setMessage("Invalid mobile number");
            responseMake(resp, response);
            return;
        }

        if (!nic.matches("\\d{9}[VvXx]|\\d{12}")) {

            response.setSuccess(false);
            response.setMessage("Invalid NIC number");
            responseMake(resp, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            response.setSuccess(false);
            response.setMessage("Invalid email address");
            responseMake(resp, response);
            return;
        }


//        System.out.println("fullName: " + fullName);
//        System.out.println("firstName: " + firstName);
//        System.out.println("lastName: " + lastName);
//        System.out.println("dob: " + dob);
//        System.out.println("gender: " + gender);
//        System.out.println("nic: " + nic);
//        System.out.println("addressLine1: " + addressLine1);
//        System.out.println("addressLine2: " + addressLine2);
//        System.out.println("city: " + city);
//        System.out.println("postalCode: " + postalCode);
//        System.out.println("mobile: " + mobile);
//        System.out.println("email: " + email);

        String password_generated = UsernamePasswordGenerator.generatePassword();
        String username_generated = UsernamePasswordGenerator.generateUsername(firstName, lastName, nic);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddressLineOne(addressLine1);
        user.setAddressLineTwo(addressLine2);
        user.setCity(city);
        user.setPostalCode(postalCode);
        user.setFullName(fullName);
        user.setDateOfBirth(LocalDate.parse(dob));
        user.setEmail(email);
        user.setGender(Gender.valueOf(gender));
        user.setNIC(nic);
        user.setMobileNumber(mobile);
        user.setUsername(username_generated);
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        user.setPassword(EncryptPassword.hashPassword(password_generated));
        user.setRegDate(LocalDate.now());


        try {
            userService.createUser(user);

            RegistrationEmail sendEmail = new RegistrationEmail(user.getEmail(), user.getFullName(), username_generated, password_generated);
            MaliServiceProvider.getInstance().sendMail(sendEmail);

        } catch (RuntimeException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("User creation failed, try again later");
        }


        responseMake(resp, response);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void responseMake(HttpServletResponse resp, Response response) throws IOException {

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(response));
    }

}
