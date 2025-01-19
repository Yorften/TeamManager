package com.teammanager.service.interfaces;

import java.util.List;

import com.teammanager.dto.user.UpdateUserDTO;
import com.teammanager.dto.user.UserDTO;

/**
 * Service interface for User entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO getUserById(Long id, String... with);

    public UserDTO getByUserName(String userName);

    public UserDTO getByUserName(String userName, String... with);

    List<UserDTO> getAllUsers();

    List<UserDTO> getAllUsers(String... with);

    UserDTO addUser(UserDTO User);

    public UserDTO updateUser(Long UserId, UpdateUserDTO User, String... with);

    public void deleteUserById(Long UserId);

}
