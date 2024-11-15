package com.polstat.mutation.service;

import com.polstat.mutation.dto.MutationRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MutationRequestService {
    MutationRequestDTO submitMutationRequest(String username, MutationRequestDTO requestDTO);
    List<MutationRequestDTO> getUserMutationRequests(String username);
    List<MutationRequestDTO> getAllMutationRequests();
    void approveMutationRequest(Long requestId);
    void rejectMutationRequest(Long requestId);

    // New methods for edit and delete
    MutationRequestDTO editMutationRequest(Long requestId, MutationRequestDTO requestDTO);
    void deleteMutationRequest(Long requestId);
}

