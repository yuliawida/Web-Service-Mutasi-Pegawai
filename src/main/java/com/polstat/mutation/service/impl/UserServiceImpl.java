package com.polstat.mutation.service.impl;

import com.polstat.mutation.dto.UserDTO;
import com.polstat.mutation.entity.User;
import com.polstat.mutation.mapper.UserMapper;
import com.polstat.mutation.repository.UserRepository;
import com.polstat.mutation.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = User.builder()
                        .username(userDTO.getUsername())
                        .password(userDTO.getPassword())
                        .name(userDTO.getName())
                        .nip(userDTO.getNip())
                        .jabatan(userDTO.getJabatan())
                        .unitKerja(userDTO.getUnitKerja())
                        .role(userDTO.getRole())
                        .build();
        return userRepository.save(user);
    }

    @Override
    public UserDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username);
        // Avoid returning password in response for security reasons
        return new UserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getNip(), user.getJabatan(), user.getUnitKerja(), user.getRole());
    }

    @Override
    public UserDTO updateProfile(String username, UserDTO userDTO) {
        User user = userRepository.findByUsername(username);

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getNip() != null) {
            user.setNip(userDTO.getNip());
        }        
        if (userDTO.getJabatan() != null) {
            user.setJabatan(userDTO.getJabatan());
        }
        if (userDTO.getUnitKerja() != null) {
            user.setUnitKerja(userDTO.getUnitKerja());
        }

        user = userRepository.save(user);

        return UserMapper.mapToDTO(user);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(user -> new UserDTO(user.getUsername(), user.getPassword(), user.getName(), user.getNip(), user.getJabatan(), user.getUnitKerja(), user.getRole()))
                    .collect(Collectors.toList());
    }
}
