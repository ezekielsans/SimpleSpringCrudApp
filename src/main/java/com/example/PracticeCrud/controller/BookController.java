package com.example.PracticeCrud.controller;

import com.example.PracticeCrud.model.Book;
import com.example.PracticeCrud.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> booklist = new ArrayList<>();
            bookRepo.findAll().forEach(booklist::add);


            if (booklist.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }

            return new ResponseEntity<>(booklist, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);


        }


    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookObj = bookRepo.findById(id);

        if (bookObj.isPresent()) {

            return new ResponseEntity<>(bookObj.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        try {
            Book bookObj = bookRepo.save(book);

            return new ResponseEntity<>(bookObj, HttpStatus.CREATED);

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
        Optional<Book> oldBookData = bookRepo.findById(id);
        if (oldBookData.isPresent()) {
            Book updateBookData = oldBookData.get();
            updateBookData.setTitle(newBookData.getTitle());
            updateBookData.setAuthor(newBookData.getAuthor());

            Book bookObj = bookRepo.save(updateBookData);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable Long id) {

        bookRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);


    }
}
