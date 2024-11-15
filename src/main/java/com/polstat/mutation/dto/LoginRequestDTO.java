package com.polstat.mutation.dto;

import jakarta.validation.constraints.NotBlank;  // Import untuk validasi
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Username harus diisi")  // Validasi username tidak boleh kosong
    private String username;

    @NotBlank(message = "Password harus diisi")  // Validasi password tidak boleh kosong
    private String password;
}
