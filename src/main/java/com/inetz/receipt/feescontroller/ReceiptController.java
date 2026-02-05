package com.inetz.receipt.feescontroller;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.inetz.receipt.constant.Constant;
import com.inetz.receipt.model.*;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin("*")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReceiptResponse>> createReceipt(
            @Valid @RequestBody ReceiptRequest dto,
            Authentication authentication) {

        String username = authentication.getName();

        ReceiptResponse receipt = receiptService.createReceipt(dto, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        Constant.RECEIPT_CREATED_SUCCESS,
                        receipt,
                        true
                ));
    }


    /*
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/fetchAll")
    public ResponseEntity<ApiResponse<List<ReceiptResponse>>> getAllReceipts() {
        List<ReceiptResponse> receipts = receiptService.getAllReceipts();
        return ResponseEntity.ok(
                new ApiResponse<>(Constant.RECEIPTS_FETCHED_SUCCESS, receipts, true));
    }

	*/
    
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<ReceiptResponse>> getReceiptById(@PathVariable Long id) {
        ReceiptResponse receipt = receiptService.getReceiptById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(Constant.RECEIPT_FETCHED_SUCCESS, receipt, true));
    }
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<PaginationResponse<ReceiptResponse>>> getAllReceipts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        var receiptPage = receiptService.getAllReceipts(page, size);

        PaginationResponse<ReceiptResponse> response =
                new PaginationResponse<>(
                        receiptPage.getContent(),
                        receiptPage.getTotalElements(),
                        receiptPage.getTotalPages(),
                        receiptPage.getNumber(),
                        receiptPage.getSize(),
                        receiptPage.isLast(),
                        receiptPage.isFirst()
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        Constant.RECEIPTS_FETCHED_SUCCESS,
                        response,
                        true
                )
        );
    }


    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReceipt(@PathVariable Long id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.ok(
                new ApiResponse<>(Constant.RECEIPT_DELETED_SUCCESS, "Deleted", true));
    }

   
    
    /*
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> totalReceipts() {
        long count = receiptService.getTotalReceipts();
        return ResponseEntity.ok(
                new ApiResponse<>("Total receipts fetched", count, true));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/count/today")
    public ResponseEntity<ApiResponse<Long>> todayReceipts() {
        long count = receiptService.getTodayReceipts();
        return ResponseEntity.ok(
                new ApiResponse<>("Today receipts fetched", count, true));
    }
    */
}
