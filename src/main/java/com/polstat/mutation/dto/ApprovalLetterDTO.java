package com.polstat.mutation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApprovalLetterDTO {
    private Long id;
    private String approvalNumber;
    private String letterContent;  // Isi surat persetujuan
    private Long mutationRequestId;  // ID dari MutationRequest
    private Long userId;  // ID dari User
}
