package lk.codebridge.app.ejb.beans;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.codebridge.app.core.dto.AccountDTO;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.Account;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.model.User;
import lk.codebridge.app.core.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserSessionBean implements UserService {

    @PersistenceContext
    private EntityManager em;


    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public User getUser(String username, String password) {
        return null;
    }

    @PermitAll
    @Override
    public User getUser(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public UserDTO getUserFromNic(String nic) {
        try {
            User user = em.createNamedQuery("User.findByNIC", User.class)
                    .setParameter("nic", nic)
                    .getSingleResult();
            return getUserDTO(user);
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public User getDirectUserFromNic(String nic) {
        try {
            return em.createNamedQuery("User.findByNIC", User.class)
                    .setParameter("nic", nic)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = em.createNamedQuery("User.findAll", User.class)
                .setParameter("role", Role.USER)
                .getResultList();

        for (User user : users) {
            userDTOS.add(new UserDTO(user));
        }

        return userDTOS;
    }

    @PermitAll
    @Override
    public User validateUser(String username, String password) {
        try {
            User user = em.createNamedQuery("User.findByUsername&Password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            if (user.getStatus() == Status.INACTIVE) {
                return null;
            }

            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName());
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @PermitAll
    @Override
    public User validateUser(String username) {
        try {
            User user = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            if (user.getStatus() == Status.INACTIVE) {
                return null;
            }

            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public Role getRole(String username) {
        User user = getUser(username);
        return user != null ? user.getRole() : null;
    }

    @RolesAllowed({"USER", "ADMIN"})
    @Override
    public String getUsernameFromUsername(String username) {
        User user = getUser(username);
        return user != null ? user.getFirstName() + " " + user.getLastName() : null;
    }

    @RolesAllowed({"ADMIN","USER"})
    @Override
    public UserDTO getUserDtoFromUsername(String username) {
        try {
            User user = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return getUserDTO(user);
        } catch (NoResultException e) {
            return null;
        }
    }

    // --- Write Methods ---

    @RolesAllowed("ADMIN")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void createUser(User user) {
        try {
            em.persist(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @RolesAllowed("ADMIN")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void updateUser(String nic, Status status) {
        try {
            User user = em.createNamedQuery("User.findByNIC", User.class)
                    .setParameter("nic", nic)
                    .getSingleResult();
            user.setStatus(status);
            em.merge(user);
        } catch (NoResultException e) {
            // silently fail or log
        }
    }

    @RolesAllowed("ADMIN")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void updateUser(UserDTO userDTO) {
        User user = getDirectUserFromNic(userDTO.getNic());
        if (user != null) {
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setFullName(userDTO.getFullName());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setGender(userDTO.getGender());
            em.merge(user);
        }
    }

    @RolesAllowed("ADMIN")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void updateUserContacts(UserDTO userDTO) {
        User user = getDirectUserFromNic(userDTO.getNic());
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setAddressLineOne(userDTO.getAddressLineOne());
            user.setAddressLineTwo(userDTO.getAddressLineTwo());
            user.setCity(userDTO.getCity());
            user.setMobileNumber(String.valueOf(userDTO.getMobileNumber()));
            user.setPostalCode(userDTO.getPostalCode());
            em.merge(user);
        }
    }

    @RolesAllowed("USER")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public boolean UpdatePassword(User user) {
        try {
            User u = em.merge(user);
            return u != null;
        } catch (Exception e) {
            return false;
        }
    }

    // --- Helper ---

    private UserDTO getUserDTO(User user) {
        List<AccountDTO> accounts = new ArrayList<>();
        for (Account account : user.getAccounts()) {
            accounts.add(new AccountDTO(account));
        }

        UserDTO userDTO = new UserDTO(user);
        userDTO.setAccounts(accounts);

        return userDTO;
    }
}
