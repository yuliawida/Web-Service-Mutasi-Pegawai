package com.polstat.mutation.service.impl;

import com.polstat.mutation.dto.ApprovalLetterDTO;
import com.polstat.mutation.entity.ApprovalLetter;
import com.polstat.mutation.entity.MutationRequest;
import com.polstat.mutation.entity.User;
import com.polstat.mutation.mapper.ApprovalLetterMapper;
import com.polstat.mutation.repository.ApprovalLetterRepository;
import com.polstat.mutation.repository.MutationRequestRepository;
import com.polstat.mutation.repository.UserRepository;
import com.polstat.mutation.service.ApprovalLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalLetterServiceImpl implements ApprovalLetterService {

    @Autowired
    private MutationRequestRepository mutationRequestRepository;

    @Autowired
    private ApprovalLetterRepository approvalLetterRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApprovalLetterDTO createApprovalLetter(Long mutationRequestId, String approvalNumber) {
        if (mutationRequestId == null || approvalNumber == null || approvalNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Mutation Request ID and Approval Number are required.");
        }

        MutationRequest mutationRequest = mutationRequestRepository.findById(mutationRequestId)
                .orElseThrow(() -> new RuntimeException("Mutation request not found"));
        
        if (!mutationRequest.getStatus().equals("approved")) {
            throw new RuntimeException("Only approved mutation requests can have an approval letter");
        }

        User user = mutationRequest.getUser(); // Get the user associated with the mutation request

        // Create Approval Letter
        ApprovalLetter approvalLetter = ApprovalLetter.builder()
                .approvalNumber(approvalNumber)
                .letterContent("Surat Persetujuan Mutasi Pegawai a.n " + user.getName())
                .mutationRequest(mutationRequest)
                .user(user)
                .build();

        approvalLetter = approvalLetterRepository.save(approvalLetter);

        // Convert ApprovalLetter entity to DTO and return
        return ApprovalLetterMapper.toDTO(approvalLetter);
    }

    @Override
    public List<ApprovalLetterDTO> getAllApprovalLetters() {
        List<ApprovalLetter> approvalLetters = approvalLetterRepository.findAll();
        return approvalLetters.stream()
                .map(ApprovalLetterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovalLetterDTO> getApprovalLettersForUser(Long userId) {
        List<ApprovalLetter> approvalLetters = approvalLetterRepository.findByMutationRequest_UserId(userId);
        return approvalLetters.stream()
                .map(ApprovalLetterMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApprovalLetterDTO editApprovalLetter(Long approvalLetterId, String approvalNumber, String letterContent) {
        ApprovalLetter approvalLetter = approvalLetterRepository.findById(approvalLetterId)
                .orElseThrow(() -> new RuntimeException("Approval letter not found"));

        if (approvalNumber != null && !approvalNumber.isEmpty()) {
            approvalLetter.setApprovalNumber(approvalNumber);
        }

        if (letterContent != null && !letterContent.isEmpty()) {
            approvalLetter.setLetterContent(letterContent);
        }

        approvalLetter = approvalLetterRepository.save(approvalLetter);
        return ApprovalLetterMapper.toDTO(approvalLetter);
    }

    @Override
    public void deleteApprovalLetter(Long approvalLetterId) {
        ApprovalLetter approvalLetter = approvalLetterRepository.findById(approvalLetterId)
                .orElseThrow(() -> new RuntimeException("Approval letter not found"));
        approvalLetterRepository.delete(approvalLetter);
    }
}
