package com.polstat.mutation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MutationRequestDTO {

    private Long id;

    @NotEmpty(message = "Provinsi tujuan tidak boleh kosong")
    private String provinsiTujuan;

    @NotEmpty(message = "Kabupaten tujuan tidak boleh kosong")
    private String kabupatenTujuan;

    @NotEmpty(message = "Jabatan tujuan tidak boleh kosong")
    private String jabatanTujuan;

    @NotEmpty(message = "Unit kerja tujuan tidak boleh kosong")
    private String unitKerjaTujuan;

    private String status;

    private Long userId;  // Add user ID for reference
    private String applicantName;  // Add applicant name for convenience
}
