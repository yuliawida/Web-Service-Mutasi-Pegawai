package com.polstat.mutation.dto;

import com.polstat.mutation.entity.Role;
import jakarta.validation.constraints.NotBlank;  // Import untuk validasi
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "Username harus diisi")  // Validasi username tidak boleh kosong
    @Size(min = 4, max = 20, message = "Username harus memiliki panjang antara 4 sampai 20 karakter")
    private String username;

    @NotBlank(message = "Password harus diisi")  // Validasi password tidak boleh kosong
    @Size(min = 6, message = "Password harus lebih dari 6 karakter")
    private String password;

    @NotBlank(message = "Nama harus diisi")  // Validasi nama tidak boleh kosong
    private String name;

    @NotBlank(message = "NIP harus diisi")  // Validasi NIP tidak boleh kosong
    private String nip;

    @NotBlank(message = "Jabatan harus diisi")  // Validasi jabatan tidak boleh kosong
    private String jabatan;

    @NotBlank(message = "Unit Kerja harus diisi")  // Validasi unit kerja tidak boleh kosong
    private String unitKerja;

    private Role role;  // Role tidak memerlukan validasi
}
