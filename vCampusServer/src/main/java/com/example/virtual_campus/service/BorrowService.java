package com.example.virtual_campus.service;

import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.model.Borrow;
import com.example.virtual_campus.repository.BookRepository;
import com.example.virtual_campus.repository.UserRepository;
import com.example.virtual_campus.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public int borrowBook(Long bookId, Long userId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (bookOptional.isPresent() && userOptional.isPresent()) {
            Book book = bookOptional.get();
            User user = userOptional.get();

            if (book.isAvailable()) {
                book.setAvailable(false);
                bookRepository.save(book);

                Borrow borrow = new Borrow();
                borrow.setBook(book);
                borrow.setUser(user);
                borrow.setBorrowDate(LocalDate.now());
                borrowRepository.save(borrow);
                return 1;
            } else {
                return 2;
            }
        } else {
            return 2;
        }
    }

    public void returnBook(Long borrowId) {
        Optional<Borrow> borrowOptional = borrowRepository.findById(borrowId);

        if (borrowOptional.isPresent()) {
            Borrow borrow = borrowOptional.get();
            Book book = borrow.getBook();

            book.setAvailable(true);
            bookRepository.save(book);

            borrow.setReturnDate(LocalDate.now());
            borrowRepository.save(borrow);
        } else {
            throw new RuntimeException("Borrow not found");
        }
    }

    public Iterable<Borrow> findAllBorrows() {
        return borrowRepository.findAll();
    }
}
