package com.polstat.mutation.service;

import com.polstat.mutation.dto.UserDTO;
import com.polstat.mutation.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerUser(UserDTO userDTO);
    UserDTO getUserProfile(String username);
    UserDTO updateProfile(String username, UserDTO userDTO);  // Return UserDTO after update
    void changePassword(String username, String newPassword);
    void deleteUser(String username);
    List<UserDTO> getAllUsers();
}
