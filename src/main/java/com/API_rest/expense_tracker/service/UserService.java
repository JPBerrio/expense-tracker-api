package com.API_rest.expense_tracker.service;

import com.API_rest.expense_tracker.persistence.entities.UserEntity;
import com.API_rest.expense_tracker.persistence.entities.UserRoleEntity;
import com.API_rest.expense_tracker.persistence.repositories.UserRepository;
import com.API_rest.expense_tracker.persistence.repositories.UserRoleRepository;
import com.API_rest.expense_tracker.web.dto.UserDTO;
import com.API_rest.expense_tracker.web.dto.UserSignUpDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public UserEntity findUserById(Long idUser) {
        return this.userRepository.findById(idUser).orElse(null);
    }

    public UserEntity findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Page<UserDTO> getAllUsers(int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        Page<UserEntity> userEntities = userRepository.findAll(pageable);

        return userEntities.map(userEntity -> modelMapper.map(userEntity, UserDTO.class));
    }

    @Transactional
    public UserEntity registerUser(UserSignUpDTO userSignUpDTO) {
        if (userRepository.findByUsername(userSignUpDTO.getUsername()) != null) {
            throw new RuntimeException("User Already Exists!");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(userSignUpDTO.getUsername());
        newUser.setLastName(userSignUpDTO.getLastName());
        newUser.setAge(userSignUpDTO.getAge());
        newUser.setEmail(userSignUpDTO.getEmail());
        newUser.setPhone(userSignUpDTO.getPhone());
        newUser.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
        newUser.setLocked(false);
        newUser.setDisabled(false);

        UserEntity savedUser = userRepository.save(newUser);

        UserRoleEntity userRoleEntity = new UserRoleEntity(savedUser.getUsername(), "USER", LocalDateTime.now());
        userRoleRepository.save(userRoleEntity);

        return savedUser;
    }
}
