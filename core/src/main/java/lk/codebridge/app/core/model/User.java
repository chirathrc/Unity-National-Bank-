package lk.codebridge.app.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername&Password", query = "select u from User u where u.username =:username and u.password=:password"),
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username =:username"),
        @NamedQuery(name = "User.findByNIC", query = "select u from User u where u.NIC =:nic"),
        @NamedQuery(name = "User.findAll", query = "select u from  User u where u.role=:role"),
})
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 45, nullable = false)
    private String password;

    @Column(length = 45)
    private String firstName;

    @Column(length = 45)
    private String lastName;

    @Column(length = 45)
    private String fullName;

    @Column(name = "regDate")
    private LocalDate regDate;

    @Column(length = 45, unique = true, nullable = false)
    private String email;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "mobile", length = 10)
    private String mobileNumber;

    @Column(columnDefinition = "TEXT")
    private String addressLineOne;

    @Column(columnDefinition = "TEXT")
    private String addressLineTwo;

    @Column(length = 45)
    private String City;

    @Column(length = 45)
    private String postalCode;

    @Column(name = "nic", length = 20)
    private String NIC;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(length = 45)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;
}
