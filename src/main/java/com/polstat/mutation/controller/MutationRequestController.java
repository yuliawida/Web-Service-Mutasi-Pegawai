package com.polstat.mutation.controller;

import com.polstat.mutation.dto.MutationRequestDTO;
import com.polstat.mutation.service.MutationRequestService;
import com.polstat.mutation.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.annotation.Secured;

@RestController
@RequestMapping("/mutations")
public class MutationRequestController {

    @Autowired
    private MutationRequestService mutationRequestService;

    @Autowired
    private JwtUtil jwtUtil;

    private String extractUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token.replace("Bearer ", ""));
    }

    @Operation(summary = "Ajukan permintaan mutasi", description = "Mengizinkan pengguna untuk mengajukan permintaan mutasi baru.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Permintaan mutasi berhasil dibuat"),
        @ApiResponse(responseCode = "400", description = "Data permintaan tidak valid"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })    
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<MutationRequestDTO> submitMutationRequest(@RequestBody @Valid MutationRequestDTO requestDTO, @RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);
        MutationRequestDTO createdRequest = mutationRequestService.submitMutationRequest(username, requestDTO);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @Operation(summary = "Ambil permintaan mutasi pengguna", description = "Mengambil permintaan mutasi yang diajukan oleh pengguna yang sedang login.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan mutasi berhasil diambil"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<List<MutationRequestDTO>> getUserMutationRequests(@RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);
        List<MutationRequestDTO> requests = mutationRequestService.getUserMutationRequests(username);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @Operation(summary = "Ambil semua permintaan mutasi", description = "Mengambil semua permintaan mutasi. Hanya untuk admin.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan mutasi berhasil diambil"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<MutationRequestDTO>> getAllMutationRequests() {
        List<MutationRequestDTO> requests = mutationRequestService.getAllMutationRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @Operation(summary = "Setujui permintaan mutasi", description = "Menyetujui permintaan mutasi berdasarkan ID. Hanya untuk admin.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan berhasil disetujui"),
        @ApiResponse(responseCode = "404", description = "Permintaan mutasi tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("/approve/{requestId}")
    public ResponseEntity<String> approveRequest(@PathVariable Long requestId) {
        mutationRequestService.approveMutationRequest(requestId);
        return new ResponseEntity<>("Permintaan berhasil disetujui", HttpStatus.OK);
    }

    @Operation(summary = "Tolak permintaan mutasi", description = "Menolak permintaan mutasi berdasarkan ID. Hanya untuk admin.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan berhasil ditolak"),
        @ApiResponse(responseCode = "404", description = "Permintaan mutasi tidak ditemukan"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("/reject/{requestId}")
    public ResponseEntity<String> rejectRequest(@PathVariable Long requestId) {
        mutationRequestService.rejectMutationRequest(requestId);
        return new ResponseEntity<>("Permintaan berhasil ditolak", HttpStatus.OK);
    }

    @Operation(summary = "Edit permintaan mutasi", description = "Mengizinkan pengguna untuk mengedit permintaan mutasi yang masih dalam status pending.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan berhasil diperbarui"),
        @ApiResponse(responseCode = "404", description = "Permintaan mutasi tidak ditemukan"),
        @ApiResponse(responseCode = "400", description = "Permintaan tidak dapat diperbarui karena tidak dalam status pending"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_USER")
    @PutMapping("/edit/{requestId}")
    public ResponseEntity<MutationRequestDTO> editMutationRequest(@PathVariable Long requestId, @RequestBody MutationRequestDTO requestDTO) {
        MutationRequestDTO updatedRequest = mutationRequestService.editMutationRequest(requestId, requestDTO);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    @Operation(summary = "Hapus permintaan mutasi", description = "Mengizinkan pengguna untuk menghapus permintaan mutasi yang masih dalam status pending.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permintaan berhasil dihapus"),
        @ApiResponse(responseCode = "404", description = "Permintaan mutasi tidak ditemukan"),
        @ApiResponse(responseCode = "400", description = "Permintaan tidak dapat dihapus karena tidak dalam status pending"),
        @ApiResponse(responseCode = "500", description = "Kesalahan server internal")
    })
    @Secured("ROLE_USER")
    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<String> deleteMutationRequest(@PathVariable Long requestId) {
        mutationRequestService.deleteMutationRequest(requestId);
        return new ResponseEntity<>("Permintaan berhasil dihapus", HttpStatus.OK);
    }

}
