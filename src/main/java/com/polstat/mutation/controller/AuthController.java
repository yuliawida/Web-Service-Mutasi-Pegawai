package com.polstat.mutation.controller;

import com.polstat.mutation.dto.LoginRequestDTO;
import com.polstat.mutation.dto.UserDTO;
import com.polstat.mutation.entity.User;
import com.polstat.mutation.security.JwtUtil;
import com.polstat.mutation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Operation(summary = "Masuk untuk mendapatkan token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login berhasil, token JWT dikembalikan"),
        @ApiResponse(responseCode = "401", description = "Username atau password salah"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        String token = jwtUtil.generateToken(loginRequestDTO.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }

    @Operation(summary = "Registrasi pengguna baru")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pengguna berhasil didaftarkan"),
        @ApiResponse(responseCode = "400", description = "Permintaan tidak valid, data input tidak sesuai"),
        @ApiResponse(responseCode = "409", description = "Konflik, username sudah ada"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })
    @PostMapping("/register")
    public User register(@Valid @RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
}
