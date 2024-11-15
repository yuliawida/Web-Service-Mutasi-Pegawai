package com.polstat.mutation.controller;

import com.polstat.mutation.dto.UserDTO;
import com.polstat.mutation.service.UserService;
import com.polstat.mutation.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Ambil profil pengguna", description = "Mengambil informasi profil dari pengguna yang sedang login.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profil berhasil diambil"),
        @ApiResponse(responseCode = "401", description = "Akses tidak sah"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @GetMapping("/profile")
    public UserDTO getProfile(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return userService.getUserProfile(username);
    }

    @Operation(summary = "Perbarui profil pengguna", description = "Memungkinkan pengguna yang sedang login untuk memperbarui profil mereka.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profil berhasil diperbarui"),
        @ApiResponse(responseCode = "400", description = "Data profil tidak valid"),
        @ApiResponse(responseCode = "401", description = "Akses tidak sah"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @PutMapping("/profile")
    public UserDTO updateProfile(@RequestBody UserDTO userDTO, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return userService.updateProfile(username, userDTO);  // Mengembalikan profil pengguna yang diperbarui
    }

    @Operation(summary = "Ubah kata sandi pengguna", description = "Memungkinkan pengguna yang sedang login untuk mengubah kata sandi mereka.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Kata sandi berhasil diubah"),
        @ApiResponse(responseCode = "400", description = "Format kata sandi tidak valid"),
        @ApiResponse(responseCode = "401", description = "Akses tidak sah"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @PutMapping("/password")
    public void changePassword(@RequestParam String newPassword, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        userService.changePassword(username, newPassword);
    }

    @Operation(summary = "Hapus akun pengguna", description = "Memungkinkan pengguna yang sedang login untuk menghapus akun mereka.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Akun berhasil dihapus"),
        @ApiResponse(responseCode = "401", description = "Akses tidak sah"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @DeleteMapping("/delete")
    public void deleteAccount(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        userService.deleteUser(username);
    }
    
    @Operation(summary = "Ambil semua pengguna", description = "Memungkinkan admin untuk mengambil daftar semua pengguna.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Daftar pengguna berhasil diambil"),
        @ApiResponse(responseCode = "403", description = "Akses ditolak"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();  // Endpoint untuk admin melihat semua pengguna
    }
}
