package lk.codebridge.app.core.dto;

import jakarta.persistence.Column;
import lk.codebridge.app.core.model.Gender;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.mobileNumber = user.getMobileNumber();
        this.addressLineOne = user.getAddressLineOne();
        this.addressLineTwo = user.getAddressLineTwo();
        this.City = user.getCity();
        this.postalCode = user.getPostalCode();
        this.nic = user.getNIC();
        this.gender = user.getGender();
        this.status = user.getStatus();
        this.role = user.getRole();
        this.regTime = user.getRegDate();
    }

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    private String addressLineOne;
    private String addressLineTwo;
    private String City;
    private String postalCode;
    private String nic;
    private Gender gender;
    private Status status;
    private Role role;
    private LocalDate regTime;

    private List<AccountDTO> accounts;

}
