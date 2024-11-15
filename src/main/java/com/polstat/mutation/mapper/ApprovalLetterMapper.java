package com.polstat.mutation.mapper;

import com.polstat.mutation.dto.ApprovalLetterDTO;
import com.polstat.mutation.entity.ApprovalLetter;
import com.polstat.mutation.entity.MutationRequest;
import com.polstat.mutation.entity.User;

public class ApprovalLetterMapper {

    // Mengonversi ApprovalLetter entity ke ApprovalLetterDTO
    public static ApprovalLetterDTO toDTO(ApprovalLetter approvalLetter) {
        if (approvalLetter == null) {
            return null;
        }

        return new ApprovalLetterDTO(
                approvalLetter.getId(),
                approvalLetter.getApprovalNumber(),
                approvalLetter.getLetterContent(),
                approvalLetter.getMutationRequest() != null ? approvalLetter.getMutationRequest().getId() : null,  // ID dari MutationRequest
                approvalLetter.getUser() != null ? approvalLetter.getUser().getId() : null  // ID dari User
        );
    }

    // Mengonversi ApprovalLetterDTO ke ApprovalLetter entity
    public static ApprovalLetter toEntity(ApprovalLetterDTO approvalLetterDTO, MutationRequest mutationRequest, User user) {
        if (approvalLetterDTO == null) {
            return null;
        }
        return ApprovalLetter.builder()
                .id(approvalLetterDTO.getId())
                .approvalNumber(approvalLetterDTO.getApprovalNumber())
                .letterContent(approvalLetterDTO.getLetterContent())
                .mutationRequest(mutationRequest)  // Mengaitkan dengan MutationRequest
                .user(user)  // Mengaitkan dengan User
                .build();
    }
}
