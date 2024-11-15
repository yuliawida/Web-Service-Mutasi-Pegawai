package com.polstat.mutation.mapper;

import com.polstat.mutation.dto.MutationRequestDTO;
import com.polstat.mutation.entity.MutationRequest;
import com.polstat.mutation.entity.User;

public class MutationRequestMapper {

    // Convert MutationRequest entity to MutationRequestDTO
    public static MutationRequestDTO toDTO(MutationRequest mutationRequest) {
        if (mutationRequest == null) {
            return null;
        }

        // Extract userId and applicantName from the associated User entity
        Long userId = mutationRequest.getUser() != null ? mutationRequest.getUser().getId() : null;
        String applicantName = mutationRequest.getUser() != null ? mutationRequest.getUser().getName() : null;

        return new MutationRequestDTO(
                mutationRequest.getId(),
                mutationRequest.getProvinsiTujuan(),
                mutationRequest.getKabupatenTujuan(),
                mutationRequest.getJabatanTujuan(),
                mutationRequest.getUnitKerjaTujuan(),
                mutationRequest.getStatus(),
                userId,
                applicantName  // Include the name of the applicant in the DTO
        );
    }

    // Convert MutationRequestDTO to MutationRequest entity
    public static MutationRequest toEntity(MutationRequestDTO mutationRequestDTO, User user) {
        if (mutationRequestDTO == null) {
            return null;
        }
        return MutationRequest.builder()
                .provinsiTujuan(mutationRequestDTO.getProvinsiTujuan())
                .kabupatenTujuan(mutationRequestDTO.getKabupatenTujuan())
                .jabatanTujuan(mutationRequestDTO.getJabatanTujuan())
                .unitKerjaTujuan(mutationRequestDTO.getUnitKerjaTujuan())
                .status(mutationRequestDTO.getStatus())
                .user(user)  // Attach the user to the mutation request
                .build();
    }
}
