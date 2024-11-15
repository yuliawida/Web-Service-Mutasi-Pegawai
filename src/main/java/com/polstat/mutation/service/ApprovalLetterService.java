package com.polstat.mutation.service;

import com.polstat.mutation.dto.ApprovalLetterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApprovalLetterService {
    ApprovalLetterDTO createApprovalLetter(Long mutationRequestId, String approvalNumber);
    List<ApprovalLetterDTO> getAllApprovalLetters();
    List<ApprovalLetterDTO> getApprovalLettersForUser(Long userId);
    ApprovalLetterDTO editApprovalLetter(Long approvalLetterId, String approvalNumber, String letterContent);
    void deleteApprovalLetter(Long approvalLetterId);
}
