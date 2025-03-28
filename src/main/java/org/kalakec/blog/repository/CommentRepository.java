package org.kalakec.blog.repository;

import org.kalakec.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT c.* from comments c inner join posts p\n" +
    " where c.post_id = p.id and p.created_by =:userId", nativeQuery = true)
    List<Comment> findCommentByPost(Long userId);
}
