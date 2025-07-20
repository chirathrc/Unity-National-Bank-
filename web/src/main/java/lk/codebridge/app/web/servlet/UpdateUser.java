package lk.codebridge.app.web.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.Request;
import lk.codebridge.app.core.dto.Response;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.Gender;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.service.UserService;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/updateUser")
public class UpdateUser extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nic = req.getParameter("nic");
        String status = req.getParameter("status");

        System.out.println("update " + nic + " " + status);

        userService.updateUser(nic, Status.valueOf(status));

        Response response = new Response();
        response.setSuccess(Boolean.TRUE);

        resp.getWriter().write(new Gson().toJson(response));

    }

    //for personal details
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(req.getReader(), JsonObject.class);


            if (jsonObject.get("detailsCategory").getAsInt() == 2) {
                System.out.println("Contact details");
                updateUserContacts(resp, req, gson, jsonObject);
                return;
            }

            String nic = jsonObject.get("nic") != null ? jsonObject.get("nic").getAsString().trim() : "";
            String firstName = jsonObject.get("firstName") != null ? jsonObject.get("firstName").getAsString().trim() : "";
            String lastName = jsonObject.get("lastName") != null ? jsonObject.get("lastName").getAsString().trim() : "";
            String dateOfBirth = jsonObject.get("dateOfBirth") != null ? jsonObject.get("dateOfBirth").getAsString().trim() : "";
            String fullName = jsonObject.get("fullName") != null ? jsonObject.get("fullName").getAsString().trim() : "";
            String gender = jsonObject.get("gender") != null ? jsonObject.get("gender").getAsString().trim() : "";

            if (nic.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || dateOfBirth.isEmpty() || fullName.isEmpty() || gender.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
                return;
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setNic(nic);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setDateOfBirth(LocalDate.parse(dateOfBirth));
            userDTO.setFullName(fullName);
            userDTO.setGender(Gender.valueOf(gender.toUpperCase()));

//            UserDTO userDTO = new UserDTO();
//            userDTO.setNic(jsonObject.get("nic").getAsString());
//            userDTO.setFirstName(jsonObject.get("firstName").getAsString());
//            userDTO.setLastName(jsonObject.get("lastName").getAsString());
//            userDTO.setDateOfBirth(LocalDate.parse(jsonObject.get("dateOfBirth").getAsString()));
//            userDTO.setFullName(jsonObject.get("fullName").getAsString());
//            userDTO.setGender(Gender.valueOf(jsonObject.get("gender").getAsString().toUpperCase()));

            System.out.println(userDTO);

            userService.updateUser(userDTO);


            Response response = new Response();
            response.setSuccess(Boolean.TRUE);

            resp.getWriter().write(gson.toJson(response));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateUserContacts(HttpServletResponse resp, HttpServletRequest req, Gson gson, JsonObject jsonObject) throws ServletException, IOException {


        System.out.println("update " + jsonObject.get("nic").getAsString());

        String nic = jsonObject.get("nic") != null ? jsonObject.get("nic").getAsString().trim() : "";
        String mobileNumber = jsonObject.get("mobileNumber") != null ? jsonObject.get("mobileNumber").getAsString().trim() : "";
        String email = jsonObject.get("email") != null ? jsonObject.get("email").getAsString().trim() : "";
        String addressLine1 = jsonObject.get("addressLine1") != null ? jsonObject.get("addressLine1").getAsString().trim() : "";
        String addressLine2 = jsonObject.get("addressLine2") != null ? jsonObject.get("addressLine2").getAsString().trim() : "";
        String postalCode = jsonObject.get("postalCode") != null ? jsonObject.get("postalCode").getAsString().trim() : "";
        String city = jsonObject.get("city") != null ? jsonObject.get("city").getAsString().trim() : "";

        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String mobileRegex = "^[1-9][0-9]{8}$";  // 9 digits, not starting with 0

        if (nic.isEmpty() || mobileNumber.isEmpty() || email.isEmpty() || addressLine1.isEmpty()
                || addressLine2.isEmpty() || postalCode.isEmpty() || city.isEmpty()
                || !email.matches(emailRegex) || !mobileNumber.matches(mobileRegex)) {

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid or missing fields. Ensure all fields are filled. Email and mobile number must be in correct format.");
            return;
        }

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setNic(nic);
        userDTO1.setMobileNumber(mobileNumber);
        userDTO1.setEmail(email);
        userDTO1.setAddressLineOne(addressLine1);
        userDTO1.setAddressLineTwo(addressLine2);
        userDTO1.setPostalCode(postalCode);
        userDTO1.setCity(city);


//        UserDTO userDTO1 = new UserDTO();
//        userDTO1.setNic(jsonObject.get("nic").getAsString());
//        userDTO1.setMobileNumber(jsonObject.get("mobileNumber").getAsString());
//        userDTO1.setEmail(jsonObject.get("email").getAsString());
//        userDTO1.setAddressLineOne(jsonObject.get("addressLine1").getAsString());
//        userDTO1.setAddressLineTwo(jsonObject.get("addressLine2").getAsString());
//        userDTO1.setPostalCode(jsonObject.get("postalCode").getAsString());
//        userDTO1.setCity(jsonObject.get("city").getAsString());

        userService.updateUserContacts(userDTO1);


        Response response = new Response();
        response.setSuccess(Boolean.TRUE);

        resp.getWriter().write(gson.toJson(response));

    }
}
