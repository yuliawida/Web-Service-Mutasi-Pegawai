package com.polstat.mutation.repository;

import com.polstat.mutation.entity.ApprovalLetter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLetterRepository extends JpaRepository<ApprovalLetter, Long> {
    List<ApprovalLetter> findByMutationRequest_UserId(Long userId);
    List<ApprovalLetter> findAll();
}
