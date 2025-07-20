package lk.codebridge.app.core.service;

import jakarta.ejb.Remote;
import lk.codebridge.app.core.dto.UserDTO;
import lk.codebridge.app.core.model.Role;
import lk.codebridge.app.core.model.Status;
import lk.codebridge.app.core.model.User;

import java.util.List;

@Remote
public interface UserService {

    public User getUser(int id);

    public User getUser(String username, String password);

    public User getUser(String username);

    public UserDTO getUserFromNic(String nic);

    public User getDirectUserFromNic(String nic);

    public List<UserDTO> getAllUsers();

    public User validateUser(String username, String password);
    public User validateUser(String username);

    public Role getRole(String username);

    public void createUser(User user);

    public void updateUser(String nic, Status status);

    public void updateUser(UserDTO userDTO);
    public void updateUserContacts(UserDTO userDTO);

    public String getUsernameFromUsername(String username);


    public UserDTO getUserDtoFromUsername(String username);

    public boolean UpdatePassword(User user);

}
