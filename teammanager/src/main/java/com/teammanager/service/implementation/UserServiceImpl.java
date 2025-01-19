package com.teammanager.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teammanager.dto.user.UpdateUserDTO;
import com.teammanager.dto.user.UserDTO;
import com.teammanager.exception.ResourceNotFoundException;
import com.teammanager.mapper.UserMapper;
import com.teammanager.model.Role;
import com.teammanager.model.User;
import com.teammanager.repository.RoleRepository;
import com.teammanager.repository.UserRepository;
import com.teammanager.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for User entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.convertToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id, String... with) {
        userMapper.verifyIncludes(with);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.convertToDTO(user, with);
    }

    @Override
    public UserDTO getByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.convertToDTO(user);
    }

    @Override
    public UserDTO getByUserName(String userName, String... with) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return userMapper.convertToDTO(user, with);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable, String... with) {
        userMapper.verifyIncludes(with);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> userMapper.convertToDTO(user, with));
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> userMapper.convertToDTO(user));
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.convertToEntity(userDTO);
        return userMapper.convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserDTO userDTO, String... with) {
        userMapper.verifyIncludes(with);
        User userDB = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (userDTO.getRole() != null) {
            Role role = roleRepository.findById(userDTO.getRole().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            userDB.setRole(role);
        }

        return userMapper.convertToDTO(userRepository.save(userDB), with);
    }

    @Override
    public void deleteUserById(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
