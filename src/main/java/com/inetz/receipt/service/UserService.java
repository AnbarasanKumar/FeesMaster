package com.inetz.receipt.service;

import com.inetz.receipt.entity.User;
import com.inetz.receipt.model.AuthRequest;

public interface UserService {
    User registerUser(AuthRequest request, String creatorRole);
}
