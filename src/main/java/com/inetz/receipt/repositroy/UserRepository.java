package com.inetz.receipt.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inetz.receipt.entity.*;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
