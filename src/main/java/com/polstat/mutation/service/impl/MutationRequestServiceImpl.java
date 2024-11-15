package com.polstat.mutation.service.impl;

import com.polstat.mutation.dto.MutationRequestDTO;
import com.polstat.mutation.entity.MutationRequest;
import com.polstat.mutation.entity.User;
import com.polstat.mutation.repository.MutationRequestRepository;
import com.polstat.mutation.repository.UserRepository;
import com.polstat.mutation.service.MutationRequestService;
import com.polstat.mutation.mapper.MutationRequestMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MutationRequestServiceImpl implements MutationRequestService {

    @Autowired
    private MutationRequestRepository mutationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MutationRequestDTO submitMutationRequest(String username, MutationRequestDTO requestDTO) {
        User user = userRepository.findByUsername(username);

        // Menggunakan mapper untuk konversi ke entity
        MutationRequest mutationRequest = MutationRequestMapper.toEntity(requestDTO, user);
        mutationRequest.setStatus("pending");  // Default status sebagai "pending"

        mutationRequest = mutationRequestRepository.save(mutationRequest);

        // Menggunakan mapper untuk mengonversi kembali entity ke DTO tanpa menambahkan data user
        return MutationRequestMapper.toDTO(mutationRequest);
    }

    @Override
    public List<MutationRequestDTO> getUserMutationRequests(String username) {
        User user = userRepository.findByUsername(username);
        List<MutationRequest> requests = mutationRequestRepository.findByUserId(user.getId());

        // Menggunakan mapper untuk konversi entity ke DTO
        return requests.stream()
                .map(MutationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MutationRequestDTO> getAllMutationRequests() {
        List<MutationRequest> requests = mutationRequestRepository.findAll();

        // Menggunakan mapper untuk konversi entity ke DTO
        return requests.stream()
                .map(MutationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void approveMutationRequest(Long requestId) {
        MutationRequest request = mutationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Mutation request not found"));

        if ("approved".equals(request.getStatus())) {
            throw new IllegalStateException("Request already approved");
        }

        request.setStatus("approved");
        mutationRequestRepository.save(request);
    }

    @Override
    public void rejectMutationRequest(Long requestId) {
        MutationRequest request = mutationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Mutation request not found"));

        if ("approved".equals(request.getStatus())) {
            throw new IllegalStateException("Approved request cannot be rejected");
        }

        request.setStatus("rejected");
        mutationRequestRepository.save(request);
    }
    
    @Transactional
    @Override
    public MutationRequestDTO editMutationRequest(Long requestId, MutationRequestDTO requestDTO) {
        MutationRequest request = mutationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Mutation request not found"));

        // Check status before allowing any edit
        if ("approved".equals(request.getStatus()) || "rejected".equals(request.getStatus())) {
            throw new IllegalStateException("Cannot edit a request that has been approved or rejected");
        }

        // Only update the field(s) provided
        if (requestDTO.getProvinsiTujuan() != null) {
            request.setProvinsiTujuan(requestDTO.getProvinsiTujuan());
        }
        if (requestDTO.getKabupatenTujuan() != null) {
            request.setKabupatenTujuan(requestDTO.getKabupatenTujuan());
        }
        if (requestDTO.getJabatanTujuan() != null) {
            request.setJabatanTujuan(requestDTO.getJabatanTujuan());
        }
        if (requestDTO.getUnitKerjaTujuan() != null) {
            request.setUnitKerjaTujuan(requestDTO.getUnitKerjaTujuan());
        }

        mutationRequestRepository.save(request);

        return MutationRequestMapper.toDTO(request);
    }

    @Transactional
    @Override
    public void deleteMutationRequest(Long requestId) {
        MutationRequest request = mutationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Mutation request not found"));

        if (!"pending".equals(request.getStatus())) {
            throw new IllegalStateException("Only requests with status 'pending' can be deleted");
        }

        mutationRequestRepository.delete(request);
    }
}
