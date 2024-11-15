package com.polstat.mutation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String approvalNumber;
    private String letterContent;

    @OneToOne
    @JoinColumn(name = "mutation_request_id")
    private MutationRequest mutationRequest;  // Relasi ke MutationRequest
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
