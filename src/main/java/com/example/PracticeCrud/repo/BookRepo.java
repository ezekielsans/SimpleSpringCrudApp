package com.example.PracticeCrud.repo;

import com.example.PracticeCrud.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo  extends JpaRepository<Book, Long> {
}
