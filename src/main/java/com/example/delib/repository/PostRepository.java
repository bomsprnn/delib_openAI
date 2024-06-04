package com.example.delib.repository;

import com.example.delib.domain.Member;
import com.example.delib.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findByMemberOrderByIdDesc(Member member, Pageable pageable);

}
