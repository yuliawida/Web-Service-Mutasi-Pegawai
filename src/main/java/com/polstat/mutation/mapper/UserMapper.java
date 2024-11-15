package com.polstat.mutation.mapper;

import com.polstat.mutation.dto.UserDTO;
import com.polstat.mutation.entity.User;

public class UserMapper {

    // Mengonversi UserDTO ke User entity
    public static User mapToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())  // Anda bisa menyesuaikan apakah password harus disertakan dalam mapping ini
                .name(userDTO.getName())
                .nip(userDTO.getNip())
                .jabatan(userDTO.getJabatan())
                .unitKerja(userDTO.getUnitKerja())
                .role(userDTO.getRole())
                .build();
    }

    // Mengonversi User entity ke UserDTO
    public static UserDTO mapToDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())  // Jika perlu, sesuaikan untuk tidak mengirimkan password
                .name(user.getName())
                .nip(user.getNip())
                .jabatan(user.getJabatan())
                .unitKerja(user.getUnitKerja())
                .role(user.getRole())
                .build();
    }
}
