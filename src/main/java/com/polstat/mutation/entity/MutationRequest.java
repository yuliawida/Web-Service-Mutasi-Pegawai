package com.polstat.mutation.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MutationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    
    private String provinsiTujuan;
    private String kabupatenTujuan;
    private String jabatanTujuan;
    private String unitKerjaTujuan;
    public String status; // "approved" or "rejected"
}
