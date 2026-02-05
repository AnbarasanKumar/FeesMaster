package com.inetz.receipt.service;


import org.springframework.data.domain.Page;
import com.inetz.receipt.model.*;


public interface ReceiptService {

    ReceiptResponse createReceipt(ReceiptRequest request, String createdBy);

    ReceiptResponse getReceiptById(Long id);

    Page<ReceiptResponse> getAllReceipts(int page, int size);

    void deleteReceipt(Long id);


    long getTotalReceipts();

    long getTodayReceipts();
}
