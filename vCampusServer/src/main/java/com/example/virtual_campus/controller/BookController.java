package com.example.virtual_campus.controller;

import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.model.Book;
import com.example.virtual_campus.repository.BookRepository;
import com.example.virtual_campus.repository.ProductRepository;
import com.example.virtual_campus.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    public Optional<Book> SearchById(Long id){
        return bookRepository.findById(id);
    }

    public Boolean BookEdit(Long id, String title, String author, String press, String isbn){
        if(!bookRepository.existsById(id)) return false;
        Book book = bookRepository.findById(id).get();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPress(press);
        book.setIsbn(isbn);
        updateBook(book);
        return true;
    }

    public boolean BookDelete(Long id){
        if(!bookRepository.existsById(id)) return false;
        Book book = bookRepository.findById(id).get();
        if(!book.isAvailable()) return false;
        bookRepository.delete(book);
        return true;
    }

    public boolean BookAdd(Long id, String title, String author, String press, String isbn){
        if(bookRepository.existsById(id)) return false;
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPress(press);
        book.setIsbn(isbn);
        book.setAvailable(true);
        bookRepository.save(book);
        return true;
    }
}
