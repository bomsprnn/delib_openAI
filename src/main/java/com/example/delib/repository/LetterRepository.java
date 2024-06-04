package com.example.delib.repository;


import com.example.delib.domain.Letter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    Letter findLetterById(Long id);
    Page<Letter> findAll(Pageable pageable);
}
