package com.polstat.mutation.repository;

import com.polstat.mutation.entity.MutationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MutationRequestRepository extends JpaRepository<MutationRequest, Long> {
    List<MutationRequest> findByUserId(Long userId);
    List<MutationRequest> findAll();

}
