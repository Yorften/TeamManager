package com.teammanager.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<UserDTO> getAllUsers(Pageable pageable);

    Page<UserDTO> getAllUsers(Pageable pageable, String... with);

    UserDTO addUser(UserDTO User);

    public UserDTO updateUser(Long UserId, UpdateUserDTO User, String... with);

    public void deleteUserById(Long UserId);

}
