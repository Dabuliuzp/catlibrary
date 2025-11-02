package com.example.virtual_campus.controller;

import com.example.virtual_campus.model.Borrow;
import com.example.virtual_campus.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
/*
    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestParam Long bookId, @RequestParam Long borrowerId) {
        return ResponseEntity.ok(borrowService.borrowBook(bookId, borrowerId));
    }
 */
    @PostMapping("/return")
    public ResponseEntity<Void> returnBook(@RequestParam Long borrowId) {
        borrowService.returnBook(borrowId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Borrow>> getAllBorrows() {
        return ResponseEntity.ok(borrowService.findAllBorrows());
    }
}
