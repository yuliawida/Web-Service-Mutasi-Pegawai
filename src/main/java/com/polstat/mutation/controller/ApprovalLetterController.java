package com.polstat.mutation.controller;

import com.polstat.mutation.dto.ApprovalLetterDTO;
import com.polstat.mutation.entity.User;
import com.polstat.mutation.repository.UserRepository;
import com.polstat.mutation.security.JwtUtil;
import com.polstat.mutation.service.ApprovalLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/approval-letters")
public class ApprovalLetterController {

    @Autowired
    private ApprovalLetterService approvalLetterService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private String extractUsernameFromToken(String token) {
        try {
            return jwtUtil.extractUsername(token.replace("Bearer ", ""));
        } catch (Exception e) {
            throw new RuntimeException("Token tidak valid atau tidak ada", e);
        }
    }

    @Operation(summary = "Buat surat persetujuan baru", description = "Membuat surat persetujuan untuk permohonan mutasi tertentu jika disetujui.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Surat persetujuan berhasil dibuat"),
        @ApiResponse(responseCode = "400", description = "Input tidak valid"),
        @ApiResponse(responseCode = "404", description = "Permohonan mutasi tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })    
    @Secured("ROLE_ADMIN")
    @PostMapping("/create")
    public ApprovalLetterDTO createApprovalLetter(@RequestBody Map<String, String> requestBody) {
        Long mutationRequestId = Long.valueOf(requestBody.get("mutationRequestId"));
        String approvalNumber = requestBody.get("approvalNumber");

        if (mutationRequestId == null || approvalNumber == null || approvalNumber.isEmpty()) {
            throw new IllegalArgumentException("ID Permohonan Mutasi dan Nomor Persetujuan harus disediakan.");
        }

        ApprovalLetterDTO approvalLetterDTO = approvalLetterService.createApprovalLetter(mutationRequestId, approvalNumber);
        return approvalLetterDTO;
    }

    @Operation(summary = "Ambil semua surat persetujuan", description = "Mengambil semua surat persetujuan.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Daftar surat persetujuan berhasil diambil"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })   
    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<ApprovalLetterDTO> getAllApprovalLetters() {
        List<ApprovalLetterDTO> approvalLetters = approvalLetterService.getAllApprovalLetters();
        return approvalLetters;
    }

    @Operation(summary = "Ambil surat persetujuan untuk pengguna", description = "Mengambil surat persetujuan yang terkait dengan pengguna yang sedang login.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Daftar surat persetujuan pengguna berhasil diambil"),
        @ApiResponse(responseCode = "404", description = "Pengguna tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })    
    @Secured("ROLE_USER")
    @GetMapping("/user")
    public List<ApprovalLetterDTO> getApprovalLettersForUser(@RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Pengguna tidak ditemukan");
        }

        List<ApprovalLetterDTO> approvalLetters = approvalLetterService.getApprovalLettersForUser(user.getId());
        return approvalLetters;
    }

    @Operation(summary = "Edit surat persetujuan", description = "Memperbarui nomor persetujuan dan isi surat persetujuan.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Surat persetujuan berhasil diperbarui"),
        @ApiResponse(responseCode = "404", description = "Surat persetujuan tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })    
    @Secured("ROLE_ADMIN")
    @PutMapping("/edit/{approvalLetterId}")
    public ApprovalLetterDTO editApprovalLetter(
            @PathVariable Long approvalLetterId,
            @RequestBody Map<String, String> requestBody) {
        String approvalNumber = requestBody.get("approvalNumber");
        String letterContent = requestBody.get("letterContent");

        return approvalLetterService.editApprovalLetter(approvalLetterId, approvalNumber, letterContent);
    }

    @Operation(summary = "Hapus surat persetujuan", description = "Menghapus surat persetujuan berdasarkan ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Surat persetujuan berhasil dihapus"),
        @ApiResponse(responseCode = "404", description = "Surat persetujuan tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Terjadi kesalahan pada server")
    })    
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{approvalLetterId}")
    public void deleteApprovalLetter(@PathVariable Long approvalLetterId) {
        approvalLetterService.deleteApprovalLetter(approvalLetterId);
    }
}
