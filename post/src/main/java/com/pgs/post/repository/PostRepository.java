package com.pgs.post.repository;

import com.pgs.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> getAllByUserId(Pageable pageable, Long userId);

    List<Post> getTop20ByUserIdOrderByCreatedDateDesc(Long userId);
}
