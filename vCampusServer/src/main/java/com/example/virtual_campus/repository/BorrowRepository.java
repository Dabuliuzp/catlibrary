package com.example.virtual_campus.repository;

import com.example.virtual_campus.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
